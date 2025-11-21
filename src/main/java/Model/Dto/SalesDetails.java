package Model.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalesDetails {
    private String orderId;
    private String patientId;
    private String drugid;
    private int quantity;
    private double discount;
    private double total;
    private LocalDate orderDate;
}
