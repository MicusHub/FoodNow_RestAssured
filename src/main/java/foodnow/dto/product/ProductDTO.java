package foodnow.dto.product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Builder

public class ProductDTO {
    private int id;
    private String title;
    private double price;
    private String minQuantity;
    private String photoLink;
}
