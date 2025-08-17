package model;

public class MovementRecord {
    String id;
    Boolean movementDetected;
    String timestamp;

    MovementRecord(String id, Boolean movementDetected, String timestamp) {
        this.id = id;
        this.movementDetected = movementDetected;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getMovementDetected() {
        return movementDetected;
    }

    public void setMovementDetected(Boolean movementDetected) {
        this.movementDetected = movementDetected;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
