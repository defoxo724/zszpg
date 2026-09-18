package io.defoxo724.zszpg.zszpg.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserAdminInitializer implements CommandLineRunner {
    private final UserService userService;
    private final String adminLogin;
    private final String adminPassword;

    public UserAdminInitializer(UserService userService, @Value("${app.admin.login:}") String adminLogin,
            @Value("${app.admin.password:}") String adminPassword) {
        this.userService = userService;
        this.adminLogin = adminLogin;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) {
        if (!adminLogin.isBlank() && !adminPassword.isBlank()) {
            userService.createAdmin(adminLogin, adminPassword);
        }
    }
}
