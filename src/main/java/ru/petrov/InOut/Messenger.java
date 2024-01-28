package ru.petrov.InOut;

public interface Messenger {
    String getString();

    int getInt();

    String getPassword();

    void sendString(String message);
}
