package com.example.desafio.responses;

public class ResponseError implements DefaultResponse {
    private boolean success;
    private String message;

    public ResponseError() {
        this.success = false;
        this.message = "Ha ocurrido un error interno del servidor!";
    }

    public ResponseError(String error) {
        this.success = false;
        this.message = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
