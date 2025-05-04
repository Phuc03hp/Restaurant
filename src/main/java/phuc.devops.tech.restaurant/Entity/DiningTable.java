package phuc.devops.tech.restaurant.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import phuc.devops.tech.restaurant.dto.request.TableStatus;

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

    @JsonIgnore
    @OneToMany(mappedBy = "diningTable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    @Column(name = "tableStatus")
    private TableStatus tableStatus;

}
