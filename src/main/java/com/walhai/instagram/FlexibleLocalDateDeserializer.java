package com.walhai.instagram;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FlexibleLocalDateDeserializer extends JsonDeserializer<LocalDate> {
    private static final DateTimeFormatter FORMATTER1 = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final DateTimeFormatter FORMATTER2 = DateTimeFormatter.ofPattern("d/MM/yyyy");

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String date = p.getText();
        try {
            return LocalDate.parse(date, FORMATTER1);
        } catch (DateTimeParseException e) {
            // Try the second format if the first fails
            try {
                return LocalDate.parse(date, FORMATTER2);
            } catch (DateTimeParseException e2) {
                // Log or rethrow if neither format works
                throw new IOException("Failed to parse LocalDate with supported formats: " + date, e2);
            }
        }
    }
}
