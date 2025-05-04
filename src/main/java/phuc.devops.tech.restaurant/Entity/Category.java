package phuc.devops.tech.restaurant.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "category")
public class Category {

    @Column(name = "category_id")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String categoryID;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Food> foods;


}
