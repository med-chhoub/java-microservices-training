package com.microservices.training.moviecatalogservice.controllers;

import com.microservices.training.moviecatalogservice.entities.Catalog;
import com.microservices.training.moviecatalogservice.entities.UserRating;
import com.microservices.training.moviecatalogservice.services.MovieInfoService;
import com.microservices.training.moviecatalogservice.services.UserRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CatalogController {

    @Autowired
    private MovieInfoService movieInfoService;

    @Autowired
    private UserRatingService userRatingService;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping(value = "/catalog/{userId}")
    public List<Catalog> getCatalog(@PathVariable("userId") String userId) {
        UserRating userRating = userRatingService.getUserRatings(userId);
        return userRating.getRatings().stream().map(rating ->  movieInfoService.getCatalogItem(rating)).collect(Collectors.toList());
    }

}
