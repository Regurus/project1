package Controller;


public class AddVacationInterface extends VacationInteraction{
    /*public boolean newVacationApprove(String vacation_id){
        String[] res = activeConnection.getByVacationID(vacation_id);
        if(res==null)
            return true;
        else return false;
    }*/

    /**
     * This function receives a string of inputs to be inserted into the database and returns whether or not they are eligible to be inserted.
     * Note that the picture_name field ([6] in the array) can be null. in that case, a default image will be used.
     * @param details -  the tuple we want to verify
     * @return whether or not it is eligible to be inserted into the database.
     */
    public boolean detailsApprove(String[] details){
        for (int i = 0 ; i < details.length ; i++){
            if(details[i].length()<1)
                return false;
        }
        return true;
    }
    public void wiriteToDB(String[] details){
        activeConnection.createTuple(details);
    }

}
