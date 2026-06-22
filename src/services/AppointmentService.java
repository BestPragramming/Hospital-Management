package services;

import interfaces.IAppointmentRepository;
import interfaces.IDoctorRepository;
import interfaces.IPatientRepository;
import interfaces.Notifiable;
import models.Appointment;
import models.Doctor;
import models.Patient;
import java.util.List;

/**
 * SRP: Only responsible for appointment scheduling logic.
 * DIP: Depends on abstractions (IPatientRepository, IDoctorRepository,
 *      IAppointmentRepository, Notifiable) — not on concrete classes.
 */
public class AppointmentService {
    private final IPatientRepository     patientRepo;
    private final IDoctorRepository      doctorRepo;
    private final IAppointmentRepository appointmentRepo;
    private final Notifiable             notifier;
    private int nextId = 1;

    public AppointmentService(IPatientRepository patientRepo,
                               IDoctorRepository doctorRepo,
                               IAppointmentRepository appointmentRepo,
                               Notifiable notifier) {
        this.patientRepo     = patientRepo;
        this.doctorRepo      = doctorRepo;
        this.appointmentRepo = appointmentRepo;
        this.notifier        = notifier;
    }

    public Appointment scheduleAppointment(int patientId, int doctorId, String date) {
        Patient patient = patientRepo.findById(patientId);
        Doctor  doctor  = doctorRepo.findById(doctorId);

        if (patient == null) throw new IllegalArgumentException("Patient not found: " + patientId);
        if (doctor  == null) throw new IllegalArgumentException("Doctor not found: "  + doctorId);
        if (date == null || date.isEmpty()) throw new IllegalArgumentException("Date cannot be empty");

        Appointment appointment = new Appointment(nextId++, patient, doctor, date);
        appointmentRepo.save(appointment);

        notifier.sendNotification(patient.getName(),
            "Appointment scheduled with Dr. " + doctor.getName() + " on " + date);

        return appointment;
    }

    public void cancelAppointment(int appointmentId) {
        appointmentRepo.getAll().stream()
            .filter(a -> a.getId() == appointmentId)
            .findFirst()
            .ifPresent(a -> {
                a.setStatus("CANCELLED");
                notifier.sendNotification(a.getPatient().getName(),
                    "Your appointment on " + a.getDate() + " has been cancelled.");
            });
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.getAll();
    }
}
