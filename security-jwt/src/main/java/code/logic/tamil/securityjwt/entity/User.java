package code.logic.tamil.securityjwt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


@Entity
@Table (name= "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private Set<Role> roles =new HashSet<>();

    public void setUsername(String username) {
    }

    public void setPassword(String encodePassword) {
    }

    public void setRoles(Set<code.logic.tamil.securityjwt.entity.Role> roles) {
    }

    public String getUsername() {
    }

    public Object getPassword() {
    }

    public Set<code.logic.tamil.securityjwt.entity.Role> getRoles() {
        return null;
    }
}

