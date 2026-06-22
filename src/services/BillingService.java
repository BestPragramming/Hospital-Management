package services;

import interfaces.IBillingStrategy;
import models.Bill;
import models.Patient;
import java.util.ArrayList;
import java.util.List;

/**
 * SRP: Only handles bill generation and payment tracking.
 * DIP: Depends on IBillingStrategy abstraction — not CashBilling or InsuranceBilling directly.
 * OCP: To support a new billing type, add a new IBillingStrategy class — this class never changes.
 */
public class BillingService {
    private final IBillingStrategy strategy;
    private final List<Bill>       bills = new ArrayList<>();
    private int nextId = 1;

    public BillingService(IBillingStrategy strategy) {
        this.strategy = strategy;
    }

    public Bill generateBill(Patient patient, double baseAmount) {
        if (baseAmount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        double finalAmount = strategy.calculateBill(baseAmount);
        Bill bill = new Bill(nextId++, patient, finalAmount, strategy.getBillingType());
        bills.add(bill);
        return bill;
    }

    public void payBill(int billId) {
        bills.stream()
             .filter(b -> b.getId() == billId)
             .findFirst()
             .ifPresent(b -> {
                 b.markAsPaid();
                 System.out.println("  >> Bill #" + billId + " marked as PAID.");
             });
    }

    public List<Bill> getAllBills() {
        return new ArrayList<>(bills);
    }
}
