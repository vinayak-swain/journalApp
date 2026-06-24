package com.vinayak.journalApp.service;

import com.vinayak.journalApp.entity.JournalEntry;
import com.vinayak.journalApp.entity.User;
import com.vinayak.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            if (user.getJournalEntries() == null) {
                user.setJournalEntries(new ArrayList<>());
            }
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Error saving JournalEntry",e);
        }
    }
    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed=false;
        try{
        User user= userService.findByUserName(userName);
        removed =user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        if(removed){
            userService.saveUser(user);
            journalEntryRepository.deleteById(id);
        }
    }catch(Exception e)
        {
            System.out.println(e);
            throw new RuntimeException("Error deleting JournalEntry",e);
        }
        return removed;
    }
}
