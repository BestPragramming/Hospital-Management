package billing;

import interfaces.IBillingStrategy;

// OCP: new billing type added by creating a new class — BillingService never modified
public class CashBilling implements IBillingStrategy {

    @Override
    public double calculateBill(double baseAmount) {
        return baseAmount;  // full price, no discount
    }

    @Override
    public String getBillingType() {
        return "CASH";
    }
}
