package tukorea.devhive.swapshopbackend.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea.devhive.swapshopbackend.model.category.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
