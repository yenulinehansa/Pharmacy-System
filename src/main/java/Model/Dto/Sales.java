package Model.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sales {
    private String Orderid;
    private String Patientid;
    private double discount;
    private double total;
    private LocalDate orderDate;
}
