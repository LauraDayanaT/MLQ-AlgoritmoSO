package models;

public class Proceso {
    private String id;
    private int burstTime;
    private int remainingTime;
    private int arrivalTime;
    private int queue;
    private int priority;
    private Integer firstResponse;
    private Integer completionTime;

    public Proceso(String id, int burstTime, int arrivalTime, int queue, int priority) {
        this.id = id;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.queue = queue;
        this.priority = priority;
    }

    // Getters y Setters
    public String getId() { return id; }
    public int getBurstTime() { return burstTime; }
    public int getArrivalTime() { return arrivalTime; }
    public int getQueue() { return queue; }
    public int getPriority() { return priority; }
    public int getRemainingTime() { return remainingTime; }
    public void setRemainingTime(int remainingTime) { this.remainingTime = remainingTime; }
    public Integer getFirstResponse() { return firstResponse; }
    public void setFirstResponse(Integer firstResponse) { this.firstResponse = firstResponse; }
    public Integer getCompletionTime() { return completionTime; }
    public void setCompletionTime(Integer completionTime) { this.completionTime = completionTime; }

    @Override
    public String toString() {
        return id + ";" + burstTime + ";" + arrivalTime + ";" + queue + ";" + priority;
    }
}
