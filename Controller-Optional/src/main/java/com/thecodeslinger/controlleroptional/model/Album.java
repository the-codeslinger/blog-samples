package com.thecodeslinger.controlleroptional.model;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    private String artist;
    private String title;
    private String genre;
    private int year;
}
