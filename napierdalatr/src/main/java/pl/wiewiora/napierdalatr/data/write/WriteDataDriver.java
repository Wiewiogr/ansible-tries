package pl.wiewiora.napierdalatr.data.write;

import pl.wiewiora.napierdalatr.data.TestData;

public interface WriteDataDriver {

    void write(TestData testData) throws Exception;
}
