package com.thecodeslinger.controlleroptional.service;

import com.thecodeslinger.controlleroptional.model.Album;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Emulate a service class that performs a data query on a repository.
 */
@Service
public class MusicService {

    private final static Album WINTERS_GATE = Album.builder()
            .artist("Insomnium")
            .title("Winter's Gate")
            .genre("Melodic Death Metal")
            .year(2016)
            .build();

    public Album getAlbumAsValue(boolean isNull) {
        if (isNull) {
            return null;
        } else {
            return WINTERS_GATE;
        }
    }

    public Optional<Album> getAlbumAsOptional(boolean isNull) {
        return Optional.ofNullable(getAlbumAsValue(isNull));
    }
}
