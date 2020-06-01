package com.zanitech.danielius.exceptions;

import lombok.Data;

@Data
public class BuildingErrorResponse {

    private int status;

    private String message;

    private long timeStamp;
}
