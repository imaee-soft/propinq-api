package com.imaee.propinq.favorites.controllers.interfaces;

import com.imaee.propinq.favorites.controllers.requests.FavoriteRequest;
import com.imaee.propinq.favorites.controllers.responses.FavoriteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/favorites")
public interface IFavoriteController {

        @PostMapping
        @ResponseStatus(HttpStatus.OK)
        void addFavorite(@RequestBody FavoriteRequest request);

        @GetMapping("/all/{userID}")
        @ResponseStatus(HttpStatus.OK)
        List<FavoriteResponse> getUserFavorites(@PathVariable UUID userID);

        @GetMapping("/user/{userID}/property")
        @ResponseStatus(HttpStatus.OK)
        List<FavoriteResponse> getFavoritesByProperty(@PathVariable UUID userID);

        @GetMapping("/user/{userID}/building")
        @ResponseStatus(HttpStatus.OK)
        List<FavoriteResponse> getFavoritesByBuilding(@PathVariable UUID userID);

        @DeleteMapping("/{favoriteID}")
        ResponseEntity<Void> removeFavorite(@PathVariable UUID favoriteID);
}
