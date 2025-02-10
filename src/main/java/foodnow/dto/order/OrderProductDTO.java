package foodnow.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Builder

public class OrderProductDTO {
    private int productId;
    private String productName;
    private int quantity;
    private double price;
}
