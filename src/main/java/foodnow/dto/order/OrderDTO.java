package foodnow.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
@Builder

public class OrderDTO {
    private int id;
    private int userId;
    private String orderTime;
    private String address;
    private String deliveryTime;
    private List<OrderProductDTO> orderProducts;
    private double totalSum;
    private String orderStatus;
    private String paymentMethod;
    private String paymentUrl;
}
