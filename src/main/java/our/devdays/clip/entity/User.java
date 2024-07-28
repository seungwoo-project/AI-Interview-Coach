package our.devdays.clip.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name = "users")
public class User {
    @Id
    private String userId;
    private String password;
}