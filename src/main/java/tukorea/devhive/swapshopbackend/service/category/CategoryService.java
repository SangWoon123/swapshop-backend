package tukorea.devhive.swapshopbackend.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea.devhive.swapshopbackend.model.category.Category;
import tukorea.devhive.swapshopbackend.repository.category.CategoryRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<Category> categoryList() {
        List<Category> allCategory = categoryRepository.findAll();
        return allCategory;
    }
}
