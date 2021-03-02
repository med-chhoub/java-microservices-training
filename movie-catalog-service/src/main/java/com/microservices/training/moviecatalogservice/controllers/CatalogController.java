package com.microservices.training.moviecatalogservice.controllers;

import com.microservices.training.moviecatalogservice.entities.Catalog;
import com.microservices.training.moviecatalogservice.entities.Movie;
import com.microservices.training.moviecatalogservice.entities.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping(value = "/catalog/{userId}")
    public List<Catalog> getCatalog(@PathVariable("userId") String userId) {

        UserRating userRating = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
        return userRating.getRatings().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://movies-info-service/movies/" + rating.getMovieId(), Movie.class);
            return new Catalog(movie.getName(),"desc",rating.getRating());
        }).collect(Collectors.toList());
    }

}
