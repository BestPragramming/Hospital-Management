package repository;

import interfaces.IPatientRepository;
import models.Patient;
import java.util.ArrayList;
import java.util.List;

// DIP: concrete implementation of IPatientRepository abstraction
// Swap this for MySQLPatientRepository without changing any service
public class InMemoryPatientRepository implements IPatientRepository {
    private final List<Patient> patients = new ArrayList<>();

    @Override
    public void save(Patient patient) {
        patients.add(patient);
    }

    @Override
    public Patient findById(int id) {
        return patients.stream()
                       .filter(p -> p.getId() == id)
                       .findFirst()
                       .orElse(null);
    }

    @Override
    public List<Patient> getAll() {
        return new ArrayList<>(patients);
    }
}
