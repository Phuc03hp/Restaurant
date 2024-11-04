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

    @Column(name = "invoiceID")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // này nó có tự sinh kh , cos nhes
    private String invoiceID;

    @OneToOne
    @JoinColumn(name = "orderID", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "customerID", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

}