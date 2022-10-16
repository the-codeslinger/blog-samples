package org.thecodeslinger.validationerrormessage.dtos;

import java.util.List;

/**
 * Simple object for structuring error return codes.
 *
 * @param code     HTTP status code.
 * @param messages A list of errors that have occurred.
 */
public record ErrorMessage(int code, List<String> messages) {
}
