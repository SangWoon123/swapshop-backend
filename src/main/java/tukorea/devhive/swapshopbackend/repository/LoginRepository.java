package tukorea.devhive.swapshopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tukorea.devhive.swapshopbackend.model.dao.Login;

public interface LoginRepository extends JpaRepository<Login,Long> {
    Login findByEmail(String email);
}
