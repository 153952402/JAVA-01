package io.github.kimmking.gateway.router;

public class BackendServer {
    private String ip;
    private int weight;

    public BackendServer(String ip, int weight) {
        this.ip = ip;
        this.weight = weight;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
