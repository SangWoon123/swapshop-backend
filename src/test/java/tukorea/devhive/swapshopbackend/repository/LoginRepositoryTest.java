package tukorea.devhive.swapshopbackend.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tukorea.devhive.swapshopbackend.model.Enum.AuthenticationType;
import tukorea.devhive.swapshopbackend.model.dao.Login;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class LoginRepositoryTest {

    @Autowired
    private LoginRepository loginRepository;

    @Test
    public void findByAuthTypeAndEmail(){

        //given
        Login login1= Login.builder()
                .email("test@naver.com")
                .authType(AuthenticationType.GOOGLE)
                .build();

        Login login2= Login.builder()
                .email("test@naver.com")
                .authType(AuthenticationType.KAKAO)
                .build();

        loginRepository.save(login1);
        loginRepository.save(login2);

        //when
        Optional<Login> byEmailAndAuthType = loginRepository.findByEmailAndAuthType("test@naver.com", AuthenticationType.GOOGLE);

        //then
        Assertions.assertThat("test@naver.com").isEqualTo(byEmailAndAuthType.get().getEmail());
        Assertions.assertThat(AuthenticationType.GOOGLE).isEqualTo(byEmailAndAuthType.get().getAuthType());

    }

}