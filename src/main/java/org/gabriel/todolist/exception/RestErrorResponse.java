package org.gabriel.todolist.exception;

import lombok.Getter;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


@Getter
public class RestErrorResponse {

    private final String error;
    private final String message;
    private final String timestamp;

    private RestErrorResponse(RestErrorResponseBuilder builder) {
        this.error = builder.error;
        this.message = builder.message;
        this.timestamp = builder.timestamp;
    }

    public static class RestErrorResponseBuilder {
        private String error;
        private String message;
        private final String timestamp = currentTimestamp();


        public RestErrorResponseBuilder setError(String error) {
            this.error = error;
            return this;
        }

        public RestErrorResponseBuilder setMessage(String message) {
            this.message = message;
            return this;

        }

        public RestErrorResponse build() {
            return new RestErrorResponse(this);
        }

        private String currentTimestamp() {

            return DateTimeFormatter.ISO_INSTANT
                    .withZone(ZoneOffset.UTC)
                    .format(Instant.now());
        }
    }
}
