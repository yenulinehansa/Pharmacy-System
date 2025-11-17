package Model.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class PatientsEntity {
    @Id

    private String id;

    private String name;

    private String telNo;

}
