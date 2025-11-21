package Model.Entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalesDetailsEntity {
    private String orderId;
    private String patientId;
    private String drugid;
    private int quantity;
    private double discount;
    private double total;
    private LocalDate orderDate;
}
