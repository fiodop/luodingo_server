package ru.artemkliucharov.luodingoserver.luodingo_server.exeption;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CardIsNullException extends Exception{
    String message;
}
