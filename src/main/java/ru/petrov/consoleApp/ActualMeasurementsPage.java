package ru.petrov.consoleApp;

import ru.petrov.model.Measurement;
import ru.petrov.model.Role;

import java.util.ArrayList;
import java.util.List;

import static ru.petrov.Initialization.*;

public class ActualMeasurementsPage {
    public static List<Measurement> getActualMeasurements() {
        System.out.print("Актуальные показания.\n");
        if (getCurrentUser().isPresent()) {
            if (getCurrentUser().get().getRole().equals(Role.USER)) {
                return measurementRepository.getLatest(getCurrentUser().get().getUuid());
            } else {
                ArrayList<Measurement> resultAllUsers = new ArrayList<>();
                userRepository.getAll().forEach(user ->
                        resultAllUsers.addAll(measurementRepository.getLatest(user.getUuid())));
                return resultAllUsers;
            }

        } else {
            return new ArrayList<>();
        }

    }


}
