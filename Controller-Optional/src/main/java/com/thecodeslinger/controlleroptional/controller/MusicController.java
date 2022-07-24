package com.thecodeslinger.controlleroptional.controller;

import com.thecodeslinger.controlleroptional.model.Album;
import com.thecodeslinger.controlleroptional.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Provides two endpoints that return a POJO or nothing. {@code /value} returns the raw the POJO,
 * and {@code /optional} returns the same value wrapped in an optional.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    /**
     * <ul>
     *   <li>When {@code isNull=false} then a JSON object is returned.</li>
     *   <li>When {@code isNull=true} then nothing is returned.</li>
     * </ul>
     */
    @GetMapping(path = "/value")
    Album getValue(@RequestParam("isNull") boolean isNull) {
        log.info("Request album value (is null: {})", isNull);
        return musicService.getAlbumAsValue(isNull);
    }

    /**
     * <ul>
     *   <li>When {@code isNull=false} then a JSON object is returned. This behaves in the same way
     *   as {@link MusicController#getValue(boolean)}.</li>
     *   <li>When {@code isNull=true} then the string "null" is returned. This is different from
     * {@link MusicController#getValue(boolean)}.</li>
     * </ul>
     */
    @GetMapping(path = "/optional")
    Optional<Album> getOptional(@RequestParam("isNull") boolean isNull) {
        log.info("Request album optional (is null: {})", isNull);
        return musicService.getAlbumAsOptional(isNull);
    }
}
