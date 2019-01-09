package pl.wiewiora.napierdalatr.metrics;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@Component
public class GraphiteMetricsConfig {

    private static final Logger log = LoggerFactory.getLogger(GraphiteMetricsConfig.class);

    @Value("${graphite.prefix:napierdalatr.localhost}")
    private String appPrefix;

    @Value("${graphite.host}")
    private String graphiteHost;

    @Value("${graphite.port:2003}")
    private Integer graphitePort;

    @Value("${graphite.config.duration:30}")
    private Integer graphiteDuration;

    private final MetricRegistry metricRegistry;

    public GraphiteMetricsConfig(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @PostConstruct
    public GraphiteReporter graphiteReporter() {
        final GraphiteSender graphite = new Graphite(new InetSocketAddress(graphiteHost, graphitePort));

        log.info("the graphite metrics prefix is {}", appPrefix);

        final GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry)
                .prefixedWith(appPrefix)
                .convertRatesTo(TimeUnit.MILLISECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL)
                .build(graphite);

        reporter.start(graphiteDuration, TimeUnit.SECONDS);
        return reporter;
    }
}