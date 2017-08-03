package com.epam.company.model.util;

/**
 * Created by @belrbeZ
 */
public class SimpleResponse {

    private String message;

    public SimpleResponse() {
        message = "Response";
    }

    public SimpleResponse(String response) {
        message = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
