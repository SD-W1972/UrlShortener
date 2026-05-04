package com.secon.UrlShortener.domain.model.enums;

public enum UserType{
    ADMIN(1),
    CLIENT(2);

    private final int code;

    private UserType(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}