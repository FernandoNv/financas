package com.example.financas.controller;

import com.example.financas.dto.BillDTO;
import com.example.financas.dto.HttpErrorResponse;
import com.example.financas.model.Bill;
import com.example.financas.service.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/bills")
public class BillController {
    private BillService billService;
    private final Logger logger = LoggerFactory.getLogger(BillController.class);

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Bill>> findAll(){
        List<Bill> bills = billService.findAll();
        logger.info(bills.toString());

        return ResponseEntity.ok().body(bills);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        Bill bill = billService.findById(id);
        if(bill == null){
            return ResponseEntity.badRequest().body(new HttpErrorResponse(HttpStatus.NOT_FOUND.value(), "Id inválido"));
        }

        return ResponseEntity.ok().body(bill);
    }

    @GetMapping(value = "person/{id}", produces = "application/json")
    public ResponseEntity<Object> findAllByIdPerson(@PathVariable Long id){
        List<Bill> bills = billService.findAllByIdPerson(id);
        if(bills == null){
            return ResponseEntity.badRequest().body(new HttpErrorResponse(HttpStatus.NOT_FOUND.value(), "Id inválido"));
        }

        return ResponseEntity.ok().body(bills);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Bill> addBill(@RequestBody BillDTO billDTO ){
        Bill newBill = billService.addBill(billDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newBill.getId())
                .toUri();

        return ResponseEntity.created(uri).body(newBill);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateBill(@RequestBody BillDTO billDTO, @PathVariable Long id){
        Bill bill = billService.updateBill(billDTO, id);

        if(bill == null) return ResponseEntity
                .badRequest()
                .body(new HttpErrorResponse(HttpStatus.NOT_FOUND.value(), "ID inválido"));

        return ResponseEntity.ok().body(bill);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Object> deleteBill(@PathVariable Long id){
        Boolean isDeleted = billService.deleteBill(id);
        if(!isDeleted){
            return ResponseEntity
                    .badRequest()
                    .body(new HttpErrorResponse(HttpStatus.NOT_FOUND.value(), "Id inválido"));
        }

        return ResponseEntity.ok().build();
    }
}
