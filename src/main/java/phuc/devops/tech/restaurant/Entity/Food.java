package phuc.devops.tech.restaurant.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.lang.model.element.NestingKind;

@Entity
@NoArgsConstructor
@Table(name = "foods")
public class Food {
    @Column(name = "foodID")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String foodID;

    @Column(name = "Category")
    private String Category;

    @Column(name = "foodName")
    private String foodName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

    @ManyToOne
    @JoinColumn(name = "invoiceID", nullable = false)
    private Invoice invoice;

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }


    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
