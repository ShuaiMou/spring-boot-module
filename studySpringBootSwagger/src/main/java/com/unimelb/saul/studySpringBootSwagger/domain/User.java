package com.unimelb.saul.studySpringBootSwagger.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable{

    @JsonProperty("account")
    private String email;

    private String username;

    @JsonIgnore
    private String password;

    public User(String email,  String password, String username) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
