package com.vinayak.journalApp.scheduler;

import com.vinayak.journalApp.cache.AppCache;
import com.vinayak.journalApp.entity.JournalEntry;
import com.vinayak.journalApp.entity.User;
import com.vinayak.journalApp.repository.UserRepositoryImpl;
import com.vinayak.journalApp.service.EmailService;
import com.vinayak.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 9 * * SUN")
    public void fetchUserAndSendSaMail(){
        List<User> users=userRepository.getUserForSA();
        for(User user: users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getContent()).collect(Collectors.toList());
            String entry= String.join(" ", filteredEntries);
            String sentiment = sentimentAnalysisService.getSentinment(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment Analysis", sentiment);
        }

    }

    @Scheduled(cron ="0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
