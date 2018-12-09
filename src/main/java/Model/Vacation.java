package Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Vacation {
    private String dest_region;
    private String dest_city;
    private String price;
    private String start;
    private String end;
    private String description;
    private String image_path;

    public Vacation(String dest_region, String dest_city, String price, String start, String end, String description, String image_path) {
        this.dest_region = dest_region;
        this.dest_city = dest_city;
        this.price = price;
        this.start = start;
        this.end = end;
        this.description = description;
        this.image_path = image_path;
    }

    public String getDest_region() {
        return dest_region;
    }

    public String getDest_city() {
        return dest_city;
    }

    public String getPrice() {
        return price;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_path() {
        return image_path;
    }

    public String[] toStringArray(){
        String[] res = {dest_region,dest_city,price,start,end,description,image_path};
        return res;
    }
    public int getVacationLenght(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try{
            Date startTime = sdf.parse(start);
            Date endTime = sdf.parse(end);
            long diffInMillies = Math.abs(endTime.getTime() - startTime.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            return (int)diff;
        }
        catch (Exception e){
            return 0;
        }
    }
}
