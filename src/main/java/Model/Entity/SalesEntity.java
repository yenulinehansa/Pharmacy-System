package Model.Entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalesEntity {
    private String Orderid;
    private String Patientid;
    private double Totaldiscount;
    private double Finaltotal;
}
