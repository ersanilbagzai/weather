package com.in.weather.service;

import java.io.IOException;
import java.util.List;

public interface ICityService {

	public List<String> getCitys() throws IOException;
}
