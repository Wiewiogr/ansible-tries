package pl.wiewiora.napierdalatr.loadDriver;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class WriteLoadDriverMetrics {

    private final Meter elementsWrittenMeter;
    private final Meter errorsMeter;
    private final Timer writeTimer;

    public WriteLoadDriverMetrics(MetricRegistry metricRegistry) {
        this.elementsWrittenMeter = new Meter();
        this.errorsMeter = new Meter();
        this.writeTimer = new Timer();
        metricRegistry.register("loaddriver.write.elements", elementsWrittenMeter);
        metricRegistry.register("loaddriver.write.time", writeTimer);
        metricRegistry.register("loaddriver.write.error", errorsMeter);
    }

    public void recordTimeAndMarkWritten(long time) {
        writeTimer.update(time, TimeUnit.MILLISECONDS);
        elementsWrittenMeter.mark();
    }

    public void markError() {
        errorsMeter.mark();
    }
}

