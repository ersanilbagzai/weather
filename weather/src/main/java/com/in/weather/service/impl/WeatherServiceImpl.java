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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.in.weather.http.service.IRestTemplateService;
import com.in.weather.rule.enums.Operation;
import com.in.weather.rule.service.IRuleService;
import com.in.weather.rule.wrapper.Rule;
import com.in.weather.service.IWeatherParser;
import com.in.weather.service.IWeatherService;
import com.in.weather.wrapper.main.WeatherData;
import com.in.weather.wrapper.main.WeatherDayData;
import com.in.weather.wrapper.main.WeatherInfo;

@Service
public class WeatherServiceImpl implements IWeatherService {

	@Autowired
	private IRestTemplateService iRestTemplateService;

	@Autowired
	private IWeatherParser iWeatherParser;
	
	@Autowired
	private IRuleService iRuleService;
	
	@Autowired
	private Environment env;
	
	@Override
	public WeatherData getWeatherDetail(String city) throws JsonMappingException, JsonProcessingException {

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("q", city);
		addParams(params);

		String postsPlainJSON = iRestTemplateService.getPostsPlainJSON("https://api.openweathermap.org/data/2.5/forecast", params, "");
		
		WeatherData weatherData = iWeatherParser.parse("openweathermap", postsPlainJSON);
		
		try {
			weatherData = averageTempData(weatherData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		populateSuggestion(weatherData);
		
		return weatherData;

	}
private void populateSuggestion(WeatherData weatherData) throws JsonMappingException, JsonProcessingException {
	
	List<Rule> rules = iRuleService.getRules();
	
	List<WeatherDayData> weatherDayData = weatherData.getWeatherDayData();
	
	for(WeatherDayData day : weatherDayData) {
	
		for(Rule rule : rules) {
			setSuggestion(rule, day);
		}
//		if(day.getWeatherInfo().getTempMax() > 40) {
//			day.getWeatherInfo().setSuggestion("Carry Umberala");
//		}
	}
		
	}

private void setSuggestion(Rule rule, WeatherDayData day) {
	
	
	switch(rule.getOnKey()) {
	case MAX_TEMP:
		Double tempMax = day.getWeatherInfo().getTempMax();
		if(isRuleApplyid(rule.getOp(), tempMax, rule.getValue())) {
			if (day.getWeatherInfo().getSuggestion() != null) {
				day.getWeatherInfo().setSuggestion(day.getWeatherInfo().getSuggestion() + ", " + rule.getMessage());
			} else {
				day.getWeatherInfo().setSuggestion(rule.getMessage());
			}
		}
		break;
	case MIN_TEMP:
		Double tempMin = day.getWeatherInfo().getTempMin();
		if(isRuleApplyid(rule.getOp(), tempMin, rule.getValue())) {
			if (day.getWeatherInfo().getSuggestion() != null) {
				day.getWeatherInfo().setSuggestion(day.getWeatherInfo().getSuggestion() + ", " + rule.getMessage());
			} else {
				day.getWeatherInfo().setSuggestion(rule.getMessage());
			}
		}
		break;
	case WIND_SPEED:
		Double speed = day.getWind().getSpeed();
		if(isRuleApplyid(rule.getOp(), speed, rule.getValue())) {
			if (day.getWeatherInfo().getSuggestion() != null) {
				day.getWeatherInfo().setSuggestion(day.getWeatherInfo().getSuggestion() + ", " + rule.getMessage());
			} else {
				day.getWeatherInfo().setSuggestion(rule.getMessage());
			}
		}
		break;
	default:
		System.out.println("Not valid");	
	}
}
private boolean isRuleApplyid(Operation op, Double valueToCheck , String value) {
	switch(op){
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
		System.out.println("operaion not supported");
	}
	return false;
}
public static void main(String[] args) throws JsonMappingException, JsonProcessingException, ParseException {
	WeatherServiceImpl wd = new WeatherServiceImpl();
	String str = "{\"weatherDayData\":[{\"date\":1635066000,\"weatherInfo\":{\"temp\":284.56,\"feelsLike\":283.66,\"tempMin\":284.56,\"tempMax\":284.69,\"pressure\":1019.0,\"seaLevel\":1019.0,\"grndLevel\":1016.0,\"humidity\":73.0,\"tempKf\":-0.13},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":91},\"wind\":{\"speed\":3.59,\"deg\":184,\"gust\":9.69},\"visibility\":10000},{\"date\":1635076800,\"weatherInfo\":{\"temp\":286.43,\"feelsLike\":285.51,\"tempMin\":286.43,\"tempMax\":287.39,\"pressure\":1018.0,\"seaLevel\":1018.0,\"grndLevel\":1014.0,\"humidity\":65.0,\"tempKf\":-0.96},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":94},\"wind\":{\"speed\":5.36,\"deg\":203,\"gust\":8.5},\"visibility\":10000},{\"date\":1635087600,\"weatherInfo\":{\"temp\":287.33,\"feelsLike\":286.58,\"tempMin\":287.33,\"tempMax\":287.33,\"pressure\":1016.0,\"seaLevel\":1016.0,\"grndLevel\":1013.0,\"humidity\":68.0,\"tempKf\":0.0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":95},\"wind\":{\"speed\":4.6,\"deg\":200,\"gust\":7.77},\"visibility\":10000},{\"date\":1635098400,\"weatherInfo\":{\"temp\":286.58,\"feelsLike\":286.02,\"tempMin\":286.58,\"tempMax\":286.58,\"pressure\":1016.0,\"seaLevel\":1016.0,\"grndLevel\":1013.0,\"humidity\":78.0,\"tempKf\":0.0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":97},\"wind\":{\"speed\":3.83,\"deg\":188,\"gust\":10.14},\"visibility\":10000},{\"date\":1635109200,\"weatherInfo\":{\"temp\":286.37,\"feelsLike\":286.07,\"tempMin\":286.37,\"tempMax\":286.37,\"pressure\":1015.0,\"seaLevel\":1015.0,\"grndLevel\":1012.0,\"humidity\":89.0,\"tempKf\":0.0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.64,\"deg\":204,\"gust\":10.47},\"visibility\":10000},{\"date\":1635120000,\"weatherInfo\":{\"temp\":285.25,\"feelsLike\":284.94,\"tempMin\":285.25,\"tempMax\":285.25,\"pressure\":1015.0,\"seaLevel\":1015.0,\"grndLevel\":1012.0,\"humidity\":93.0,\"tempKf\":0.0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":62},\"wind\":{\"speed\":3.19,\"deg\":217,\"gust\":10.11},\"visibility\":10000},{\"date\":1635130800,\"weatherInfo\":{\"temp\":284.44,\"feelsLike\":284.05,\"tempMin\":284.44,\"tempMax\":284.44,\"pressure\":1014.0,\"seaLevel\":1014.0,\"grndLevel\":1011.0,\"humidity\":93.0,\"tempKf\":0.0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":2},\"wind\":{\"speed\":3.03,\"deg\":217,\"gust\":10.52},\"visibility\":10000},{\"date\":1635141600,\"weatherInfo\":{\"temp\":284.16,\"feelsLike\":283.67,\"tempMin\":284.16,\"tempMax\":284.16,\"pressure\":1015.0,\"seaLevel\":1015.0,\"grndLevel\":1011.0,\"humidity\":90.0,\"tempKf\":0.0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":3.22,\"deg\":222,\"gust\":10.87},\"visibility\":10000},{\"date\":1635152400,\"weatherInfo\":{\"temp\":285.77,\"feelsLike\":285.2,\"tempMin\":285.77,\"tempMax\":285.77,\"pressure\":1015.0,\"seaLevel\":1015.0,\"grndLevel\":1012.0,\"humidity\":81.0,\"tempKf\":0.0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":59},\"wind\":{\"speed\":3.49,\"deg\":234,\"gust\":8.9},\"visibility\":10000},{\"date\":1635163200,\"weatherInfo\":{\"temp\":288.22,\"feelsLike\":287.4,\"tempMin\":288.22,\"tempMax\":288.22,\"pressure\":1015.0,\"seaLevel\":1015.0,\"grndLevel\":1012.0,\"humidity\":62.0,\"tempKf\":0.0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":3.3,\"deg\":252,\"gust\":5.57},\"visibility\":10000}],\"city\":{\"id\":2643743,\"name\":\"London\",\"coord\":{\"lat\":51.5085,\"lon\":-0.1257},\"country\":\"GB\",\"population\":1000000,\"timezone\":3600,\"sunriseTime\":1635057597,\"sunsetTime\":1635094149}}";
	
	ObjectMapper objectMapper = new ObjectMapper();
	WeatherData data = objectMapper.readValue(str, new TypeReference<WeatherData>() {
	});
	
	wd.averageTempData(data);
}
	private WeatherData averageTempData(WeatherData weatherData) throws ParseException {
		Integer count = 3;
		WeatherData wd = new WeatherData();
		wd.setCity(weatherData.getCity());
		DateFormat dateFormat = new SimpleDateFormat("dd-MM");
		
		List<WeatherDayData> weatherDayData = weatherData.getWeatherDayData();
		Map<String, WeatherDayData> dataMap = getDataMap();
		
		
		
		for(WeatherDayData day : weatherDayData) {
			Long date = day.getDate() * 1000;
			Date d = new Date(date);
			
			String dateStr = dateFormat.format(d);
			if(dataMap.get(dateStr)!=null) {				
				WeatherDayData newWeatherDayData = dataMap.get(dateStr);
				newWeatherDayData.setClouds(day.getClouds());
				newWeatherDayData.setVisibility(day.getVisibility());
				newWeatherDayData.setWeather(day.getWeather());
				newWeatherDayData.setWind(day.getWind());
			    WeatherInfo weatherInfo = newWeatherDayData.getWeatherInfo();
				if(weatherInfo!=null) {
					if(weatherInfo.getTempMax()!=null) {
						Double avgTemp = (weatherInfo.getTempMax() + day.getWeatherInfo().getTempMax()) / 2;
						weatherInfo.setTempMax(avgTemp);
						newWeatherDayData.setWeatherInfo(weatherInfo);
					}else {
						weatherInfo.setTempMax(day.getWeatherInfo().getTempMax());
						newWeatherDayData.setWeatherInfo(weatherInfo);
					}
					if(weatherInfo.getTempMin()!=null) {
						Double avgTemp = (weatherInfo.getTempMin() + day.getWeatherInfo().getTempMin()) / 2;
						weatherInfo.setTempMin(avgTemp);
						newWeatherDayData.setWeatherInfo(weatherInfo);
					}else {
						weatherInfo.setTempMin(day.getWeatherInfo().getTempMin());
						newWeatherDayData.setWeatherInfo(weatherInfo);
					}
				}
			}
		}
		
		System.out.println(dataMap);
		List<WeatherDayData> weatherDayAvgList = new ArrayList<>();
		for (Map.Entry<String, WeatherDayData> entry : dataMap.entrySet()) {
			WeatherDayData dayData = entry.getValue();
			
			//dayData.setWeatherInfo(entry.getValue().getWeatherInfo());
			
			Date newDate = dateFormat.parse(entry.getKey());
			dayData.setDate(newDate.getTime());
			//dayData.setClouds(entry.getValue().getClouds());
			//dayData.setWeather(entry.getValue().get);
			weatherDayAvgList.add(dayData);
		}
		wd.setWeatherDayData(weatherDayAvgList);
		return wd;
	}

	private Map<String, WeatherDayData> getDataMap() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM");
		Map<String, WeatherDayData> dataMap = new HashMap<>();
		Date todayDate = new Date();
		
		for(int count = 0; count <=3; count++) {
			Instant instant = todayDate.toInstant();
			Date futureDate = Date.from(instant.plus(count, ChronoUnit.DAYS));
			WeatherDayData wd = new WeatherDayData();
			WeatherInfo info = new WeatherInfo();
			wd.setWeatherInfo(info);
			dataMap.put(dateFormat.format(futureDate), wd);
		}
		return dataMap;
	}
	private void addParams(MultiValueMap<String, String> params) {
		params.add("appid", env.getProperty("api.key"));
		params.add("cnt", "32");
	}
}
