package tukorea.devhive.swapshopbackend.service.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea.devhive.swapshopbackend.model.Enum.login.AuthenticationType;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final LoginRepository loginRepository;

    public void addInfo(LoginDTO loginDTO){
        // repository 검색을위해 dto에서 email,authType을 추출
        String email= loginDTO.getEmail();
        AuthenticationType authType=loginDTO.getAuthenticationType();

        Login userInfo = loginRepository.findByEmailAndAuthType(email, authType)
                .orElseThrow(()->new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        userInfo.update(loginDTO.getMajor(),loginDTO.getIntroduction(), loginDTO.getPassword());

        loginRepository.save(userInfo);

    }

}
