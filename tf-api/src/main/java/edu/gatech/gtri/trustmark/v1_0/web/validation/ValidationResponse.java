package edu.gatech.gtri.trustmark.v1_0.web.validation;

import static java.util.Objects.requireNonNull;

public class ValidationResponse {

    private final Object responseBody;
    private final int responseStatus;

    public ValidationResponse(
            final Object responseBody,
            final int responseStatus) {

        requireNonNull(responseBody);

        this.responseBody = responseBody;
        this.responseStatus = responseStatus;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public int getResponseStatus() {
        return responseStatus;
    }
}
