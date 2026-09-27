package com.vinayak.journalApp.service;

import com.vinayak.journalApp.api.response.WeatherResponse;
import com.vinayak.journalApp.cache.AppCache;
import com.vinayak.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Value("${weatherstack.api.key}")
    private String apikey;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCache appCache;

    public WeatherResponse getwheather(String city){
        String finalAPI=appCache.appCache.get("AppCache.keys.WEATHER_API.toString()").replace(Placeholders.CITY,city).replace(Placeholders.API_KEY,apikey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }

}
