package com.thecodeslinger.validationerrormessage.dtos.validators;

import com.thecodeslinger.validationerrormessage.dtos.MusicAlbum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Custom validator that operates on a class level. It has access to the complete
 * {@link MusicAlbum} instance instead of only a single field.
 * <p>
 * The sample shows how to set a custom error message for a specific field and how to
 * use the default message set in the {@link Album} annotation.
 */
public class AlbumValidator implements ConstraintValidator<Album, MusicAlbum> {

    @Override
    public boolean isValid(MusicAlbum album, ConstraintValidatorContext context) {

        if ("Insomnium".equals(album.getArtist()) &&
                !"Melodic Death Metal".equals(album.getGenre())) {
            // Disable the default error message defined in `@interface Album`.
            context.disableDefaultConstraintViolation();
            // Set a custom error message. Use value defined in messages.properties file.
            context.buildConstraintViolationWithTemplate("{album.error.invalid-genre}")
                    .addPropertyNode("genre")
                    .addConstraintViolation();
            // When not enclosing the string in curly braces, the string will be the exact
            // error message.
            context.buildConstraintViolationWithTemplate(
                        "'Melodic Death Metal' is expected for artist Insomnium.")
                    .addPropertyNode("genre")
                    .addConstraintViolation();
            return false;
        } else if (album.getTracks().isEmpty()) {
            // Use default error message defined in `@interface Album`.
            return false;
        }

        return true;
    }
}
