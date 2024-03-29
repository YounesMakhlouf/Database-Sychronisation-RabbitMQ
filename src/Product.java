import java.sql.Date;

public class Product {
    private final Date date;
    private final String region;
    private final String product;
    private final int qty;
    private final double cost;
    private final double amount;
    private final double tax;
    private final double total;

    public Date getDate() {
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

    public Product(Date date, String region, String product, int qty, double cost, double amount, double tax, double total) {
        this.date = date;
        this.region = region;
        this.product = product;
        this.qty = qty;
        this.cost = cost;
        this.amount = amount;
        this.tax = tax;
        this.total = total;
    }

    @Override
    public String toString() {
        return "Product{" + "date=" + date + ", region='" + region + '\'' + ", product='" + product + '\'' + ", qty=" + qty + ", cost=" + cost + ", amount=" + amount + ", tax=" + tax + ", total=" + total + '}';
    }
}
