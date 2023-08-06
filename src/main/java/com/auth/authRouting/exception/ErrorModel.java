package com.auth.authRouting.exception;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class ErrorModel {

    private Integer statusCode;
    private String message;
    private Date timestamp;
}
