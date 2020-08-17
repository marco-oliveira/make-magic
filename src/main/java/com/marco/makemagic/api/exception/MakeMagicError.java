package com.marco.makemagic.api.exception;

import java.io.Serializable;

public class MakeMagicError implements Serializable {

    private final String userMessage;
    private final String devMessage;

    public MakeMagicError(String userMessage, String devMessage) {
        this.userMessage = userMessage;
        this.devMessage = devMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getDevMessage() {
        return devMessage;
    }
}
