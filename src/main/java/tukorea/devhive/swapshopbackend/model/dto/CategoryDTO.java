package tukorea.devhive.swapshopbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
    @JsonIgnore
    private Long id;
    private String name;
    private String professor;
    private String code;
    private String major;
    @JsonIgnore
    private List<Post> posts;

    @Builder
    public CategoryDTO(Long id, String name, String professor, String code, String major, List<Post> posts) {
        this.id = id;
        this.name = name;
        this.professor = professor;
        this.code = code;
        this.major = major;
        this.posts = posts;
    }
}
