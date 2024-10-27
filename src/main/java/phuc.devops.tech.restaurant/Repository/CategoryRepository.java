package phuc.devops.tech.restaurant.Repository;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phuc.devops.tech.restaurant.Entity.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByName(String name);
}
