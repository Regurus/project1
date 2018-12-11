package Model;

public class PurchasedVacation extends Vacation{
    private String purchase_date;

    public PurchasedVacation(String dest_region, String dest_city, String price, String start, String end, String description, String image_path, String listing_id, String owner, String applicant, String purchase_date) {
        super(dest_region, dest_city, price, start, end, description, image_path, listing_id, owner, applicant);
        this.purchase_date = purchase_date;
    }

    public PurchasedVacation(Vacation v, String purchase_date){
        super(v.getDest_region(),
              v.getDest_city(),
              v.getPrice(),
              v.getStart(),
              v.getEnd(),
              v.getDescription(),
              v.getImage_path(),
              v.getListing_id(),
              v.getOwner(),
              v.getApplicant());
        this.purchase_date = purchase_date;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public String[] toStringArray(){
        String[] res = {dest_region,dest_city,price,start,end,description,image_path,listing_id,owner,applicant,purchase_date};
        return res;
    }
}
