package repository;

import interfaces.IAppointmentRepository;
import models.Appointment;
import java.util.ArrayList;
import java.util.List;

public class InMemoryAppointmentRepository implements IAppointmentRepository {
    private final List<Appointment> appointments = new ArrayList<>();

    @Override
    public void save(Appointment appointment) {
        appointments.add(appointment);
    }

    @Override
    public List<Appointment> getAll() {
        return new ArrayList<>(appointments);
    }
}
