package Model.Dto;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Suppliers {
    private String id;
    private String name;
    private String telNo;
    private String email;
    private String company;
    private LocalDate regDate;
}
