package com.exercise.quotes.repository;

import com.exercise.quotes.entity.ItemQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ItemQuoteRepository extends JpaRepository<ItemQuote, Long> {
    @Override
    @Query("select e from #{#entityName} e where e.removed=false")
    List<ItemQuote> findAll();


    @Query("update #{#entityName} e set e.removed=true where e.id=?1")
    @Modifying
    void deleteById(long id);


}
