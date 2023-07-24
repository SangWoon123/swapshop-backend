package tukorea.devhive.swapshopbackend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tukorea.devhive.swapshopbackend.model.Enum.login.AuthenticationType;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
import tukorea.devhive.swapshopbackend.service.login.UserInfoService;

import java.util.Optional;

@SpringBootTest
@Transactional
class UserInfoServiceTest {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void addUserInfoTest(){
        //given
        Login login= Login.builder()
                .email("test@kakao.com")
                .nickname("tester")
                .authType(AuthenticationType.KAKAO)
                .build();
        loginRepository.save(login);

        //when
        LoginDTO loginDTO = LoginDTO.builder()
                .email("test@kakao.com")
                .nickname("Update username")
                .authenticationType(AuthenticationType.KAKAO)
                .major("컴퓨터공학과")
                .introduction("자기소개 입니다.")
                .build();

        userInfoService.addInfo(loginDTO);

        //then
        Optional<Login> updatedUser = loginRepository.findByEmailAndAuthType("test@kakao.com", AuthenticationType.KAKAO);
        Assertions.assertTrue(updatedUser.isPresent());
        Assertions.assertEquals("컴퓨터공학과", updatedUser.get().getMajor());
        Assertions.assertEquals("자기소개 입니다.", updatedUser.get().getIntroduction());


    }


}