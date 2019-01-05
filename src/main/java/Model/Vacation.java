package Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Vacation {
    String dest_region;
    String dest_city;
    String price;
    String start;
    String end;
    String description;
    String image_path;
    String listing_id;
    String owner;
    String applicant;




    public Vacation(String dest_region, String dest_city, String price, String start, String end, String description, String image_path, String listing_id, String owner, String applicant) {
        this.dest_region = dest_region;
        this.dest_city = dest_city;
        this.price = price;
        this.start = start;
        this.end = end;
        this.description = description;
        this.image_path = image_path;
        this.listing_id = listing_id;
        this.owner = owner;
        this.applicant = applicant;
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
    public String getListing_id() {
        return listing_id;
    }
    public String getOwner() {
        return owner;
    }
    public String getApplicant() {
        return applicant;
    }
    public String[] toStringArray(){
        return new String[] {dest_region,dest_city,price,start,end,description,image_path,listing_id,owner,applicant};
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
    public void setDest_region(String dest_region) {
        this.dest_region = dest_region;
    }
    public void setDest_city(String dest_city) {
        this.dest_city = dest_city;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public void setEnd(String end) {
        this.end = end;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
    public void setListing_id(String listing_id) {
        this.listing_id = listing_id;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }
}
