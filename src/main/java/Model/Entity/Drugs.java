package Model.Entity;

import jakarta.persistence.Column;
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

public class Drugs {
    @Id

    private String id;

    private String name;

    private String brand;

    private double unitprice;

    private int stock_qty;

    private LocalDate expDate;

}
