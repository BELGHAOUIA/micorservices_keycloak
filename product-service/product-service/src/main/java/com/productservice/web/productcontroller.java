package com.productservice.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.productservice.repository.ProductRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.ParameterizedTypeReference;

import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;

@EnableMethodSecurity
@RequiredArgsConstructor
@Controller
@Slf4j
public class productcontroller {


    private final OAuth2AuthorizedClientService oauth2ClientService;
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;


    @GetMapping("/")
    //@PreAuthorize("hasRole('client-user')")
    public String index() {
        return "index";
    }

    @GetMapping("/products")
//    @PreAuthorize("hasRole('client-admin')")
    public String products(Model model, @AuthenticationPrincipal OidcUser principal
    ) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken)
                authentication;

        OAuth2AuthorizedClient oauth2Client =
                oauth2ClientService.loadAuthorizedClient(oauthToken.
                        getAuthorizedClientRegistrationId(), oauthToken.getName());

        String jwtAccessToken = oauth2Client.getAccessToken().getTokenValue();
        System.out.println("jwtAccessToken = " + jwtAccessToken);


        System.out.println("Principal = " + principal);

        OidcIdToken idToken = principal.getIdToken();
        String idTokenValue = idToken.getTokenValue();
        System.out.println("idTokenValue = " + idTokenValue);
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @GetMapping("/suppliers")
    public String suppliers(Model model, @AuthenticationPrincipal OidcUser principal
    ) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken)
                authentication;

        OAuth2AuthorizedClient oauth2Client =
                oauth2ClientService.loadAuthorizedClient(oauthToken.
                        getAuthorizedClientRegistrationId(), oauthToken.getName());

        String jwtAccessToken = oauth2Client.getAccessToken().getTokenValue();
        System.out.println("jwtAccessToken = " + jwtAccessToken);


        System.out.println("Principal = " + principal);

        OidcIdToken idToken = principal.getIdToken();
        String idTokenValue = idToken.getTokenValue();
        System.out.println("idTokenValue = " + idTokenValue);
        String url = "http://localhost:8888/suppliers/";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<List<Supplier>> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Supplier>> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                });

        List<Supplier> pageSuppliers = responseEntity.getBody();
        log.info(pageSuppliers.toString());

        model.addAttribute("suppliers", pageSuppliers);
        return "suppliers";
    }

    @GetMapping("/customers")
    public String customers(Model model, @AuthenticationPrincipal OidcUser principal) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient oauth2Client =
                oauth2ClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());

        String jwtAccessToken = oauth2Client.getAccessToken().getTokenValue();
        System.out.println("jwtAccessToken = " + jwtAccessToken);

        System.out.println("Principal = " + principal);

        OidcIdToken idToken = principal.getIdToken();
        String idTokenValue = idToken.getTokenValue();
        System.out.println("idTokenValue = " + idTokenValue);

        String url = "http://localhost:8888/customers/";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<PagedModel<Customer>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<PagedModel<Customer>>() {
                });

        PagedModel<Customer> customers = responseEntity.getBody();
        List<Customer> pageCustomers = new ArrayList<>(customers.getContent());
        log.info(pageCustomers.toString());

        model.addAttribute("customers", pageCustomers);
        return "customers";
    }

    @GetMapping("/inventories")
    public String inventories(Model model, @AuthenticationPrincipal OidcUser principal) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient oauth2Client =
                oauth2ClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());

        String jwtAccessToken = oauth2Client.getAccessToken().getTokenValue();
        System.out.println("jwtAccessToken = " + jwtAccessToken);

        System.out.println("Principal = " + principal);

        OidcIdToken idToken = principal.getIdToken();
        String idTokenValue = idToken.getTokenValue();
        System.out.println("idTokenValue = " + idTokenValue);

        String url = "http://localhost:8888/products/";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<PagedModel<Product>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                });

        PagedModel<Product> products = responseEntity.getBody();
        List<Product> pageProducts = new ArrayList<>(products.getContent());
        log.info(pageProducts.toString());

        model.addAttribute("inventories", pageProducts);
        return "inventories";
    }

 @GetMapping("/bills")
    public String bills(Model model, @AuthenticationPrincipal OidcUser principal) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient oauth2Client =
                oauth2ClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());

        String jwtAccessToken = oauth2Client.getAccessToken().getTokenValue();
        System.out.println("jwtAccessToken = " + jwtAccessToken);

        System.out.println("Principal = " + principal);

        OidcIdToken idToken = principal.getIdToken();
        String idTokenValue = idToken.getTokenValue();
        System.out.println("idTokenValue = " + idTokenValue);

        String startUrl = "http://localhost:8888/bills/start";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

     ResponseEntity<Long> responseId = restTemplate.exchange(
             startUrl,
             HttpMethod.POST,
             entity,
             new ParameterizedTypeReference<>() {
             });
     System.out.println("id bills = " + responseId.getBody());

     String url = "http://localhost:8888/bills/full/"+ responseId.getBody();

     ResponseEntity<Bill> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                });

        System.out.println("\n" + "responseEntity"+ responseEntity +"\n");

        Bill bill = responseEntity.getBody();
        System.out.println("\n" + bill +"\n");
        log.info(bill.toString());

        model.addAttribute("bill", bill);
        return "bills";
    }


}
@Data
@AllArgsConstructor
@NoArgsConstructor
class Supplier{
    private Long id;
    private String nom;
    private String description;


}

@AllArgsConstructor@NoArgsConstructor@Data @ToString
class Customer {

    private Long id;
    private String name;
    private String email;
}

@AllArgsConstructor@NoArgsConstructor@Data @ToString
class Product {

    private Long id;
    private String name;
    private double price;
}

@Data @NoArgsConstructor @AllArgsConstructor
class Bill{
    private Long id;
    private Date billingDate;
    private Collection<ProductItem> productItems;
    private Customer customer;
    private Long customerID;
}

@Data @NoArgsConstructor @AllArgsConstructor
class ProductItem{

    private Long id;
    private Product product;
    private Long productID;
    private double price;
    private double quantity;
}