package phuc.devops.tech.restaurant.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Customer")
public class Customer {

    @Column(name = "customerID")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String customerID;

    @Column(name = "name")
    private String name;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Invoice> invoices;
}
