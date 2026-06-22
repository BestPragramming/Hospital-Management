package repository;

import interfaces.IDoctorRepository;
import models.Doctor;
import java.util.ArrayList;
import java.util.List;

public class InMemoryDoctorRepository implements IDoctorRepository {
    private final List<Doctor> doctors = new ArrayList<>();

    @Override
    public void save(Doctor doctor) {
        doctors.add(doctor);
    }

    @Override
    public Doctor findById(int id) {
        return doctors.stream()
                      .filter(d -> d.getId() == id)
                      .findFirst()
                      .orElse(null);
    }

    @Override
    public List<Doctor> getAll() {
        return new ArrayList<>(doctors);
    }
}
