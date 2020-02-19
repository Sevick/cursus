package com.fbytes.cursus.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserData {
    private List<String> role;
    private String password;
}