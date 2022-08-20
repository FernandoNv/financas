package com.example.financas.controller;

import com.example.financas.dto.HttpErrorResponse;
import com.example.financas.dto.PurchaseDTO;
import com.example.financas.model.Purchase;
import com.example.financas.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/purchases")
public class PurchaseController {
    private PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Purchase>> listPurchases(){
        List<Purchase> purchases = purchaseService.findAll();
        return ResponseEntity.ok().body(purchases);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        Purchase purchase = purchaseService.findById(id);
        if(purchase == null){
            return ResponseEntity.badRequest().body(new HttpErrorResponse(HttpStatus.NOT_FOUND.value(), "Id inválido"));
        }

        return ResponseEntity.ok().body(purchase);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updatePurchase(@RequestBody PurchaseDTO purchaseDTO, @PathVariable Long id){
        Purchase purchase = purchaseService.updatePurchase(purchaseDTO, id);
        if(purchase == null){
            return ResponseEntity.badRequest().body(new HttpErrorResponse(HttpStatus.NOT_FOUND.value(), "Id inválido"));
        }

        return ResponseEntity.ok().body(purchase);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Purchase> addPurchase(@RequestBody PurchaseDTO purchaseDTO){
        Purchase purchase = purchaseService.addPurchase(purchaseDTO);

        return ResponseEntity.ok().body(purchase);
    }

//    @DeleteMapping(value = "/{id}", produces = "application/json")
//    public ResponseEntity<Object> deletePurchase(@PathVariable Long id){
//        boolean isDeleted = purchaseService.deletePurchase(id);
//        if(!isDeleted){
//            return ResponseEntity.badRequest().body(new HttpErrorResponse(HttpStatus.NOT_FOUND.value(), "Id inválido"));
//        }
//
//        return ResponseEntity.ok().build();
//    }
}
