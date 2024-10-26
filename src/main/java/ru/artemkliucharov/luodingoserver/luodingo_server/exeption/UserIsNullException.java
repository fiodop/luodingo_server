package ru.artemkliucharov.luodingoserver.luodingo_server.exeption;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserIsNullException extends Exception{
    String message;
}
