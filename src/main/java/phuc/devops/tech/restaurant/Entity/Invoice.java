package phuc.devops.tech.restaurant.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "invoice")
public class Invoice  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // này nó có tự sinh kh , cos nhes
    private String invoiceID;

    @ManyToOne
    @JoinColumn(name = "customerID", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "tableID", nullable = false)
    private DiningTable diningTable;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Food> foods;

    @Column(name = "quantity")
    private List<Long> quantity;

    @Column(name = "total")
    private Float total;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;
}
