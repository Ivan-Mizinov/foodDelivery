package org.example.fooddelivery.conf;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {

    private final PasswordEncoder bcryptEncoder;

    public AuthUtils(PasswordEncoder bcryptEncoder) {
        this.bcryptEncoder = bcryptEncoder;
    }

    public String encodePassword(String rawPassword) {
        return bcryptEncoder.encode(rawPassword);
    }

    public boolean authenticate(String rawPassword, String encodedPassword) {
        return bcryptEncoder.matches(rawPassword, encodedPassword);
    }
}
