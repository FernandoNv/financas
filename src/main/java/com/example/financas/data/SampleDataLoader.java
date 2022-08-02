package com.example.financas.data;

import com.example.financas.model.Bill;
import com.example.financas.model.Category;
import com.example.financas.model.Person;
import com.example.financas.model.Purchase;
import com.example.financas.repository.BillRepository;
import com.example.financas.repository.CategoryRepository;
import com.example.financas.repository.PersonRepository;
import com.example.financas.repository.PurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public SampleDataLoader(
            CategoryRepository categoryRepository,
            PersonRepository personRepository,
            PurchaseRepository purchaseRepository,
            BillRepository billRepository
//            Faker faker
    ) {
        this.categoryRepository = categoryRepository;
        this.personRepository = personRepository;
        this.purchaseRepository = purchaseRepository;
        this.billRepository = billRepository;
//        this.faker = faker;
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

        Purchase purchase1 = new Purchase(
                null,
                LocalDate.of(2022, 2,2),
                27.90,
                "99app",
                1,
                1,
                false
        );
        purchase1.setCategory(c1);

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
                true
        );
        purchase3.setCategory(c2);

        categoryRepository.saveAll(Arrays.asList(c1, c2));
        purchaseRepository.saveAll(Arrays.asList(purchase1, purchase2, purchase3));

        Bill b1 = new Bill(null, LocalDate.of(2022, 3,1));
        b1.setPerson(p1);
        p1.getBills().add(b1);

        Bill b2 = new Bill(null, LocalDate.of(2022, 3,1));
        b2.setPerson(p2);
        p2.getBills().add(b2);

        billRepository.saveAll(Arrays.asList(b1, b2));

        b1.getPurchases().addAll(Arrays.asList(purchase1, purchase2, purchase3));
        b2.getPurchases().add(purchase3);
        purchase1.getBills().add(b1);
        purchase2.getBills().add(b1);
        purchase3.getBills().addAll(Arrays.asList(b1, b2));
        purchase3.setPriceShared();

        billRepository.saveAll(Arrays.asList(b1, b2));
//        purchaseRepository.saveAll(Arrays.asList(purchase1, purchase2, purchase3));
    }
}
