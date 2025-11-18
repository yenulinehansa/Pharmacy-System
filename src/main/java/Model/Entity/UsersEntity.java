package Model.Entity;

import lombok.*;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsersEntity {
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
