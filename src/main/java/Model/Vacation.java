package Model;

public class Vacation {
    private String dest_cntr;
    private String dest_area;
    private int price;
    private int duration;

    public Vacation(String dest_cntr, String dest_area, int price, int duration) {
        this.dest_cntr = dest_cntr;
        this.dest_area = dest_area;
        this.price = price;
        this.duration = duration;
    }

    public String getDest_cntr() {
        return dest_cntr;
    }

    public String getDest_area() {
        return dest_area;
    }

    public int getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }
}
