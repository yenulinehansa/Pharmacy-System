package Model.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalesDetails {
    private String orderId;
    private String drugid;
    private int quantity;
}
