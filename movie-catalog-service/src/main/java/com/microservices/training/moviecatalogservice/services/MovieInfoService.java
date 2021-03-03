package com.microservices.training.moviecatalogservice.services;

import com.microservices.training.moviecatalogservice.entities.Catalog;
import com.microservices.training.moviecatalogservice.entities.Movie;
import com.microservices.training.moviecatalogservice.entities.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackCatalogItem",
            threadPoolKey = "movieInfoPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10"),
            },
    commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            @HystrixProperty(name = "metrics.rollingStats.errorThresholdPercentage", value = "50") })
    public Catalog getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movies-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new Catalog(movie.getName(),"desc",rating.getRating());
    }

    public Catalog getFallBackCatalogItem(Rating rating) {
        return new Catalog("No Movie","",rating.getRating());
    }
}
