package Model.Entity;

import lombok.*;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class SuppliersEntity {
    private String id;
    private String name;
    private String telNo;
    private String email;
    private String company;
    private LocalDate regDate;
}
