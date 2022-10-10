package com.bustle.bustlebackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // generate all getters and setters
@AllArgsConstructor // generate all args constructor
@NoArgsConstructor // generate no args constructor
public class Response {

    private String statusCode;
    private String statusMsg;

}
