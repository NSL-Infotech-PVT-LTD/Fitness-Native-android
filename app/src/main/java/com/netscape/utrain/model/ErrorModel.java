package com.netscape.utrain.model;

import java.util.List;

public class ErrorModel {
    private int code;
    private ErrorMessageBean error_message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ErrorMessageBean getError_message() {
        return error_message;
    }

    public void setError_message(ErrorMessageBean error_message) {
        this.error_message = error_message;
    }

    public static class ErrorMessageBean {
        private List<String> message;

        public List<String> getMessage() {
            return message;
        }

        public void setMessage(List<String> message) {
            this.message = message;
        }
    }
}
