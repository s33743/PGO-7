import java.util.Objects;

public abstract class ShipmentOrder implements SummaryPrintable {
    public String  orderNumber;
    public String customerName;
    public double distanceKm;
    public double baseFee;
    public boolean insured;
    public double lastCalculatedPrice;

    public ShipmentOrder(String customerName, String orderNumber, double distanceKm, double baseFee, boolean insured, double lastCalculatedPrice) {
        this.customerName = customerName;
        this.orderNumber = orderNumber;
        this.distanceKm = distanceKm;
        this.baseFee = baseFee;
        this.insured = insured;
        this.lastCalculatedPrice = lastCalculatedPrice;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public double getBaseFee() {
        return baseFee;
    }

    public boolean isInsured() {
        return insured;
    }

    public double getLastCalculatedPrice() {
        return lastCalculatedPrice;
    }

    public final void processOrder() {
        validateOrder();
        validateSpecificRules();

        double price = calculateBasePrice();
        price += calculateAdditionalFee();
        price = applyInsurance(price);
        price = applyBusinessDiscount(price);

        lastCalculatedPrice = price;
        printProcessingResult();
    }

    private void validateOrder() {
        if (getOrderNumber() == null || getOrderNumber().isEmpty() || getDistanceKm() < 0) {
            throw new IllegalArgumentException("Podano nieprawidłowe dane");
        }
        this.orderNumber = orderNumber;
    }
    protected void validateSpecificRules();

    private double applyInsurance(double price) {
        if (isInsured()) {
            return price * 1.07;
        }else{
            return price;
        }
    }

    protected double applyBusinessDiscount(double price) {
        return price;
    }

    @Override
    public String toString() {
        return "ShipmentOrder{" +
                "orderNumber='" + orderNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", distanceKm=" + distanceKm +
                ", baseFee=" + baseFee +
                ", insured=" + insured +
                ", lastCalculatedPrice=" + lastCalculatedPrice +
                '}';
    }

    private void printProcessingResult() {
        System.out.println(toString());
    }

    public String buildSummaryLine() {
        return ("numer zamówienia: " + getOrderNumber() + "klient: " + getCustomerName() + "typ przesyłki: " + isInsured() + "ostatnio obliczona cena: " + lastCalculatedPrice );
    }

    protected abstract double calculateBasePrice();

    protected abstract double calculateAdditionalFee();

    public abstract  String getShipmentType();
}
