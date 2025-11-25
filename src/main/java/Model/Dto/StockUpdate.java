package Model.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class StockUpdate {
    private String supplierId;
    private String drugId;
    private int quantity;
    private double buying_price;
    private LocalDate date;

}
