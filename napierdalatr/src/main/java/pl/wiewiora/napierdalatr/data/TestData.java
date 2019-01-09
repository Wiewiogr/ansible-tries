package pl.wiewiora.napierdalatr.data;

import java.util.UUID;

public class TestData {

    UUID uuid;
    String x;
    String y;

    public TestData(UUID uuid, String x, String y) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
    }

    public TestData() {

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
