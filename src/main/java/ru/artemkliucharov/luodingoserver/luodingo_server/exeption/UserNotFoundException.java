package ru.artemkliucharov.luodingoserver.luodingo_server.exeption;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserNotFoundException extends Exception{
    private String message;
}
