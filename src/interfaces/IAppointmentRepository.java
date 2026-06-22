package interfaces;

import models.Appointment;
import java.util.List;

public interface IAppointmentRepository {
    void save(Appointment appointment);
    List<Appointment> getAll();
}
