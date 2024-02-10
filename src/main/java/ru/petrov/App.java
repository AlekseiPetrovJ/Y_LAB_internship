package ru.petrov;

import ru.petrov.consoleApp.WelcomePage;

public class App {
    public static void main(String[] args) {
        InitializationDb.migration();
        Initialization.init();
        WelcomePage.showMenu();

    }
}
