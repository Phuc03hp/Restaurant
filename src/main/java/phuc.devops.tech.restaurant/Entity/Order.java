package phuc.devops.tech.restaurant.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Column(name = "order_id")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String orderID;

    @ManyToOne
    @JoinColumn(name = "tableID", nullable = false)
    private DiningTable diningTable;

    @Column(name = "quantity")
    private List<Long> quantity;

    @JsonIgnore
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Invoice invoice;

    @Column(name = "total")
    private Float total;

    @ManyToMany(mappedBy = "orders")
    private List<Food> foods = new ArrayList<>();

}