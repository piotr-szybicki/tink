package blog;

import com.google.crypto.tink.config.TinkConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.security.GeneralSecurityException;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class Application {

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        TinkConfig.register();
        SpringApplication.run(Application.class, args);
    }

}
