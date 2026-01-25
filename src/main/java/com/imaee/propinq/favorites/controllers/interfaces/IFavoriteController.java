package com.imaee.propinq.favorites.controllers.interfaces;

import com.imaee.propinq.favorites.controllers.requests.FavoriteRequest;
import com.imaee.propinq.favorites.controllers.responses.FavoriteEntity;
import com.imaee.propinq.favorites.controllers.responses.FavoriteResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/v1/favorites")
public interface IFavoriteController {

        @PostMapping
        @ResponseStatus(OK)
        FavoriteResponse addFavorite(@RequestBody FavoriteRequest request);

        @GetMapping("/buildings")
        @ResponseStatus(OK)
        Page<FavoriteEntity> getFavoriteBuildings(
                @RequestParam(defaultValue = "0", name = "page") Integer pageNumber,
                @RequestParam(defaultValue = "6", name = "size") Integer pageSize
        );

        @GetMapping("/properties")
        @ResponseStatus(OK)
        Page<FavoriteEntity> getFavoriteProperties(
                @RequestParam(defaultValue = "0", name = "page") Integer pageNumber,
                @RequestParam(defaultValue = "6", name = "size") Integer pageSize
        );

        @DeleteMapping("/{favoriteID}")
        @ResponseStatus(NO_CONTENT)
        ResponseEntity<Void> removeFavorite(@PathVariable UUID favoriteID);
}