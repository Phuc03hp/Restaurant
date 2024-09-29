package phuc.devops.tech.restaurant.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.lang.model.element.NestingKind;

@Data
@Entity
@NoArgsConstructor
@Table(name = "foods")
public class Food {
    @Column(name = "foodID")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String foodID;

    @Column(name = "category")
    private String category;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

    @ManyToOne
    @JoinColumn(name = "invoiceID", nullable = true)
    private Invoice invoice;
}
