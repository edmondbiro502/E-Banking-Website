package com.ebanking.ebanking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Client {
    private int id;
    private String email;
    private String lastName;
    private String firstName;
    private String username;
    private String password;
    private int admin;
    private String jwt;

    public Client(@JsonProperty("firstName")String firstName,@JsonProperty("lastName")String lastName,
                  @JsonProperty("username")String username,@JsonProperty("password")String password,
                  @JsonProperty("email")String email, @JsonProperty("admin")int admin,@JsonProperty("jwt")String jwt) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.jwt = jwt;
    }

    public int getAdmin() {
        return admin;
    }

    public String getJwt() {
        return jwt;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Client(int id, String email, String firstName, String lastName, String username, String password, int admin, String jwt) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.jwt = jwt;

    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
