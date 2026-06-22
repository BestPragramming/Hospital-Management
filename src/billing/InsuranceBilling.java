package billing;

import interfaces.IBillingStrategy;

// OCP: another billing strategy — zero changes to BillingService to support this
public class InsuranceBilling implements IBillingStrategy {
    private final double coveragePercent;

    public InsuranceBilling(double coveragePercent) {
        if (coveragePercent < 0 || coveragePercent > 100)
            throw new IllegalArgumentException("Coverage must be between 0 and 100");
        this.coveragePercent = coveragePercent;
    }

    @Override
    public double calculateBill(double baseAmount) {
        double covered = baseAmount * (coveragePercent / 100);
        return baseAmount - covered;   // patient pays the remainder
    }

    @Override
    public String getBillingType() {
        return "INSURANCE (" + coveragePercent + "% covered)";
    }
}
