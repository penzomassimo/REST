package max;

import javax.persistence.*;

/**
 * Created by massimo on 3/2/15.
 */
@Entity
@Table(name = "man_products")
public class ManProduct {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int product_id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    public ManProduct() {
        super();
    }

    public ManProduct(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
