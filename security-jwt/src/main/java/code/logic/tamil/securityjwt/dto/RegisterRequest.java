package code.logic.tamil.securityjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private Set<String> roles;

    public String getUsername() {
    }

    public CharSequence getPassword() {
    }

    public String[] getRoles() {
        return null;
    }
}
