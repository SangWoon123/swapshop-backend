package tukorea.devhive.swapshopbackend.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea.devhive.swapshopbackend.model.category.Category;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;
import tukorea.devhive.swapshopbackend.model.dto.CategoryDTO;
import tukorea.devhive.swapshopbackend.repository.category.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<CategoryDTO> categoryList() {
        List<Category> allCategory = categoryRepository.findAll();
        return allCategory.stream().map(i->{
            return CategoryDTO.builder()
                    .code(i.getCode())
                    .name(i.getName())
                    .major(i.getMajor())
                    .professor(i.getProfessor())
                    .posts(i.getPosts())
                    .id(i.getId())
                    .build();
        }).collect(Collectors.toList());
    }

    public CategoryDTO search(CategoryDTO categoryDTO, Post post){

        Category category = categoryRepository.findByMajorAndProfessorAndName(categoryDTO.getMajor(), categoryDTO.getProfessor(), categoryDTO.getName());
        category.addPost(post);
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .code(category.getCode())
                .major(category.getMajor())
                .build();
    }

    public Category mapCategoryToEntity(CategoryDTO categoryDTO) {
        // 이미 DB에 존재하는 Category인지 확인
        Category category = categoryRepository.findByMajorAndProfessorAndName(categoryDTO.getMajor(), categoryDTO.getProfessor(), categoryDTO.getName());
        if (category == null) {
            // DB에 존재하지 않는 경우 새로운 Category 생성
            category = Category.builder()
                    .major(categoryDTO.getMajor())
                    .name(categoryDTO.getName())
                    .professor(categoryDTO.getProfessor())
                    .code(categoryDTO.getCode())
                    .build();
        }
        return category;
    }
}
