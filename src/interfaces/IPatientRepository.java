package interfaces;

import models.Patient;
import java.util.List;

// DIP: high-level services depend on this abstraction, not a concrete DB class
public interface IPatientRepository {
    void save(Patient patient);
    Patient findById(int id);
    List<Patient> getAll();
}
