package com.in.weather.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.in.weather.http.service.IRestTemplateService;
import com.in.weather.parser.exception.ParseDataException;
import com.in.weather.rule.enums.Operation;
import com.in.weather.rule.exceptions.RuleOperationKeyNotValidException;
import com.in.weather.rule.exceptions.RuleOperationNotValidException;
import com.in.weather.rule.service.IRuleService;
import com.in.weather.rule.wrapper.Rule;
import com.in.weather.service.IWeatherParser;
import com.in.weather.service.IWeatherService;
import com.in.weather.service.exceptions.ParamTypeNotValidException;
import com.in.weather.utils.WeatherConstants;
import com.in.weather.wrapper.main.ChartRecord;
import com.in.weather.wrapper.main.WeatherData;
import com.in.weather.wrapper.main.WeatherDayData;
import com.in.weather.wrapper.main.WeatherInfo;

/**
 * The Class WeatherServiceImpl.
 */
@Service
public class WeatherServiceImpl implements IWeatherService {

	Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);
	
	/** The i rest template service. */
	@Autowired
	private IRestTemplateService iRestTemplateService;

	/** The i weather parser. */
	@Autowired
	private IWeatherParser iWeatherParser;

	/** The i rule service. */
	@Autowired
	private IRuleService iRuleService;

	/** The env. */
	@Autowired
	private Environment env;

	/**
	 * Gets the weather detail.
	 *
	 * @param city the city
	 * @return the weather detail
	 * @throws ParseDataException                the parse data exception
	 * @throws ParseException                    the parse exception
	 * @throws RuleOperationKeyNotValidException the rule operation key not valid
	 *                                           exception
	 * @throws RuleOperationNotValidException    the rule operation not valid
	 *                                           exception
	 * @throws ParamTypeNotValidException        the param type not valid exception
	 */
	@Override
	public WeatherData getWeatherDetail(String city) throws ParseDataException, ParseException,
			RuleOperationKeyNotValidException, RuleOperationNotValidException, ParamTypeNotValidException {

		logger.info("Execution start : getWeatherDetail, param : city - " + city);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

		params.add(env.getProperty(WeatherConstants.WEATHER_API_INPUT_CITY_CONFIG_CONST), city);

		addApiKeyInWeatherParam(params);

		logger.trace("Added weather API key, param - " + params + " api param : city - " + city);

		addTimeCounterInWeatherParam(params);

		logger.trace("Added Time Counter API key, param - " + params + " api param : city - " + city);

		String response = iRestTemplateService.getPostsPlainJSON(
				env.getProperty(WeatherConstants.WEATHER_API_URI_CONFIG_CONST), params,
				WeatherConstants.PARAM_TYPE_QUERY);

		logger.trace("Got response from Weather API, api param : city - " + city);

		WeatherData weatherData = iWeatherParser
				.parse(env.getProperty(WeatherConstants.WEATHER_API_IMPL_TYPE_CONFIG_CONST), response);

		logger.trace("Weather Response string parsing success, api param : city - " + city);

		try {
			weatherData = averageTempData(weatherData);
			logger.trace("Average Temperature calculated and added in the response, api param : city - " + city);
		} catch (ParseDataException e) {
			logger.error("ParseDataException in getWeatherDetail : ", e.getStackTrace());
			throw new ParseDataException();
		}

		populateSuggestion(weatherData);

		logger.trace("Suggestions populated in weather response, api param : city - " + city);

		return weatherData;

	}

	/**
	 * Populate suggestion.
	 *
	 * @param weatherData the weather data
	 * @throws ParseDataException                the parse data exception
	 * @throws RuleOperationKeyNotValidException the rule operation key not valid
	 *                                           exception
	 * @throws RuleOperationNotValidException    the rule operation not valid
	 *                                           exception
	 */
	private void populateSuggestion(WeatherData weatherData)
			throws ParseDataException, RuleOperationKeyNotValidException, RuleOperationNotValidException {
		
		List<Rule> rules = iRuleService.getRules();

		logger.trace("In populateSuggestion : rules size : " + rules != null ? rules.size() + "" : "0");
		
		List<WeatherDayData> weatherDayData = weatherData.getWeatherDayData();

		for (WeatherDayData day : weatherDayData) {

			for (Rule rule : rules) {
				setSuggestion(rule, day);
			}
		}
		
		logger.trace("Rules applied success");

	}

	/**
	 * Sets the suggestion.
	 *
	 * @param rule the rule
	 * @param day  the day
	 * @throws RuleOperationKeyNotValidException the rule operation key not valid
	 *                                           exception
	 * @throws RuleOperationNotValidException    the rule operation not valid
	 *                                           exception
	 */
	private void setSuggestion(Rule rule, WeatherDayData day)
			throws RuleOperationKeyNotValidException, RuleOperationNotValidException {

		logger.trace("In setSuggestion : rule " + rule);
		switch (rule.getOnKey()) {
		case MAX_TEMP:
			Double tempMax = day.getWeatherInfo().getTempMax();
			if (isRuleApplied(rule.getOp(), tempMax, rule.getValue())) {
				if (day.getWeatherInfo().getSuggestion() != null) {
					day.getWeatherInfo().setSuggestion(day.getWeatherInfo().getSuggestion() + ", " + rule.getMessage());
				} else {
					day.getWeatherInfo().setSuggestion(rule.getMessage());
				}
			}
			break;
		case MIN_TEMP:
			Double tempMin = day.getWeatherInfo().getTempMin();
			if (isRuleApplied(rule.getOp(), tempMin, rule.getValue())) {
				if (day.getWeatherInfo().getSuggestion() != null) {
					day.getWeatherInfo().setSuggestion(day.getWeatherInfo().getSuggestion() + ", " + rule.getMessage());
				} else {
					day.getWeatherInfo().setSuggestion(rule.getMessage());
				}
			}
			break;
		case WIND_SPEED:
			Double speed = day.getWind().getSpeed();
			if (isRuleApplied(rule.getOp(), speed, rule.getValue())) {
				if (day.getWeatherInfo().getSuggestion() != null) {
					day.getWeatherInfo().setSuggestion(day.getWeatherInfo().getSuggestion() + ", " + rule.getMessage());
				} else {
					day.getWeatherInfo().setSuggestion(rule.getMessage());
				}
			}
			break;
		default:
			logger.error(
					"RuleOperationKeyNotValidException in setSuggestion : Given rule not supported : rule " + rule);
			throw new RuleOperationKeyNotValidException();
		}
	}

	/**
	 * Checks if is rule applied.
	 *
	 * @param op           the op
	 * @param valueToCheck the value to check
	 * @param value        the value
	 * @return true, if is rule applied
	 * @throws RuleOperationNotValidException the rule operation not valid exception
	 */
	private boolean isRuleApplied(Operation op, Double valueToCheck, String value)
			throws RuleOperationNotValidException {
		switch (op) {
		case eq:
			return valueToCheck.equals(Double.parseDouble(value));
		case gt:
			return valueToCheck > Double.parseDouble(value);
		case lt:
			return valueToCheck < Double.parseDouble(value);
		case gte:
			return valueToCheck >= Double.parseDouble(value);
		case lte:
			return valueToCheck <= Double.parseDouble(value);
		default:
			throw new RuleOperationNotValidException();
		}
	}

	/**
	 * Average temp data.
	 *
	 * @param weatherData the weather data
	 * @return the weather data
	 * @throws ParseDataException the parse data exception
	 * @throws ParseException     the parse exception
	 */
	private WeatherData averageTempData(WeatherData weatherData) throws ParseDataException, ParseException {
		WeatherData wd = new WeatherData();
		wd.setCity(weatherData.getCity());
		DateFormat dateFormat = new SimpleDateFormat(WeatherConstants.DD_MM_DATE_FORMAT);

		List<WeatherDayData> weatherDayData = weatherData.getWeatherDayData();
		Map<String, WeatherDayData> dataMap = getDataMap();

		for (WeatherDayData day : weatherDayData) {
			Long date = day.getDate() * 1000;
			Date d = new Date(date);

			String dateStr = dateFormat.format(d);
			if (dataMap.get(dateStr) != null) {
				WeatherDayData newWeatherDayData = dataMap.get(dateStr);
				newWeatherDayData.setClouds(day.getClouds());
				newWeatherDayData.setVisibility(day.getVisibility());
				newWeatherDayData.setWeather(day.getWeather());
				newWeatherDayData.setWind(day.getWind());
				WeatherInfo weatherInfo = newWeatherDayData.getWeatherInfo();
				if (weatherInfo != null) {
					if (weatherInfo.getTempMax() != null) {
						Double avgTemp = (weatherInfo.getTempMax() + day.getWeatherInfo().getTempMax()) / 2;
						weatherInfo.setTempMax(avgTemp);
						newWeatherDayData.setWeatherInfo(weatherInfo);
					} else {
						weatherInfo.setTempMax(day.getWeatherInfo().getTempMax());
						newWeatherDayData.setWeatherInfo(weatherInfo);
					}
					if (weatherInfo.getTempMin() != null) {
						Double avgTemp = (weatherInfo.getTempMin() + day.getWeatherInfo().getTempMin()) / 2;
						weatherInfo.setTempMin(avgTemp);
						newWeatherDayData.setWeatherInfo(weatherInfo);
					} else {
						weatherInfo.setTempMin(day.getWeatherInfo().getTempMin());
						newWeatherDayData.setWeatherInfo(weatherInfo);
					}
				}
			}
		}

		List<WeatherDayData> weatherDayAvgList = new ArrayList<>();
		for (Map.Entry<String, WeatherDayData> entry : dataMap.entrySet()) {
			WeatherDayData dayData = entry.getValue();

			Date newDate = dateFormat.parse(entry.getKey());
			dayData.setDate(newDate.getTime());
			weatherDayAvgList.add(dayData);
		}
		wd.setWeatherDayData(weatherDayAvgList);
		return wd;
	}

	/**
	 * Gets the data map.
	 *
	 * @return the data map
	 */
	private Map<String, WeatherDayData> getDataMap() {
		DateFormat dateFormat = new SimpleDateFormat(WeatherConstants.DD_MM_DATE_FORMAT);
		Map<String, WeatherDayData> dataMap = new HashMap<>();
		Date todayDate = new Date();

		for (int count = 0; count <= getWeatherAPIPredictDaysCount(); count++) {
			Instant instant = todayDate.toInstant();
			Date futureDate = Date.from(instant.plus(count, ChronoUnit.DAYS));
			WeatherDayData wd = new WeatherDayData();
			WeatherInfo info = new WeatherInfo();
			wd.setWeatherInfo(info);
			dataMap.put(dateFormat.format(futureDate), wd);
		}
		return dataMap;
	}

	/**
	 * Adds the api key in weather param.
	 *
	 * @param params the params
	 */
	private void addApiKeyInWeatherParam(MultiValueMap<String, String> params) {
		params.add(WeatherConstants.WEATHER_API_INPUT_API_ID_CONFIG_CONST, env.getProperty(WeatherConstants.API_KEY));
	}

	/**
	 * Adds the time counter in weather param.
	 *
	 * @param params the params
	 */
	private void addTimeCounterInWeatherParam(MultiValueMap<String, String> params) {
		Integer counter = getWeatherAPIPredictDaysCount() * 8;
		params.add(WeatherConstants.WEATHER_API_INPUT_COUNT_CONFIG_CONST, counter.toString());
	}

	/**
	 * Adds the max time counter in weather param.
	 *
	 * @param params the params
	 */
	private void addMaxTimeCounterInWeatherParam(MultiValueMap<String, String> params) {
		params.add(WeatherConstants.WEATHER_API_INPUT_COUNT_CONFIG_CONST, "40");
	}

	/**
	 * Gets the weather API predict days count.
	 *
	 * @return the weather API predict days count
	 */
	private Integer getWeatherAPIPredictDaysCount() {
		return Integer.parseInt(env.getProperty(WeatherConstants.WEATHER_API_PREDICT_DAYS_COUNT));
	}

	/**
	 * Gets the weather hourly detail.
	 *
	 * @param city the city
	 * @param date the date
	 * @return the weather hourly detail
	 * @throws ParseDataException         the parse data exception
	 * @throws ParamTypeNotValidException the param type not valid exception
	 */
	@Override
	public List<ChartRecord> getWeatherHourlyDetail(String city, Long date)
			throws ParseDataException, ParamTypeNotValidException {
		List<ChartRecord> hourlyResponse = new ArrayList<>();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

		params.add(env.getProperty(WeatherConstants.WEATHER_API_INPUT_CITY_CONFIG_CONST), city);

		addApiKeyInWeatherParam(params);

		addMaxTimeCounterInWeatherParam(params);

		String response = iRestTemplateService.getPostsPlainJSON(
				env.getProperty(WeatherConstants.WEATHER_API_URI_CONFIG_CONST), params,
				WeatherConstants.PARAM_TYPE_QUERY);

		WeatherData weatherData = iWeatherParser
				.parse(env.getProperty(WeatherConstants.WEATHER_API_IMPL_TYPE_CONFIG_CONST), response);

		DateFormat dateFormat = new SimpleDateFormat(WeatherConstants.DD_MM_DATE_FORMAT);
		String dateFromUser = dateFormat.format(date);

		List<WeatherDayData> weatherDayData = weatherData.getWeatherDayData();

		for (WeatherDayData day : weatherDayData) {
			Long dateInResponse = day.getDate() * 1000;
			Date d = new Date(dateInResponse);
			String rsDate = dateFormat.format(d);

			if (dateFromUser.equalsIgnoreCase(rsDate)) {
				ChartRecord rec = new ChartRecord();
				rec.setDate(dateInResponse);
				rec.setValue(day.getWeatherInfo().getTempMax());

				hourlyResponse.add(rec);
			}
		}

		return hourlyResponse;

	}
}
