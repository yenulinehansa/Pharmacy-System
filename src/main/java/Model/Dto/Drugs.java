package Model.Dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Drugs {
    private String id;
    private String name;
    private String brand;
    private double unitprice;
    private int stock_qty;
    private LocalDate expDate;

}
