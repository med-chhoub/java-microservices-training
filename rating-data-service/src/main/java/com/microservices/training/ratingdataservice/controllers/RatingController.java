package com.microservices.training.ratingdataservice.controllers;

import com.microservices.training.ratingdataservice.entities.Rating;
import com.microservices.training.ratingdataservice.entities.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class RatingController {

    @GetMapping(value = "/ratingsdata/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @GetMapping(value = "/ratingsdata/users/{userId}")
    public UserRating getUsersRating(@PathVariable("userId") String userId) {
        List<Rating> ratings = Arrays.asList(
                new Rating("15", 4),
                new Rating("20", 2)
        );
        UserRating userRating = new UserRating();
        userRating.setRatings(ratings);
        return userRating;
    }

}