package com.iwhalecloud.web.bin;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

//@Component
@EnableAsync
public class ScheduleTask {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Async("taskExecutor")
    @Scheduled(fixedRate = 2000)
    public void testScheduleTask() {
        try{
            Thread.sleep(6000);
            System.out.println("SpringBoot的定时任务" + Thread.currentThread().getName() + sdf.format(new Date()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
