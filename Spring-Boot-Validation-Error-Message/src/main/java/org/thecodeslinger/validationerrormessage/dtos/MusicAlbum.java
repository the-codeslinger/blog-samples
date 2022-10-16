package org.thecodeslinger.validationerrormessage.dtos;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.thecodeslinger.validationerrormessage.dtos.validators.Album;

@Data
@Album
@NoArgsConstructor
public class MusicAlbum {
    @NotNull
    @NotBlank
    private String artist;
    
    @NotNull
    @NotBlank
    private String album;
    
    @NotNull
    @NotBlank
    private String genre;
    
    @Min(1900)
    private int year;

    @NotNull
    private List<MusicTrack> tracks;
}