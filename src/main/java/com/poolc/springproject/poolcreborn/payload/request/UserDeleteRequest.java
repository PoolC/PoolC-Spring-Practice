package com.poolc.springproject.poolcreborn.payload.request;

import com.poolc.springproject.poolcreborn.validator.CorrectDeleteMessage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class UserDeleteRequest {

    @CorrectDeleteMessage
    private String message;

    public UserDeleteRequest() {}

    public UserDeleteRequest(String message) {
        this.message = message;
    }
}
