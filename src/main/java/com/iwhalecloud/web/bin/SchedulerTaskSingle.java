package com.iwhalecloud.web.bin;

import com.iwhalecloud.web.service.MstActProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SchedulerTaskSingle {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerTaskSingle.class);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    MstActProcessingService mstActProcessingService;

    /**
     * @Scheduled(fixedRate = 6000) ：上一次开始执行时间点之后6秒再执行
     * @Scheduled(fixedDelay = 6000) ：上一次执行完毕时间点之后6秒再执行
     * @Scheduled(initialDelay=1000, fixedRate=6000) ：第一次延迟1秒后执行，之后按fixedRate的规则每6秒执行一次
     * @Scheduled(cron=""):详见cron表达式http://www.pppet.net/
     */

    /*  @Scheduled(fixedRate = 5000)
        public void scheduled1() {
        logger.info("=====>>>>>使用fixedRate执行定时任务");
        System.out.println("使用fixedRate执行定时任务" + Thread.currentThread().getName() + sdf.format(new Date()));
    }*/

    @Scheduled(initialDelay = 10000, fixedDelay = 30000)
    public void scheduled2() {
        logger.info("=====>>>>>使用fixedDelay执行定时任务");
        System.out.println("使用fixedDelay执行定时任务" + Thread.currentThread().getName() + sdf.format(new Date()));
        mstActProcessingService.taskExecute();
    }

//    @Scheduled(cron="*/6 * * * * ?")
//    public void scheduled3(){
//        logger.info("=====>>>>>使用cron执行定时任务");
//        System.out.println("使用cron执行定时任务" + Thread.currentThread().getName() + sdf.format(new Date()));
//    }

}
