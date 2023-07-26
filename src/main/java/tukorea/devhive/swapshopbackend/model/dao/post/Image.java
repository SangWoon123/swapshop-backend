package tukorea.devhive.swapshopbackend.model.dao.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "file_path")
    private String filePath;

    @Builder
    public Image(Long id,String filePath) {
        this.id = id;
        this.filePath = filePath;
    }
}
