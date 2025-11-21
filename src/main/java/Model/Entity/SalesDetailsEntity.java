package Model.Entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalesDetailsEntity {
    private String orderId;
    private String drugid;
    private int quantity;
}
