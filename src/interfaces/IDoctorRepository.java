package interfaces;

import models.Doctor;
import java.util.List;

public interface IDoctorRepository {
    void save(Doctor doctor);
    Doctor findById(int id);
    List<Doctor> getAll();
}
