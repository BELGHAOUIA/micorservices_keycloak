package or.sid.billingservice.mockups;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    private Long id;
    private String name;
    private Double price;

}
