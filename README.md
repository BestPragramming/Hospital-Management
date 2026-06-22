# 🏥 Hospital Management System — SOLID Principles

> **Assignment #2** | Object-Oriented Design | Java Implementation

A refactored Hospital Management System built to demonstrate all five **SOLID** object-oriented design principles. The system manages patients, doctors, appointments, billing, and notifications through a clean, layered architecture.

---

## 👥 Group Members

| Name | Student ID |
|------|------------|
| Mucyo Joel | 26606 |
| Shema Owen | 26253 |
| Aze Solide | 27396 |

---

## 📋 Assignment Requirements

- [x] Apply all five SOLID principles
- [x] Create UML Class Diagrams
- [x] Implement in Java
- [x] Explain which principle was applied and where

---

## 🗂️ Project Structure

```
HospitalManagement/
├── src/
│   ├── Main.java
│   ├── models/
│   │   ├── Patient.java
│   │   ├── Doctor.java
│   │   ├── Specialist.java
│   │   ├── Appointment.java
│   │   └── Bill.java
│   ├── interfaces/
│   │   ├── Notifiable.java
│   │   ├── IBillingStrategy.java
│   │   ├── IPatientRepository.java
│   │   ├── IDoctorRepository.java
│   │   └── IAppointmentRepository.java
│   ├── repository/
│   │   ├── InMemoryPatientRepository.java
│   │   ├── InMemoryDoctorRepository.java
│   │   └── InMemoryAppointmentRepository.java
│   ├── services/
│   │   ├── AppointmentService.java
│   │   ├── BillingService.java
│   │   └── NotificationService.java
│   ├── billing/
│   │   ├── CashBilling.java
│   │   └── InsuranceBilling.java
│   └── notification/
│       ├── EmailNotification.java
│       └── SMSNotification.java
├── SOLID_EXPLANATION.txt
└── UML.txt
```

---

## ⚙️ How to Run

### Requirements
- Java 17+
- VS Code with Java Extension Pack *(or any Java IDE)*

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/BestPragramming/Hospital-Management.git

# 2. Open in VS Code
cd Hospital-Management
code .

# 3. Run Main.java
# Via VS Code: open src/Main.java → click ▶ Run
# Via terminal:
javac -d out src/**/*.java src/Main.java
java -cp out Main
```

### Expected Output

```
=== SCHEDULING APPOINTMENTS ===
[EMAIL] To: Alice Uwase | Appointment scheduled with Dr. Dr. Kamau on 2026-06-20
[EMAIL] To: Bonfils Mugisha | Appointment scheduled with Dr. Dr. Nkosi on 2026-06-21
[EMAIL] To: Clara Ineza | Appointment scheduled with Dr. Dr. Kamau on 2026-06-22

--- Cancelling appointment #3 ---
[EMAIL] To: Clara Ineza | Your appointment on 2026-06-22 has been cancelled.

=== BILLING ===
Bill{id=1, patient='Alice Uwase', amount=$500.00, type='CASH', paid=false}
Bill{id=1, patient='Bonfils Mugisha', amount=$240.00, type='INSURANCE (80.0% covered)', paid=false}
  >> Bill #1 marked as PAID.

=== MULTI-CHANNEL NOTIFICATIONS ===
[EMAIL] To: Alice Uwase | Your test results are ready for pickup.
[SMS]   To: Alice Uwase | Your test results are ready for pickup.
[EMAIL] To: Bonfils Mugisha | Please arrive 15 minutes early tomorrow.
[SMS]   To: Bonfils Mugisha | Please arrive 15 minutes early tomorrow.

=== ALL APPOINTMENTS ===
...

