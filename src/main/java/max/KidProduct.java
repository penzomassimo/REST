package max;

import javax.persistence.*;

/**
 * Created by massimo on 3/2/15.
 */
@Entity
@Table(name = "kid_products")
public class KidProduct {
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

    @Column(name = "pic_url")
    private String pic_url;

    public KidProduct() {
        super();
    }

    public KidProduct(String name, String description, double price) {
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

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
