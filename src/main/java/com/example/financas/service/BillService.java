package com.example.financas.service;

import com.example.financas.dto.BillDTO;
import com.example.financas.model.Bill;
import com.example.financas.repository.BillRepository;
import com.example.financas.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {
    private BillRepository billRepository;
    private PurchaseRepository purchaseRepository;

    @Autowired
    public BillService(BillRepository billRepository, PurchaseRepository purchaseRepository) {
        this.billRepository = billRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public BillDTO toDTO(Bill bill){
        return new BillDTO(bill.getId(), bill.getExpiredAt(), bill.getPerson(), bill.getTotalPrice());
    }

    public Bill toBill(BillDTO billDTO){
        Bill bill = new Bill(billDTO.getId(), billDTO.getExpiredAt());
        bill.setPerson(billDTO.getPerson());
        bill.setPurchases(billDTO.getPurchases());

        return bill;
    }

    public List<Bill> findAll(){
        return billRepository.findAll();
    }

    public Bill findById(Long id) {
        return billRepository.findById(id).orElse(null);
    }

    public Bill addBill(BillDTO billDTO) {
        Bill bill = toBill(billDTO);
        purchaseRepository.saveAll(bill.getPurchases());

        return billRepository.save(bill);
    }

    public Bill updateBill(BillDTO billDTO, Long id){
        Bill bill = findById(id);
        if(bill == null) return null;
        bill.getPurchases().addAll(billDTO.getPurchases());
        purchaseRepository.saveAll(bill.getPurchases());
        return billRepository.save(bill);
    }

    public Boolean deleteBill(Long id) {
        Bill bill = findById(id);
        if(bill == null) return false;

        billRepository.delete(bill);

        return true;
    }

    public List<Bill> findAllByIdPerson(Long id) {
        return billRepository.findAllByIdPerson(id);
    }
}
