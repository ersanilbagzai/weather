package com.in.weather.wrapper.openweather;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.in.weather.wrapper.main.WeatherData;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataOpenWeather {

	private String cod;
	
	private Integer message;
	
	private Integer cnt;
	
	private List<WeatherDayDataOpenWeather> list;

	private CityOpenWeather city;
	
	public static WeatherData getGenericWeatherData(WeatherDataOpenWeather input) {
		WeatherData wd = new WeatherData();
		wd.setWeatherDayData(WeatherDayDataOpenWeather.getGenericWeatherDayDataOpenWeather(input.getList()));
		wd.setCity(CityOpenWeather.getGenericCityOpenWeather(input.getCity()));
		return wd;
	}
	
	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		String str = "{ \"cod\": \"200\", \"message\": 0, \"cnt\": 10, \"list\": [ { \"dt\": 1634968800, \"main\": { \"temp\": 282.2, \"feels_like\": 281.86, \"temp_min\": 280.73, \"temp_max\": 282.2, \"pressure\": 1025, \"sea_level\": 1025, \"grnd_level\": 1021, \"humidity\": 90, \"temp_kf\": 1.47 }, \"weather\": [ { \"id\": 802, \"main\": \"Clouds\", \"description\": \"scattered clouds\", \"icon\": \"03n\" } ], \"clouds\": { \"all\": 28 }, \"wind\": { \"speed\": 1.37, \"deg\": 219, \"gust\": 2.17 }, \"visibility\": 10000, \"pop\": 0, \"sys\": { \"pod\": \"n\" }, \"dt_txt\": \"2021-10-23 06:00:00\" }, { \"dt\": 1634979600, \"main\": { \"temp\": 282.45, \"feels_like\": 281.57, \"temp_min\": 282.45, \"temp_max\": 282.95, \"pressure\": 1025, \"sea_level\": 1025, \"grnd_level\": 1023, \"humidity\": 83, \"temp_kf\": -0.5 }, \"weather\": [ { \"id\": 802, \"main\": \"Clouds\", \"description\": \"scattered clouds\", \"icon\": \"03d\" } ], \"clouds\": { \"all\": 36 }, \"wind\": { \"speed\": 1.95, \"deg\": 219, \"gust\": 3.6 }, \"visibility\": 10000, \"pop\": 0, \"sys\": { \"pod\": \"d\" }, \"dt_txt\": \"2021-10-23 09:00:00\" }, { \"dt\": 1634990400, \"main\": { \"temp\": 285.03, \"feels_like\": 284.02, \"temp_min\": 285.03, \"temp_max\": 286.44, \"pressure\": 1025, \"sea_level\": 1025, \"grnd_level\": 1022, \"humidity\": 67, \"temp_kf\": -1.41 }, \"weather\": [ { \"id\": 802, \"main\": \"Clouds\", \"description\": \"scattered clouds\", \"icon\": \"03d\" } ], \"clouds\": { \"all\": 31 }, \"wind\": { \"speed\": 3.69, \"deg\": 210, \"gust\": 6.29 }, \"visibility\": 10000, \"pop\": 0, \"sys\": { \"pod\": \"d\" }, \"dt_txt\": \"2021-10-23 12:00:00\" }, { \"dt\": 1635001200, \"main\": { \"temp\": 286.8, \"feels_like\": 285.68, \"temp_min\": 286.8, \"temp_max\": 286.8, \"pressure\": 1023, \"sea_level\": 1023, \"grnd_level\": 1020, \"humidity\": 56, \"temp_kf\": 0 }, \"weather\": [ { \"id\": 803, \"main\": \"Clouds\", \"description\": \"broken clouds\", \"icon\": \"04d\" } ], \"clouds\": { \"all\": 74 }, \"wind\": { \"speed\": 3.81, \"deg\": 211, \"gust\": 6.03 }, \"visibility\": 10000, \"pop\": 0, \"sys\": { \"pod\": \"d\" }, \"dt_txt\": \"2021-10-23 15:00:00\" }, { \"dt\": 1635012000, \"main\": { \"temp\": 284.66, \"feels_like\": 283.72, \"temp_min\": 284.66, \"temp_max\": 284.66, \"pressure\": 1023, \"sea_level\": 1023, \"grnd_level\": 1020, \"humidity\": 71, \"temp_kf\": 0 }, \"weather\": [ { \"id\": 804, \"main\": \"Clouds\", \"description\": \"overcast clouds\", \"icon\": \"04n\" } ], \"clouds\": { \"all\": 86 }, \"wind\": { \"speed\": 3.09, \"deg\": 192, \"gust\": 8.63 }, \"visibility\": 10000, \"pop\": 0, \"sys\": { \"pod\": \"n\" }, \"dt_txt\": \"2021-10-23 18:00:00\" }, { \"dt\": 1635022800, \"main\": { \"temp\": 284.58, \"feels_like\": 283.58, \"temp_min\": 284.58, \"temp_max\": 284.58, \"pressure\": 1023, \"sea_level\": 1023, \"grnd_level\": 1020, \"humidity\": 69, \"temp_kf\": 0 }, \"weather\": [ { \"id\": 803, \"main\": \"Clouds\", \"description\": \"broken clouds\", \"icon\": \"04n\" } ], \"clouds\": { \"all\": 55 }, \"wind\": { \"speed\": 2.44, \"deg\": 202, \"gust\": 8.48 }, \"visibility\": 10000, \"pop\": 0, \"sys\": { \"pod\": \"n\" }, \"dt_txt\": \"2021-10-23 21:00:00\" }, { \"dt\": 1635033600, \"main\": { \"temp\": 283.53, \"feels_like\": 282.61, \"temp_min\": 283.53, \"temp_max\": 283.53, \"pressure\": 1022, \"sea_level\": 1022, \"grnd_level\": 1019, \"humidity\": 76, \"temp_kf\": 0 }, \"weather\": [ { \"id\": 803, \"main\": \"Clouds\", \"description\": \"broken clouds\", \"icon\": \"04n\" } ], \"clouds\": { \"all\": 51 }, \"wind\": { \"speed\": 2.5, \"deg\": 168, \"gust\": 8.56 }, \"visibility\": 10000, \"pop\": 0, \"sys\": { \"pod\": \"n\" }, \"dt_txt\": \"2021-10-24 00:00:00\" }, { \"dt\": 1635044400, \"main\": { \"temp\": 283.05, \"feels_like\": 281.55, \"temp_min\": 283.05, \"temp_max\": 283.05, \"pressure\": 1020, \"sea_level\": 1020, \"grnd_level\": 1017, \"humidity\": 78, \"temp_kf\": 0 }, \"weather\": [ { \"id\": 803, \"main\": \"Clouds\", \"description\": \"broken clouds\", \"icon\": \"04n\" } ], \"clouds\": { \"all\": 74 }, \"wind\": { \"speed\": 2.98, \"deg\": 180, \"gust\": 10.56 }, \"visibility\": 10000, \"pop\": 0, \"sys\": { \"pod\": \"n\" }, \"dt_txt\": \"2021-10-24 03:00:00\" }, { \"dt\": 1635055200, \"main\": { \"temp\": 282.63, \"feels_like\": 281.02, \"temp_min\": 282.63, \"temp_max\": 282.63, \"pressure\": 1020, \"sea_level\": 1020, \"grnd_level\": 1016, \"humidity\": 78, \"temp_kf\": 0 }, \"weather\": [ { \"id\": 803, \"main\": \"Clouds\", \"description\": \"broken clouds\", \"icon\": \"04n\" } ], \"clouds\": { \"all\": 73 }, \"wind\": { \"speed\": 3.03, \"deg\": 183, \"gust\": 10.62 }, \"visibility\": 10000, \"pop\": 0, \"sys\": { \"pod\": \"n\" }, \"dt_txt\": \"2021-10-24 06:00:00\" }, { \"dt\": 1635066000, \"main\": { \"temp\": 284.65, \"feels_like\": 283.71, \"temp_min\": 284.65, \"temp_max\": 284.65, \"pressure\": 1019, \"sea_level\": 1019, \"grnd_level\": 1016, \"humidity\": 71, \"temp_kf\": 0 }, \"weather\": [ { \"id\": 804, \"main\": \"Clouds\", \"description\": \"overcast clouds\", \"icon\": \"04d\" } ], \"clouds\": { \"all\": 85 }, \"wind\": { \"speed\": 3.61, \"deg\": 182, \"gust\": 10.06 }, \"visibility\": 10000, \"pop\": 0, \"sys\": { \"pod\": \"d\" }, \"dt_txt\": \"2021-10-24 09:00:00\" } ], \"city\": { \"id\": 2643743, \"name\": \"London\", \"coord\": { \"lat\": 51.5085, \"lon\": -0.1257 }, \"country\": \"GB\", \"population\": 1000000, \"timezone\": 3600, \"sunrise\": 1634971092, \"sunset\": 1635007868 } }";
		ObjectMapper objectMapper = new ObjectMapper();
		WeatherDataOpenWeather weather = objectMapper.readValue(str, new TypeReference<WeatherDataOpenWeather>() {
		});

		WeatherData genericWeatherData = getGenericWeatherData(weather);
		System.out.println(new Gson().toJson(genericWeatherData));
	}
	
}
