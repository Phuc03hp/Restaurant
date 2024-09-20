package phuc.devops.tech.restaurant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phuc.devops.tech.restaurant.Entity.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food,String> {

}
