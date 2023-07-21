package tukorea.devhive.swapshopbackend.model.category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostCategory {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public PostCategory(Long id, Post post, Category category) {
        this.id = id;
        this.post = post;
        this.category = category;
    }
}
