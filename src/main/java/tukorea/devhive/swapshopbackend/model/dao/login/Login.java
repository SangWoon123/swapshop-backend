package tukorea.devhive.swapshopbackend.model.dao.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tukorea.devhive.swapshopbackend.model.Enum.login.AuthenticationType;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Login {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "major")
    private String major;

    @Column(name = "introduction")
    private String introduction;

    @Enumerated(EnumType.STRING)
    @Column(name = "authentication_type", nullable = false)
    private AuthenticationType authType;

    @Builder
    public Login(Long id, String email, String password, String nickname, String major, String introduction, AuthenticationType authType) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.major = major;
        this.introduction = introduction;
        this.authType = authType;
    }

    public void update(String major,String introduction,String password){
        this.major=major;
        this.introduction=introduction;
        this.password=password;
    }
}
