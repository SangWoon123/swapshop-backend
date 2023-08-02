package tukorea.devhive.swapshopbackend.model.dao.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_id")
    private Login login;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Favorite(Login login, Post post) {
        this.login = login;
        this.post = post;
    }

    @Builder
    public Favorite(Long id, Login login, Post post) {
        this.id = id;
        this.login = login;
        this.post = post;
    }
}
