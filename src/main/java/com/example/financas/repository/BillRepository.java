package com.example.financas.repository;

import com.example.financas.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query("SELECT DISTINCT bill FROM Bill bill " +
            "INNER JOIN bill.person p " +
            "WHERE bill.person.id = p.id AND p.id = :idPerson " +
            "ORDER BY bill.expiredAt asc")
    List<Bill> findAllByIdPerson(@Param("idPerson") Long id);
}
