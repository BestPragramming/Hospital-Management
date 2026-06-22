package interfaces;

// OCP + DIP: abstraction that BillingService depends on;
// new strategies extend this without touching BillingService
public interface IBillingStrategy {
    double calculateBill(double baseAmount);
    String getBillingType();
}
