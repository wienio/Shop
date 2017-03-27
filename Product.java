package sample;

import javafx.beans.property.*;

/**
 * Created by Wienio on 2017-03-18.
 */
public class Product {
    private final StringProperty name;
    private final IntegerProperty amount;
    private ProductType type;
    private final BooleanProperty ifConttainsPreservatives;

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getName() {
        return name.get();
    }

    public boolean isIfConttainsPreservatives() {
        return ifConttainsPreservatives.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public void setIfConttainsPreservatives(boolean ifConttainsPreservatives) {
        this.ifConttainsPreservatives.set(ifConttainsPreservatives);
    }

    public ProductType getType() {
        return type;
    }

    public BooleanProperty ifConttainsPreservativesProperty() {
        return ifConttainsPreservatives;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public Product(int amount, boolean ifContainsPreservatives, String name, ProductType type) {
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleIntegerProperty(amount);
        this.ifConttainsPreservatives = new SimpleBooleanProperty(ifContainsPreservatives);
        this.type =type;
    }

    public Product() {
        this(0,false,null,null);
    }
}
