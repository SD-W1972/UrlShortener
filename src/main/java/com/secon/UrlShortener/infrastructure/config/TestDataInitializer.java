package com.secon.UrlShortener.infrastructure.config;

import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.model.enums.UserType;
import com.secon.UrlShortener.domain.out.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TestDataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TestDataInitializer.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TestDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("🔧 TestDataInitializer starting...");
        try {
            if (userRepository.findByEmail("admin@test.com").isEmpty()) {
                User adminUser = new User(
                        "admin@test.com",
                        passwordEncoder.encode("admin123"),
                        UserType.ADMIN,
                        new ArrayList<>()
                );
                userRepository.save(adminUser);
                logger.info("✅ Admin user created: admin@test.com / admin123");
            } else {
                logger.info("⚠️ Admin user already exists");
            }
        } catch (Exception e) {
            logger.error("❌ Error creating admin user", e);
        }
    }
}


