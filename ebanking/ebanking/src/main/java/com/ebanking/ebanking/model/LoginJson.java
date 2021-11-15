package com.ebanking.ebanking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginJson {
    private String email = null;
    private String pass = null;
    private int id = 0;
    private String jwt;

    public LoginJson(@JsonProperty("email")String email,@JsonProperty("pass") String pass,
                     @JsonProperty("id") int id,@JsonProperty("jwt") String jwt) {
        this.email = email;
        this.pass = pass;
        this.id = id;
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "LoginJson{" +
                "email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", id=" + id +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public int getId() {
        return id;
    }

    public String getJwt() {
        return jwt;
    }
}
