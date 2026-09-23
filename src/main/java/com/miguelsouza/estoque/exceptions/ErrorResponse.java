package com.miguelsouza.estoque.exceptions;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

        private Integer status;
        private String message;

        public ErrorResponse() {
        }

        public ErrorResponse(Integer status, String message) {
            this.status = status;
            this.message = message;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public static ErrorResponse notFound(String message) {
            return new ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    message
            );
        }

    public static ErrorResponse businessRule(String message) {
        return new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                message
        );
    }

}
