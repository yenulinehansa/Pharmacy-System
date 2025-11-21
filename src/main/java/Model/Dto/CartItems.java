package Model.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CartItems {
    private String Patientid;
    private String Drugid;
    private double totalprice;
    private double discount;
    private int quantity;
}
