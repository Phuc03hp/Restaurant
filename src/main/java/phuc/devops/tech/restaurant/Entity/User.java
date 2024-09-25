package phuc.devops.tech.restaurant.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Column(name = "userID")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userID;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;



}
