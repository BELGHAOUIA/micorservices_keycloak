package or.sid.billingservice.repository;

import or.sid.billingservice.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ProductItemRepo extends JpaRepository<ProductItem,Long> {
    List<ProductItem> findByBillId(Long billID);
}
