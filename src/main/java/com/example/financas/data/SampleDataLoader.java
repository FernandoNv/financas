package com.example.financas.data;

import com.example.financas.dto.PersonDTO;
import com.example.financas.dto.PurchaseDTO;
import com.example.financas.model.Bill;
import com.example.financas.model.Category;
import com.example.financas.model.Person;
import com.example.financas.model.Purchase;
import com.example.financas.repository.BillRepository;
import com.example.financas.repository.CategoryRepository;
import com.example.financas.repository.PersonRepository;
import com.example.financas.repository.PurchaseRepository;
import com.example.financas.service.PurchaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class SampleDataLoader implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(SampleDataLoader.class);

    private CategoryRepository categoryRepository;
    private PersonRepository personRepository;
    private PurchaseRepository purchaseRepository;
    private BillRepository billRepository;
//    private Faker faker;
    private PurchaseService purchaseService;

    @Autowired
    public SampleDataLoader(CategoryRepository categoryRepository, PersonRepository personRepository, PurchaseRepository purchaseRepository, BillRepository billRepository, PurchaseService purchaseService) {
        this.categoryRepository = categoryRepository;
        this.personRepository = personRepository;
        this.purchaseRepository = purchaseRepository;
        this.billRepository = billRepository;
        this.purchaseService = purchaseService;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Loading Sample data...");
        Person p1 = new Person(
                null,
                "Fernando Vieira",
                "fernando@email.com",
                LocalDate.now()
        );
        p1.getPhoneNumbers().add("2112341234");

        Person p2 = new Person(
                null,
                "Juliana Vieira",
                "juliana@email.com",
                LocalDate.now()
        );

        p2.getPhoneNumbers().add("2112121212");
        logger.info("Inserting person...");
        personRepository.saveAll(Arrays.asList(p1, p2));

        Category c1 = new Category(
                null,
                "Lazer",
                LocalDate.now()
        );

        Category c2 = new Category(
                null,
                "Transporte",
                LocalDate.now()
        );

        //Long id, LocalDate date, Double totalPrice, Double sharedPrice, String description, int instalments, int paidInstalments, boolean isShared
        Purchase purchase1 = new Purchase(
                null,
                LocalDate.of(2022, 2,2),
                9.99,
                "99app",
                1,
                1,
                false
        );
        purchase1.setCategory(c1);

        logger.info("Inserting category...");
        categoryRepository.saveAll(Arrays.asList(c1, c2));

        Purchase purchase2 = new Purchase(
                null,
                LocalDate.of(2022, 2,3),
                10.88,
                "Ingresso Cinema",
                1,
                1,
                false
        );
        purchase2.setCategory(c1);

        Purchase purchase3 = new Purchase(
                null,
                LocalDate.of(2022, 2,3),
                50.00,
                "Uber",
                1,
                1,
                false
        );
        purchase3.setCategory(c2);

        //logger.info("Inserting purchase...");
        //purchaseRepository.saveAll(Arrays.asList(purchase1, purchase2, purchase3));

        Bill b1 = new Bill(null, LocalDate.of(2022, 3,1));
        b1.setPerson(p1);
        //p1.getBills().add(b1);

        Bill b2 = new Bill(null, LocalDate.of(2022, 3,1));
        b2.setPerson(p2);
        //p2.getBills().add(b2);

//        logger.info("Inserting bill... first");
//        billRepository.saveAll(Arrays.asList(b1, b2));

        b1.getPurchases().addAll(Arrays.asList(purchase1, purchase2, purchase3));
        b2.getPurchases().add(purchase3);

        purchase1.getBills().add(b1);
        purchase2.getBills().add(b1);
        purchase3.getBills().addAll(Arrays.asList(b1, b2));
        purchase3.setSharedPrice();

        b1.setTotalPrice();
        b2.setTotalPrice();

        logger.info("Inserting purchase...");
        purchaseRepository.saveAll(Arrays.asList(purchase1, purchase2, purchase3));
        logger.info("Inserting bill...");
        billRepository.saveAll(Arrays.asList(b1, b2));
        logger.info("Bill 1 total - " + b1.getTotalPrice());
        logger.info("Bill 2 total - " + b2.getTotalPrice());

        PurchaseDTO purchase4 = new PurchaseDTO(
                null,
                LocalDate.of(2022, 2, 1),
                50D,
                "Compra shared teste com criacao de novas bills",
                3,
                1,
                true
        );
        purchase4.setCategory(c1);
        PersonDTO pdto1 = new PersonDTO();
        pdto1.setId(1L);
        PersonDTO pdto2 = new PersonDTO();
        pdto2.setId(2L);
        purchase4.setPersons(Arrays.asList(pdto1, pdto2));
        purchaseService.addPurchase(purchase4);
    }
}
