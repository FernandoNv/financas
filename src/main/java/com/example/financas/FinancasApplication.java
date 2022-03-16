package com.example.financas;

import com.example.financas.domain.Bill;
import com.example.financas.domain.Category;
import com.example.financas.domain.Person;
import com.example.financas.domain.Purchase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class FinancasApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FinancasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Person p1 = new Person(
				1L,
				"Fernando Vieira",
				"21 12345678",
				"fernando@email.com"
		);

		Category c1 = new Category(
				1L,
				"Lazer"
		);

		Category c2 = new Category(
				2L,
				"Transporte"
		);

		Purchase purchase1 = new Purchase(
				1L,
				LocalDate.of(2022, 2,2),
				27.90,
				"99app",
				1,
				false,
				c2
		);
		c2.getPurchaseSet().add(purchase1);
		purchase1.getPersonSet().add(p1);
		p1.getPurchaseSet().add(purchase1);

		Purchase purchase2 = new Purchase(
				2L,
				LocalDate.of(2022, 2,3),
				10.88,
				"Ingresso Cinema",
				1,
				false,
				c1
		);
		c1.getPurchaseSet().add(purchase2);
		purchase2.getPersonSet().add(p1);
		p1.getPurchaseSet().add(purchase2);

		Bill b1 = new Bill(
				1L,
				LocalDate.of(2022, 3,1)
		);
		b1.setPerson(p1);
		p1.getBillSet().add(b1);

		b1.getPurchaseSet().add(purchase1);
		purchase1.getBillSet().add(b1);
		b1.getPurchaseSet().add(purchase2);
		purchase2.getBillSet().add(b1);

		System.out.println(p1);
		System.out.println(b1);
		b1.getPurchaseSet().forEach(System.out::println);
	}
}
