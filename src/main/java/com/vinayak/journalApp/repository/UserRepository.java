package com.vinayak.journalApp.repository;

import com.vinayak.journalApp.entity.JournalEntry;
import com.vinayak.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId>
{
User findByUserName (String userName);
}
