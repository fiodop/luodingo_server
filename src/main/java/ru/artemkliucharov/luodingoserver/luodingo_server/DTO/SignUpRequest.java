package ru.artemkliucharov.luodingoserver.luodingo_server.DTO;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
}
