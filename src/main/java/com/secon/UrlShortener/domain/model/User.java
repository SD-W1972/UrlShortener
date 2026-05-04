package com.secon.UrlShortener.domain.model;

import java.util.List;
import com.secon.UrlShortener.domain.model.enums.*;

public class User{

    private Long id;
    private String email;
    private String password;
    private UserType userType;
    private List<Url> urls;

    public User(String email, String password, UserType userType, List<Url> urls) {
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.urls = urls;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    public Long getId(){
        return this.id;
    }

    public String getEmail(){
        return this.email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public UserType getUserType(){
        return this.userType;
    }

    public void setUserType(UserType userType){
        this.userType = userType;
    }

}
