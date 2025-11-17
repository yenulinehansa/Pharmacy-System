package Model.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class UsersEntity {
    @Id

    private String id;

    private String name;

    private String telNo;

    private String email;

    private String role;

    private String username;

    private String password;

    private double salary;

    private LocalDate regDate;

}
