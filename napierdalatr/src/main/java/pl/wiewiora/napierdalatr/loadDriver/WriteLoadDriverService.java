package pl.wiewiora.napierdalatr.loadDriver;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PreDestroy;

@Component
@Controller
public class WriteLoadDriverService {

    private WriteTaskRepeater taskRepeater;

    WriteLoadDriverService(WriteTaskRepeater taskRepeater) {
        this.taskRepeater = taskRepeater;
    }

    @PreDestroy
    public void stopWriteLoadDriver() {
        taskRepeater.stop();
    }

    @RequestMapping(value = "/driver/write/stop")
    public ResponseEntity stopLoadDriver() {
        taskRepeater.stop();
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/driver/write/start/{numberOfThreads}")
    public ResponseEntity startLoadDriver(@PathVariable int numberOfThreads) {
        taskRepeater.reschedule(numberOfThreads);
        return ResponseEntity.ok().build();
    }
}
