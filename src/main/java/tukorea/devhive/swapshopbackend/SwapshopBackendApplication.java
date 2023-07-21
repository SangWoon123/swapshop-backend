package tukorea.devhive.swapshopbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 작성일, 수정일 엔티티설정을 위해 설정
@SpringBootApplication
public class SwapshopBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwapshopBackendApplication.class, args);
	}

}
