package tukorea.devhive.swapshopbackend.model.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tukorea.devhive.swapshopbackend.model.Enum.AuthenticationType;

import javax.persistence.*;

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

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String name;

    @Column(name = "major")
    private String major;

    @Column(name = "introduction")
    private String introduction;

    @Enumerated(EnumType.STRING)
    @Column(name = "authentication_type", nullable = false)
    private AuthenticationType authType;

    @Column(name = "authentication_token")
    private String token;

    @Builder
    public Login(String email, String password, String name, String major, String introduction, AuthenticationType authType, String token) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.major = major;
        this.introduction = introduction;
        this.authType = authType;
        this.token = token;
    }
}
