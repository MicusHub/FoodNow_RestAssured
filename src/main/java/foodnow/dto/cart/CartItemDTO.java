package foodnow.dto.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class CartItemDTO {
    private int id;
    private int cartId;
    private int productId;
    private int productQuantity;
    private double sum;
}
