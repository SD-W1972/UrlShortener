package com.secon.UrlShortener.domain.model;

import java.util.Objects;
import com.secon.UrlShortener.domain.model.enums.*;

public class User{

    private Long id;
    private String email;
    private String password;
    private UserType userType;

    public User(String email, String password, UserType userType){
        this.id = null;
        this.email = email;
        this.password = password;
        this.userType = userType;
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;

        if (this.id != null && user.id != null) {
            return Objects.equals(this.id, user.id);
        }

        return Objects.equals(this.email, user.email);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hash(id);
        }
        return Objects.hash(email);
    }
}
