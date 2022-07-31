package com.example.financas.data;

import com.example.financas.model.Bill;
import com.example.financas.model.Category;
import com.example.financas.model.Person;
import com.example.financas.model.Purchase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class SampleDataLoader implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(SampleDataLoader.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("Loading Sample data...");
        Person p1 = new Person(
                1L,
                "Fernando Vieira",
                "fernando@email.com",
                LocalDate.now()
        );

        p1.getPhoneNumber().add("21 12345678");

        Category c1 = new Category(
                1L,
                "Lazer",
                LocalDate.now()
        );

        Category c2 = new Category(
                2L,
                "Transporte",
                LocalDate.now()
        );

        Purchase purchase1 = new Purchase(
                1L,
                LocalDate.of(2022, 2,2),
                27.90,
                "99app",
                1,
                1,
                false
        );

        Purchase purchase2 = new Purchase(
                2L,
                LocalDate.of(2022, 2,3),
                10.88,
                "Ingresso Cinema",
                1,
                1,
                false
        );

        Bill b1 = new Bill(
                1L,
                LocalDate.of(2022, 3,1)
        );
        b1.setPerson(p1);
        p1.getBills().add(b1);

        b1.getPurchases().addAll(Arrays.asList(purchase1, purchase2));

        System.out.println(p1);
        System.out.println(b1);
        b1.getPurchases().forEach(System.out::println);
    }
}
