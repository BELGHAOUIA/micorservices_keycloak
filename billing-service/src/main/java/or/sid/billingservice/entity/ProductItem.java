package or.sid.billingservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import or.sid.billingservice.mockups.Product;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductItem{

        @Id
        @GeneratedValue(strategy =GenerationType.IDENTITY)
        private Long id;
        @Transient
        private Product product;
        private Long productID;
        private double price;
        private double quantity;

        @ManyToOne
        @JsonProperty(access =JsonProperty.Access.WRITE_ONLY)
        private Bill bill;
}

