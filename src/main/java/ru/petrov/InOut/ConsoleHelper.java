package ru.petrov.InOut;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper implements Messenger {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String getString() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getInt() {
        try {
            return Integer.parseInt(getString().trim());
        } catch (NumberFormatException e) {
            System.out.println("catch2");
            return 0;
        }
    }

    public String getPassword() {
        try {
            Console con = System.console();
            if (con != null) {
                char[] ch = con.readPassword();
                return String.valueOf(ch);
            } else {
                return getString();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendString(String message) {
        System.out.printf(message);
    }
}
