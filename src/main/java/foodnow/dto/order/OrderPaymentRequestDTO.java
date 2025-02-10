package foodnow.dto.order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Builder

public class OrderPaymentRequestDTO {
    private int id;
    private String paymentMethod;
}
