package models;

// SRP: Appointment only stores appointment data — scheduling logic lives in AppointmentService
public class Appointment {
    private int    id;
    private Patient patient;
    private Doctor  doctor;
    private String  date;
    private String  status;   // SCHEDULED | COMPLETED | CANCELLED

    public Appointment(int id, Patient patient, Doctor doctor, String date) {
        this.id      = id;
        this.patient = patient;
        this.doctor  = doctor;
        this.date    = date;
        this.status  = "SCHEDULED";
    }

    public int     getId()      { return id; }
    public Patient getPatient() { return patient; }
    public Doctor  getDoctor()  { return doctor; }
    public String  getDate()    { return date; }
    public String  getStatus()  { return status; }
    public void    setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Appointment{id=" + id +
               ", patient='" + patient.getName() +
               "', doctor='"  + doctor.getName() +
               "', date='"    + date +
               "', status='"  + status + "'}";
    }
}
