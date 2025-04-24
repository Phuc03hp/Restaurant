package phuc.devops.tech.restaurant.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Admin")
public class Admin implements Serializable {
    @Column(name = "adminID")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String adminID;

    @Column(name = "adminName")
    private String adminName;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

}
