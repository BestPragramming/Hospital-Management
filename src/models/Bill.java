package models;

// SRP: Bill only stores billing data — calculation logic lives in BillingService
public class Bill {
    private int     id;
    private Patient patient;
    private double  amount;
    private String  billingType;
    private boolean paid;

    public Bill(int id, Patient patient, double amount, String billingType) {
        this.id          = id;
        this.patient     = patient;
        this.amount      = amount;
        this.billingType = billingType;
        this.paid        = false;
    }

    public int     getId()          { return id; }
    public Patient getPatient()     { return patient; }
    public double  getAmount()      { return amount; }
    public String  getBillingType() { return billingType; }
    public boolean isPaid()         { return paid; }
    public void    markAsPaid()     { this.paid = true; }

    @Override
    public String toString() {
        return "Bill{id=" + id +
               ", patient='" + patient.getName() +
               "', amount=$"  + String.format("%.2f", amount) +
               ", type='"    + billingType +
               "', paid="    + paid + "}";
    }
}
