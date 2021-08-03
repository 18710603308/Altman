package com.dy_name.config;

import com.dy_name.config.base.DDSHolder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author mzy
 * @date 2021/8/3 21:31
 */
@Component
public class ScheduleClear {

    private static ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
    /**
     * 通过定时任务周期性清除不使用的数据源
     */
    @Scheduled(cron = "*/3 * * * * ?")
    public void clear(){

        executorService.schedule(()->{
            DDSHolder.instance().clearIdleDDS();
        }, 10, TimeUnit.SECONDS);
    }
}
