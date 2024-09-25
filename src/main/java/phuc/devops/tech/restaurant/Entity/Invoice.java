package phuc.devops.tech.restaurant.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String invoiceID;

    @OneToOne
    @JoinColumn(name = "customerID", nullable = false)
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "tableID", nullable = false)
    private DiningTable diningTable;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Food> foods;



}
