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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

		params.add(env.getProperty(WeatherConstants.WEATHER_API_INPUT_CITY_CONFIG_CONST), city);

		addApiKeyInWeatherParam(params);

		addTimeCounterInWeatherParam(params);

		String response = iRestTemplateService.getPostsPlainJSON(
				env.getProperty(WeatherConstants.WEATHER_API_URI_CONFIG_CONST), params,
				WeatherConstants.PARAM_TYPE_QUERY);

		WeatherData weatherData = iWeatherParser
				.parse(env.getProperty(WeatherConstants.WEATHER_API_IMPL_TYPE_CONFIG_CONST), response);

		try {
			weatherData = averageTempData(weatherData);
		} catch (ParseDataException e) {
			throw new ParseDataException();
		}

		populateSuggestion(weatherData);

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

		List<WeatherDayData> weatherDayData = weatherData.getWeatherDayData();

		for (WeatherDayData day : weatherDayData) {

			for (Rule rule : rules) {
				setSuggestion(rule, day);
			}
		}

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
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws ParseDataException the parse data exception
	 * @throws ParseException     the parse exception
	 */
	public static void main(String[] args) throws ParseDataException, ParseException {
		WeatherServiceImpl wd = new WeatherServiceImpl();
		String str = "{\"weatherDayData\":[{\"date\":1635066000,\"weatherInfo\":{\"temp\":284.56,\"feelsLike\":283.66,\"tempMin\":284.56,\"tempMax\":284.69,\"pressure\":1019.0,\"seaLevel\":1019.0,\"grndLevel\":1016.0,\"humidity\":73.0,\"tempKf\":-0.13},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":91},\"wind\":{\"speed\":3.59,\"deg\":184,\"gust\":9.69},\"visibility\":10000},{\"date\":1635076800,\"weatherInfo\":{\"temp\":286.43,\"feelsLike\":285.51,\"tempMin\":286.43,\"tempMax\":287.39,\"pressure\":1018.0,\"seaLevel\":1018.0,\"grndLevel\":1014.0,\"humidity\":65.0,\"tempKf\":-0.96},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":94},\"wind\":{\"speed\":5.36,\"deg\":203,\"gust\":8.5},\"visibility\":10000},{\"date\":1635087600,\"weatherInfo\":{\"temp\":287.33,\"feelsLike\":286.58,\"tempMin\":287.33,\"tempMax\":287.33,\"pressure\":1016.0,\"seaLevel\":1016.0,\"grndLevel\":1013.0,\"humidity\":68.0,\"tempKf\":0.0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":95},\"wind\":{\"speed\":4.6,\"deg\":200,\"gust\":7.77},\"visibility\":10000},{\"date\":1635098400,\"weatherInfo\":{\"temp\":286.58,\"feelsLike\":286.02,\"tempMin\":286.58,\"tempMax\":286.58,\"pressure\":1016.0,\"seaLevel\":1016.0,\"grndLevel\":1013.0,\"humidity\":78.0,\"tempKf\":0.0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":97},\"wind\":{\"speed\":3.83,\"deg\":188,\"gust\":10.14},\"visibility\":10000},{\"date\":1635109200,\"weatherInfo\":{\"temp\":286.37,\"feelsLike\":286.07,\"tempMin\":286.37,\"tempMax\":286.37,\"pressure\":1015.0,\"seaLevel\":1015.0,\"grndLevel\":1012.0,\"humidity\":89.0,\"tempKf\":0.0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.64,\"deg\":204,\"gust\":10.47},\"visibility\":10000},{\"date\":1635120000,\"weatherInfo\":{\"temp\":285.25,\"feelsLike\":284.94,\"tempMin\":285.25,\"tempMax\":285.25,\"pressure\":1015.0,\"seaLevel\":1015.0,\"grndLevel\":1012.0,\"humidity\":93.0,\"tempKf\":0.0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":62},\"wind\":{\"speed\":3.19,\"deg\":217,\"gust\":10.11},\"visibility\":10000},{\"date\":1635130800,\"weatherInfo\":{\"temp\":284.44,\"feelsLike\":284.05,\"tempMin\":284.44,\"tempMax\":284.44,\"pressure\":1014.0,\"seaLevel\":1014.0,\"grndLevel\":1011.0,\"humidity\":93.0,\"tempKf\":0.0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":2},\"wind\":{\"speed\":3.03,\"deg\":217,\"gust\":10.52},\"visibility\":10000},{\"date\":1635141600,\"weatherInfo\":{\"temp\":284.16,\"feelsLike\":283.67,\"tempMin\":284.16,\"tempMax\":284.16,\"pressure\":1015.0,\"seaLevel\":1015.0,\"grndLevel\":1011.0,\"humidity\":90.0,\"tempKf\":0.0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":3.22,\"deg\":222,\"gust\":10.87},\"visibility\":10000},{\"date\":1635152400,\"weatherInfo\":{\"temp\":285.77,\"feelsLike\":285.2,\"tempMin\":285.77,\"tempMax\":285.77,\"pressure\":1015.0,\"seaLevel\":1015.0,\"grndLevel\":1012.0,\"humidity\":81.0,\"tempKf\":0.0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":59},\"wind\":{\"speed\":3.49,\"deg\":234,\"gust\":8.9},\"visibility\":10000},{\"date\":1635163200,\"weatherInfo\":{\"temp\":288.22,\"feelsLike\":287.4,\"tempMin\":288.22,\"tempMax\":288.22,\"pressure\":1015.0,\"seaLevel\":1015.0,\"grndLevel\":1012.0,\"humidity\":62.0,\"tempKf\":0.0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":3.3,\"deg\":252,\"gust\":5.57},\"visibility\":10000}],\"city\":{\"id\":2643743,\"name\":\"London\",\"coord\":{\"lat\":51.5085,\"lon\":-0.1257},\"country\":\"GB\",\"population\":1000000,\"timezone\":3600,\"sunriseTime\":1635057597,\"sunsetTime\":1635094149}}";

		WeatherData data = new WeatherData();

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			data = objectMapper.readValue(str, new TypeReference<WeatherData>() {
			});
		} catch (JsonProcessingException e) {
			throw new ParseDataException();
		}

		wd.averageTempData(data);
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
