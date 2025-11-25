package Model.Entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class StockUpdateEntity {
    private String supplierId;
    private String drugId;
    private int quantity;
    private double buying_price;
    private LocalDate date;

}
