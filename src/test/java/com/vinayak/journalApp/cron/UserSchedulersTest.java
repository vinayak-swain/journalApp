package com.vinayak.journalApp.cron;

import com.vinayak.journalApp.scheduler.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulersTest {

    @Autowired
    private UserScheduler userSchedulers;

    @Test
    public void testFetchUserAndSendEmail() {
        userSchedulers.fetchUsersAndSendSaMail();
    }
}
