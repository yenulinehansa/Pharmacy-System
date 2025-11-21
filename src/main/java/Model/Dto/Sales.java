package Model.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sales {
    private String Orderid;
    private String Patientid;
    private double Totaldiscount;
    private double Finaltotal;
}
