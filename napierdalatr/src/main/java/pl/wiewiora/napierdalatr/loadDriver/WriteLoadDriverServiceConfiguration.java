package pl.wiewiora.napierdalatr.loadDriver;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wiewiora.napierdalatr.data.TestData;
import pl.wiewiora.napierdalatr.data.TestDataGenerator;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class WriteLoadDriverServiceConfiguration {

    @Bean
    public ScheduledExecutorService getWriteLoadingPool() {
        return Executors.newScheduledThreadPool(4);
    }

    @Bean
    public TestDataGenerator testDataGenerator() {
        return () -> new TestData(UUID.randomUUID(), getRandomString(), getRandomString());
    }

    private String getRandomString() {
        return RandomStringUtils.randomAlphabetic(20, 40);
    }
}
