package com.exercise.quotes.repository;


import com.exercise.quotes.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.stream.Stream;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    Stream<Quote> findAllByRemovedIsFalse();


    @Query("update #{#entityName} e set e.removed=true where e.id=?1")
    @Modifying
    void deleteById(long id);

    @Query("select e from #{#entityName} e where e.removed=false and e.name=?1")
    Optional<Quote> findByName(String name);

}
