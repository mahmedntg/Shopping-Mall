package com.example.anas.shoppingmall.utillity;

import java.io.Serializable;

/**
 * Created by mhamedsayed on 3/15/2019.
 */

public class User implements Serializable {
private String name,email,password;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
