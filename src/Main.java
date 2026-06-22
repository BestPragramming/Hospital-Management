import billing.CashBilling;
import billing.InsuranceBilling;
import interfaces.Notifiable;
import models.*;
import notification.EmailNotification;
import notification.SMSNotification;
import repository.*;
import services.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // 1. Setup repositories  (DIP: program to interfaces, not classes)
        // ----------------------------------------------------------------
        InMemoryPatientRepository     patientRepo     = new InMemoryPatientRepository();
        InMemoryDoctorRepository      doctorRepo      = new InMemoryDoctorRepository();
        InMemoryAppointmentRepository appointmentRepo = new InMemoryAppointmentRepository();

        // ----------------------------------------------------------------
        // 2. Create patients  (SRP: Patient only holds patient data)
        // ----------------------------------------------------------------
        Patient alice = new Patient(1, "Alice Uwase",   30, "None");
        Patient bonfils   = new Patient(2, "Bonfils Mugisha",   45, "Diabetes");
        Patient clara = new Patient(3, "Clara Ineza",   28, "Asthma");
        patientRepo.save(alice);
        patientRepo.save(bonfils);
        patientRepo.save(clara);

        // ----------------------------------------------------------------
        // 3. Create doctors  (LSP: Specialist extends Doctor; usable
        //    anywhere Doctor is expected without breaking behaviour)
        // ----------------------------------------------------------------
        Doctor    generalDoctor = new Doctor(1, "Dr. Kamau",  "General Practice");
        Specialist specialist   = new Specialist(2, "Dr. Nkosi", "Cardiology",
                                                 "Interventional Cardiology", 12);
        doctorRepo.save(generalDoctor);
        doctorRepo.save(specialist);   // stored as Doctor — LSP holds

        // ----------------------------------------------------------------
        // 4. Notification channels  (ISP: Notifiable has ONE focused method)
        // ----------------------------------------------------------------
        Notifiable email = new EmailNotification();
        Notifiable sms   = new SMSNotification();

        // ----------------------------------------------------------------
        // 5. Appointment service  (DIP: injected interfaces, not concrete classes)
        // ----------------------------------------------------------------
        AppointmentService appointmentService = new AppointmentService(
            patientRepo, doctorRepo, appointmentRepo, email
        );

        System.out.println("=== SCHEDULING APPOINTMENTS ===");
        Appointment a1 = appointmentService.scheduleAppointment(1, 1, "2026-06-20");
        Appointment a2 = appointmentService.scheduleAppointment(2, 2, "2026-06-21");
        Appointment a3 = appointmentService.scheduleAppointment(3, 1, "2026-06-22");

        System.out.println("\n--- Cancelling appointment #" + a3.getId() + " ---");
        appointmentService.cancelAppointment(a3.getId());

        // ----------------------------------------------------------------
        // 6. Billing  (OCP: switch strategy without changing BillingService)
        // ----------------------------------------------------------------
        System.out.println("\n=== BILLING ===");
        BillingService cashService      = new BillingService(new CashBilling());
        BillingService insuranceService = new BillingService(new InsuranceBilling(80));

        Bill bill1 = cashService.generateBill(alice, 500.00);
        Bill bill2 = insuranceService.generateBill(bonfils, 1200.00);
        System.out.println(bill1);
        System.out.println(bill2);

        cashService.payBill(bill1.getId());

        // ----------------------------------------------------------------
        // 7. Multi-channel notifications  (OCP + DIP + ISP)
        // ----------------------------------------------------------------
        System.out.println("\n=== MULTI-CHANNEL NOTIFICATIONS ===");
        NotificationService notifier = new NotificationService(Arrays.asList(email, sms));
        notifier.notifyAll("Alice Uwase",  "Your test results are ready for pickup.");
        notifier.notifyAll("Bonfils Mugisha",  "Please arrive 15 minutes early tomorrow.");

        // ----------------------------------------------------------------
        // 8. Summary
        // ----------------------------------------------------------------
        System.out.println("\n=== ALL APPOINTMENTS ===");
        appointmentService.getAllAppointments().forEach(System.out::println);

        System.out.println("\n=== ALL BILLS ===");
        cashService.getAllBills().forEach(System.out::println);
        insuranceService.getAllBills().forEach(System.out::println);
    }
}
