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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tableID")
    private Long tableID;

    @Column(name = "maxCapacity")
    private Long maxCapacity;

    @Column(name = "description")
    private String description;


}
