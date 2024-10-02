package ru.artemkliucharov.luodingoserver.luodingo_server.DTO;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}
