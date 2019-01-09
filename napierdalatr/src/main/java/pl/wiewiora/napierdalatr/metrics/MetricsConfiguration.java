package pl.wiewiora.napierdalatr.metrics;

import com.codahale.metrics.MetricRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {

    @Bean
    public MetricRegistry metricRegistry() {
        return new MetricRegistry();
    }
}
