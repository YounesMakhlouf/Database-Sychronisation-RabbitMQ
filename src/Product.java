import java.time.LocalDate;

public class Product {
    private final LocalDate date;
    private final String region;
    private final String product;
    private final int qty;
    private final double cost;
    private final double amount;
    private final double tax;
    private final double total;

    public Product(LocalDate date, String region, String product, int qty, double cost, double amount, double tax, double total) {
        this.date = date;
        this.region = region;
        this.product = product;
        this.qty = qty;
        this.cost = cost;
        this.amount = amount;
        this.tax = tax;
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getRegion() {
        return region;
    }

    public String getProduct() {
        return product;
    }

    public int getQty() {
        return qty;
    }

    public double getCost() {
        return cost;
    }

    public double getAmount() {
        return amount;
    }

    public double getTax() {
        return tax;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return String.format("Product{date=%s, region='%s', product='%s', qty=%d, cost=%.2f, amount=%.2f, tax=%.2f, total=%.2f}", date, region, product, qty, cost, amount, tax, total);
    }
}
