package com.thecodeslinger.validationerrormessage.dtos.validators;

import com.thecodeslinger.validationerrormessage.dtos.MusicAlbum;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom validation annotation for a class. Uses {@link AlbumValidator} as the validation
 * implementation.
 *
 * @see MusicAlbum
 */
@Documented
@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = AlbumValidator.class)
public @interface Album {

    String message() default "The album does not match our level of quality.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
