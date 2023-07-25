package tukorea.devhive.swapshopbackend.model.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String major;
    private String name;
    private String professor;
    private String code;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> posts=new ArrayList<>();
    @Builder
    public Category(Long id, String major, String name, String professor, String code ) {
        this.id = id;
        this.major = major;
        this.name = name;
        this.professor = professor;
        this.code = code;
    }

    public void addPost(Post post){
        this.getPosts().add(post);
        post.setCategory(this);
    }
}
