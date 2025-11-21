package Model.Entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CartItemsEntity {
    private String Patientid;
    private String Drugid;
    private double totalprice;
    private double discount;
    private int quantity;
}
