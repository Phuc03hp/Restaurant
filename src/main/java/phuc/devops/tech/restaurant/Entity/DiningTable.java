package phuc.devops.tech.restaurant.Entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "DiningTable")
public class DiningTable {

    @Column(name = "tableID")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String tableID;

    @Column(name = "maxCapacity")
    private Long maxCapacity;

    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "diningTable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Invoice invoice;

}
