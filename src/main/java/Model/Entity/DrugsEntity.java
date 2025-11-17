package Model.Entity;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class DrugsEntity {
    private String id;
    private String name;
    private String brand;
    private double unitprice;
    private int stock_qty;
    private LocalDate expDate;

}
