package by.epam.xmlparsing.bean;

import java.math.BigDecimal;
import java.util.Objects;

public class DrugPackage {
    private DrugPackageType type;
    private int number;
    private BigDecimal price;

    public DrugPackage() {
    }

    public DrugPackage(DrugPackageType type, int number, BigDecimal price) {
        this.type = type;
        this.number = number;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrugPackage that = (DrugPackage) o;
        return number == that.number &&
                type == that.type &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, number, price);
    }

    @Override
    public String toString() {
        return "DrugPackage{" +
                "type=" + type +
                ", number=" + number +
                ", price=" + price +
                '}';
    }

    public DrugPackageType getType() {
        return type;
    }

    public void setType(DrugPackageType type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
