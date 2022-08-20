package com.example.financas.service;

import com.example.financas.dto.PersonDTO;
import com.example.financas.dto.PurchaseDTO;
import com.example.financas.model.Bill;
import com.example.financas.model.Category;
import com.example.financas.model.Person;
import com.example.financas.model.Purchase;
import com.example.financas.repository.BillRepository;
import com.example.financas.repository.PurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {
    private final Logger logger = LoggerFactory.getLogger(PurchaseService.class);
    private PurchaseRepository purchaseRepository;
    private BillRepository billRepository;
    private BillService billService;
    private PersonService personService;
    private CategoryService categoryService;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, BillRepository billRepository, BillService billService, PersonService personService, CategoryService categoryService) {
        this.purchaseRepository = purchaseRepository;
        this.billRepository = billRepository;
        this.billService = billService;
        this.personService = personService;
        this.categoryService = categoryService;
    }

    public List<Purchase> findAll(){
        return purchaseRepository.findAll();
    }

    public Purchase findById(Long id){
        return purchaseRepository.findById(id).orElse(null);
    }

    @Transactional
    public Purchase addPurchase(PurchaseDTO purchaseDTO){
        // 1. criar uma lista de comprar de acordo com a quantidade de parcelas
        // 2. adicionar a compra na bill do usuario
        // 3. se a bill nao existe, precisamos cria-la

        logger.info("adding purchase...");
        List<Purchase> newPurchases = createListPurchase(purchaseDTO);
        purchaseDTO.getPersons().forEach(p -> this.addPurchaseBill(p, newPurchases));

       return newPurchases.get(0);
    }

    public Purchase updatePurchase(PurchaseDTO purchaseDTO, Long id){
        Purchase purchase = findById(id);
        if(purchase == null) return null;
        if(purchaseDTO.getDate() != null) purchase.setDate(purchaseDTO.getDate());
        if(purchaseDTO.getTotalPrice() != null) purchase.setTotalPrice(purchaseDTO.getTotalPrice());
        if(purchaseDTO.getDescription() != null) purchase.setDescription(purchaseDTO.getDescription());
        if(purchaseDTO.getInstalments() == 0) purchase.setInstalments(purchaseDTO.getInstalments());
        purchase.setShared(purchaseDTO.getShared());

        if(purchaseDTO.getCategory() != null){
            Category category = categoryService.findById(purchaseDTO.getCategory().getId());
            if(category != null) purchase.setCategory(category);
        }

        purchase.getBills().forEach(Bill::setTotalPrice);

        return purchaseRepository.save(purchase);
    }

//    public Boolean deletePurchase(Long id){
//        Purchase purchase = findById(id);
//        if(purchase == null) return false;
//
//        List<Long> billsId = purchase.getBills().stream().map(Bill::getId).toList();
//        purchaseRepository.delete(purchase);
//
//        List<Bill> bills = billsId.stream().map(idBill -> billService.findById(idBill)).toList();
//        bills.forEach(Bill::setTotalPrice);
//        billRepository.saveAll(bills);
//
//        return true;
//    }

    private List<Purchase> createListPurchase(PurchaseDTO purchaseDTO){
        List<Purchase> newPurchases = new ArrayList<>();
        int day = purchaseDTO.getDate().getDayOfMonth();
        int month = purchaseDTO.getDate().getMonthValue();
        int year = purchaseDTO.getDate().getYear();
        int paidInstalments = purchaseDTO.getPaidInstalments();
        logger.info("creating the purchase list...");
        for(int i = 0; i < purchaseDTO.getInstalments(); i++){
            Purchase newPurchase = new Purchase(
                    null,
                    LocalDate.of(year, month, day),
                    purchaseDTO.getTotalPrice(),
                    purchaseDTO.getDescription(),
                    purchaseDTO.getInstalments(),
                    paidInstalments,
                    purchaseDTO.getShared()
            );
            newPurchase.setCategory(purchaseDTO.getCategory());

            month++;
            if(month == 13) {
                month = 1;
                year++;
            }
            paidInstalments++;
            if(purchaseDTO.getShared()){
                newPurchase.setSharedPrice(newPurchase.getTotalPrice()/purchaseDTO.getPersons().size());
            }
            newPurchases.add(newPurchase);
        }

        return newPurchases;
    }

    private void addPurchaseBill(PersonDTO p, List<Purchase> purchases){
        logger.info("verifying if the person exist...");
        Person person = personService.findById(p.getId());
        if(person == null) return;
        logger.info("getting persons bills...");
        List<Bill> bills = billService.findAllByIdPerson(person.getId());
        logger.info("ID person - "  + person.getId().toString());
        purchases.forEach(purchase -> {
            logger.info("----------------------------------------");
            //verificar se existe a bill correspondete da compra
            int monthPurchase = purchase.getDate().getMonthValue();
            int yearPurchase = purchase.getDate().getYear();

            logger.info("verifying if the bill exist...");
            Bill bill = containsBill(monthPurchase, yearPurchase, bills);
            if(bill == null){
                int month = monthPurchase == 12 ? 1 : monthPurchase + 1;
                int year = monthPurchase == 12 ? yearPurchase + 1 : yearPurchase;
                bill = new Bill(null, LocalDate.of(year, month, 1));
                bill.setPerson(person);
            }
            bill.getPurchases().add(purchase);
            purchase.getBills().add(bill);
            bill.setTotalPrice();

            logger.info("saving the purchase...");
            purchaseRepository.save(purchase);
            logger.info("saving the bill...");
            // como ao salva a compra ele ja criar uma nova bill q esta associada a ela, nao precisamos salvar a bill novamente
            // billRepository.save(bill);
        });
    }

    private Bill containsBill(int monthPurchase, int yearPurchase, List<Bill> bills){
        return bills.stream().filter(b -> {
            int monthBill = b.getExpiredAt().getMonthValue();
            int yearBill = b.getExpiredAt().getYear();

            //caso que o ano tem q ser o proximo
            if(monthPurchase == 12){
                return (yearBill == yearPurchase + 1) && (monthBill == 1);
            }
            return (yearBill == yearPurchase) && (monthBill == monthPurchase + 1);
        }).findAny().orElse(null);
    }

}
