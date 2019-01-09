package pl.wiewiora.napierdalatr.metrics;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JvmMetricsConfig {

    JvmMetricsConfig(MetricRegistry metricRegistry) {
        // add some JVM metrics (wrap in MetricSet to add better key prefixes)
        MetricSet jvmMetrics = () -> {

            Map<String, Metric> metrics = new HashMap<>();
            metrics.put("gc", new GarbageCollectorMetricSet());
            metrics.put("memory-usage", new MemoryUsageGaugeSet());
            metrics.put("threads", new ThreadStatesGaugeSet());
            return metrics;
        };

        metricRegistry.registerAll(jvmMetrics);
    }
}
