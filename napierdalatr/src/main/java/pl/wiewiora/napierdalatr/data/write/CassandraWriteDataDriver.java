package pl.wiewiora.napierdalatr.data.write;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.wiewiora.napierdalatr.data.TestData;

@Component
public class CassandraWriteDataDriver implements WriteDataDriver {

    private final Cluster cluster;
    private final Session session;

    CassandraWriteDataDriver(@Value("${cassandra.address:80.211.148.5}") String servers) {
        cluster = Cluster.builder().addContactPoints(servers).build();
        session = cluster.connect("loaddriver");
    }

    @Override
    public void write(TestData testData) {
        session.execute("INSERT INTO loaddriver.test (id, x, y) VALUES " +
                "(" +
                "" + testData.getUuid().toString() + "," +
                "'" + testData.getX() + "'," +
                "'" + testData.getY() + "'" +
                ")");
    }
}
