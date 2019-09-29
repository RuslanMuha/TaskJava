package com.exercise.security.accounting.dao;

import com.exercise.security.accounting.entities.AccountMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccMngRepository extends
        MongoRepository<AccountMongo, String> {

}