=== ALL BILLS ===
...
```

---

## 🧱 SOLID Principles Applied

### S — Single Responsibility Principle
> *A class should have only one reason to change.*

| Class | Responsibility |
|-------|---------------|
| `Patient.java` | Stores patient data only (name, age, medicalHistory) |
| `Doctor.java` | Stores doctor data only |
| `Appointment.java` | Stores appointment data only — scheduling logic lives in `AppointmentService` |
| `Bill.java` | Stores billing data only — calculation logic lives in `BillingService` |
| `AppointmentService.java` | Handles appointment scheduling logic only |
| `BillingService.java` | Handles bill generation and payment only |
| `NotificationService.java` | Coordinates sending notifications only |

**Before (violation — God class):**
```java
public class HospitalManager {
    public void scheduleAppointment() { ... }
    public void sendEmail()           { ... }
    public void generateBill()        { ... }
    public void savePatient()         { ... }
    // one class doing EVERYTHING — changes for many reasons
}
```

**After (fixed):** `AppointmentService`, `BillingService`, and `NotificationService` — each changes for only one reason.

---

### O — Open/Closed Principle
> *Open for extension, closed for modification.*

| Extension point | Current implementations | Future additions (zero code changes) |
|----------------|------------------------|--------------------------------------|
| `IBillingStrategy` | `CashBilling`, `InsuranceBilling` | `GovernmentBilling`, `MpesaBilling` |
| `Notifiable` | `EmailNotification`, `SMSNotification` | `PushNotification`, `WhatsAppNotification` |

**Before (violation):**
```java
public double calculateBill(String type, double amount) {
    if (type.equals("cash"))      return amount;
    if (type.equals("insurance")) return amount * 0.2;
    // every new type = edit this method = risky
}
```

**After (fixed):** `BillingService` depends on `IBillingStrategy`. A new billing type = a new class only. Existing code untouched.

---

### L — Liskov Substitution Principle
> *Subtypes must be substitutable for their base type without breaking the program.*

- **`Specialist` extends `Doctor`** — `AppointmentService` only knows about `Doctor`. When a `Specialist` is stored or retrieved, all `Doctor` methods (`getId`, `getName`, `getSpecialization`) still work correctly. `Specialist` only *adds* new methods (`getSubSpecialization`, `getYearsOfExperience`) — it never overrides parent behaviour.

- **`EmailNotification` and `SMSNotification`** both implement `Notifiable`. Swapping one for the other never breaks the `sendNotification(recipient, message)` contract.

---

### I — Interface Segregation Principle
> *Clients should not be forced to depend on methods they don't use.*

| Interface | Methods | Why it's focused |
|-----------|---------|-----------------|
| `Notifiable` | `sendNotification()` | One method — nothing else |
| `IBillingStrategy` | `calculateBill()`, `getBillingType()` | Only what `BillingService` needs |
| `IPatientRepository` | `save()`, `findById()`, `getAll()` | Patient CRUD only |
| `IDoctorRepository` | `save()`, `findById()`, `getAll()` | Doctor CRUD only |
| `IAppointmentRepository` | `save()`, `getAll()` | Appointment persistence only |

**Before (violation):**
```java
public interface HospitalWorker {
    void treatPatient();  // only doctors
    void payBill();       // only billing staff
    void sendEmail();     // only notification staff
    void scheduleRoom();  // only admins
    // Doctor is forced to implement payBill() — not its job!
}
```

---

### D — Dependency Inversion Principle
> *High-level modules should not depend on low-level modules. Both should depend on abstractions.*

`AppointmentService` receives all dependencies via constructor injection:

```java
public AppointmentService(
    IPatientRepository     patientRepo,      // not InMemoryPatientRepository
    IDoctorRepository      doctorRepo,       // not InMemoryDoctorRepository
    IAppointmentRepository appointmentRepo,  // not InMemoryAppointmentRepository
    Notifiable             notifier          // not EmailNotification
) { ... }
```

To switch from in-memory storage to MySQL, only `Main.java` changes — `AppointmentService` does not change at all.

**Before (violation — tight coupling):**
```java
public class AppointmentService {
    private MySQLDatabase db    = new MySQLDatabase();    // hardcoded
    private EmailNotification e = new EmailNotification(); // hardcoded
    // changing DB or email = rewrite this class
}
```

---

## 📊 UML Class Diagram

![UML Class Diagram](https://github.com/BestPragramming/Hospital-Management/blob/main/src/ui/class%20diagram%20best.png)

---

## 🔗 References

- R. C. Martin, *Agile Software Development: Principles, Patterns, and Practices.* Prentice Hall, 2003.
- Wikipedia, [SOLID (object-oriented design)](https://en.wikipedia.org/wiki/SOLID)
- Anthropic, [Claude AI](https://claude.ai) — used for documentation structuring and SOLID principle explanations.

---

*Submitted as part of Assignment #2 — Hospital Management Refactoring Challenge*
