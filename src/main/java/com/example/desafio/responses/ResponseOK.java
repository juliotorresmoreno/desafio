package com.example.desafio.responses;

public class ResponseOK implements DefaultResponse {
    private boolean success;

    public ResponseOK() {
        this.success = true;
    }

    public boolean isSuccess() {
        return success;
    }
}
