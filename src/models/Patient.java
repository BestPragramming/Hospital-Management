package models;

// SRP: Patient only stores and exposes patient data — no billing, no notifications
public class Patient {
    private int id;
    private String name;
    private int age;
    private String medicalHistory;

    public Patient(int id, String name, int age, String medicalHistory) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Patient name cannot be empty");
        if (age < 0)                        throw new IllegalArgumentException("Age cannot be negative");
        this.id             = id;
        this.name           = name;
        this.age            = age;
        this.medicalHistory = medicalHistory;
    }

    public int    getId()             { return id; }
    public String getName()           { return name; }
    public int    getAge()            { return age; }
    public String getMedicalHistory() { return medicalHistory; }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    @Override
    public String toString() {
        return "Patient{id=" + id + ", name='" + name + "', age=" + age + "}";
    }
}
