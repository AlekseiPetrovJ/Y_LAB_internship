package ru.petrov.in;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    /**
     *@return Read a line of text by BufferedReader.readLine
     */
    public String getString() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return int parsed from a line of text by BufferedReader.readLine
     * @throws NumberFormatException
     */
    public int getInt() {
        try {
            return Integer.parseInt(getString().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     *@return  A character array containing the password or passphrase read
     *          from the console, not including any line-termination characters
     *          if System.console available,
     *          or {@link #getString()}
     */
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
}
