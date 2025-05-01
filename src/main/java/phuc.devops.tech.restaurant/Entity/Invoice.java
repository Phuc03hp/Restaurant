package phuc.devops.tech.restaurant.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "invoice")
public class Invoice  implements Serializable {

    @Column(name = "invoiceID")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String invoiceID;

    @OneToOne
    @JoinColumn(name = "orderID", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "customer_phone", referencedColumnName = "phoneNumber")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "comment")
    private String comment;



}
