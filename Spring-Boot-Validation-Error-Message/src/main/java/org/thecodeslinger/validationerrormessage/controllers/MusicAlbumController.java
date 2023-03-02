package com.thecodeslinger.validationerrormessage.controllers;

import javax.validation.Valid;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.thecodeslinger.validationerrormessage.dtos.MusicAlbum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MusicAlbumController {
    
    @PostMapping(path = "/albums", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public void upload(@Valid @RequestBody MusicAlbum album) {
        log.info("User uploaded album '{}'.", album);
    }
}
