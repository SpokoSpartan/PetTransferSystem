package com.pwr.it.app.web.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiError {

    private String code;
    private String message;

}
