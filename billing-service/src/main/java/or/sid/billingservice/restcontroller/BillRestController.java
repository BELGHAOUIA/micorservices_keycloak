package or.sid.billingservice.restcontroller;

import or.sid.billingservice.entity.Bill;
import or.sid.billingservice.entity.ProductItem;
import or.sid.billingservice.mockups.Customer;
import or.sid.billingservice.repository.BillRepo;
import or.sid.billingservice.repository.ProductItemRepo;
import or.sid.billingservice.service.CustomerServiceClient;
import or.sid.billingservice.service.InventoryServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
class BillRestController{
    @Autowired
    private BillRepo billRepository;
    @Autowired private ProductItemRepo productItemRepository;
    @Autowired private CustomerServiceClient customerServiceClient;
    @Autowired private InventoryServiceClient inventoryServiceClient;


    @PostMapping("/bills/start")
    Long start() {

        Customer customer= customerServiceClient.findCustomerById(1L);
        System.out.print("/**************************************************************************/");
        System.out.print(customer);
        System.out.print("/**************************************************************************/");
        Bill bill= Bill.builder()
                .customerID(customer.getId())
                .billingDate(new Date())
                .customer(customer)
                .build();
        billRepository.save(bill);
        inventoryServiceClient.findAll().getContent().forEach( p-> {
            productItemRepository.save(
                    ProductItem.builder()
                            .price(p.getPrice())
                            .product(inventoryServiceClient.findProductById(p.getId()))
                            .productID(p.getId())
                            .quantity((int)(1+Math.random()*1000))
                            .bill(bill)
                            .build()
            );
        });

        return bill.getId();
    }

    @GetMapping("/bills/full/{id}")
    Bill getBill(@PathVariable(name="id") Long id) {
        Bill bill=billRepository.findById(id).get();
        bill.setCustomer(customerServiceClient.findCustomerById(bill.getCustomerID()));
        bill.setProductItems(productItemRepository.findByBillId(id));
        bill.getProductItems().forEach(pi -> {
            pi.setProduct(inventoryServiceClient.findProductById(pi.getProductID()));
        });
        return bill;
    }
}
