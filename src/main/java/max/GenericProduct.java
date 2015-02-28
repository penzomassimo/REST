package max;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by massimo on 2/27/15.
 */
@Entity
@Table(name = "generic_products")
public class GenericProduct {

    @Id
    private int product_id;
    @Column()
    private String name;
    @Column
    private double price;
    @Column
    private int category;

    public GenericProduct(int product_id, String name, double price, int category) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
