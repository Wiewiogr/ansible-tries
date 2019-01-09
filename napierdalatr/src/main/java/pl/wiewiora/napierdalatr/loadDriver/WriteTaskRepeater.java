package pl.wiewiora.napierdalatr.loadDriver;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.wiewiora.napierdalatr.data.TestDataGenerator;
import pl.wiewiora.napierdalatr.data.write.WriteDataDriver;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public class WriteTaskRepeater {

    Logger logger = LoggerFactory.getLogger(WriteTaskRepeater.class);

    private ScheduledExecutorService executorService;
    private final WriteDataDriver writeDataDriver;
    private final TestDataGenerator testDataGenerator;
    private final WriteLoadDriverMetrics writeLoadDriverMetrics;
    private final List<ScheduledFuture> scheduledFutures = Lists.newArrayList();

    public WriteTaskRepeater(/*ScheduledExecutorService executorService,*/
                             WriteDataDriver writeDataDriver,
                             TestDataGenerator testDataGenerator,
                             WriteLoadDriverMetrics writeLoadDriverMetrics) {
//        this.executorService = executorService;
        this.writeDataDriver = writeDataDriver;
        this.testDataGenerator = testDataGenerator;
        this.writeLoadDriverMetrics = writeLoadDriverMetrics;
    }

    public void reschedule(int numberOfThreads) {
        stop();

        executorService = Executors.newScheduledThreadPool(numberOfThreads);
        logger.info("Scheduled writing in {} threads", numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            ScheduledFuture<?> scheduledFuture = executorService.scheduleWithFixedDelay(() -> {
                try {
                    StopWatch stopWatch = new StopWatch();
                    stopWatch.start();
                    writeDataDriver.write(testDataGenerator.generate());
                    stopWatch.stop();
                    writeLoadDriverMetrics.recordTimeAndMarkWritten(stopWatch.getTime());
                } catch (Exception e) {
                    logger.error("Error occured during writing", e);
                    writeLoadDriverMetrics.markError();
                }
            }, 1, 1, TimeUnit.NANOSECONDS);
            scheduledFutures.add(scheduledFuture);
        }
    }

    public boolean isRunning() {
        return !scheduledFutures.isEmpty();
    }

    public void stop() {
        logger.info("Stopped writing");
        scheduledFutures.forEach(scheduledFuture -> scheduledFuture.cancel(false));
        scheduledFutures.clear();
        executorService = null;
    }
}
