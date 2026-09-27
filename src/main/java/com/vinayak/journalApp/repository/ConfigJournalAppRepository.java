package com.vinayak.journalApp.repository;

import com.vinayak.journalApp.entity.ConfigJournalAppEntity;
import com.vinayak.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId>
{


}
