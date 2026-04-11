package com.vinayak.journalApp.controller;

import com.vinayak.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
private Map<Long, JournalEntry> journalEntries= new HashMap<>();

@GetMapping
    public List<JournalEntry> getAll(){
    return new ArrayList<>(journalEntries.values());
}

@PostMapping
    public Boolean createEntry(@RequestBody JournalEntry myEntry){
    journalEntries.put(myEntry.getId(), myEntry);
    return true;
}

@GetMapping("id{myid}")
    public JournalEntry getJournalEntryById(@PathVariable long myId){
    return journalEntries.get(myId);
}

@DeleteMapping("id{myid}")
    public JournalEntry deleteJournalEntryById(@PathVariable long myId){
    return journalEntries.remove(myId);
}

@PutMapping("id{myid}")
    public JournalEntry updateJournalEntryById(@PathVariable long id,
                                               @RequestBody JournalEntry myEntry){
    return journalEntries.put(id, myEntry);
}
}
