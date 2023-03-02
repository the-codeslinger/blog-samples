package com.thecodeslinger.validationerrormessage.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MusicTrack {
    @Min(1)
    private int track;
    
    @NotNull
    @NotBlank
    private String title;
}
