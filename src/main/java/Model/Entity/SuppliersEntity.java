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

public class SuppliersEntity {
    @Id

    private String id;

    private String name;

    private String telNo;

    private String email;

    private String company;

    private LocalDate regDate;
}
