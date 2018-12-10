package Model;

public class PurchaseApplication {
    private String vacation_id;
    private String applicant;

    public PurchaseApplication(String vacation_id, String applicant) {
        this.vacation_id = vacation_id;
        this.applicant = applicant;
    }

    public String getVacation_id() {
        return vacation_id;
    }

    public String getApplicant() {
        return applicant;
    }

    public String[] toStringArray(){
        String[] res = {vacation_id,applicant};
        return res;
    }
}
