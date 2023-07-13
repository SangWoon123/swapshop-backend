package tukorea.devhive.swapshopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tukorea.devhive.swapshopbackend.model.Enum.AuthenticationType;
import tukorea.devhive.swapshopbackend.model.dao.Login;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login,Long> {
    Optional<Login> findByEmailAndAuthType(String email, AuthenticationType authType);
}
