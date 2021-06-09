 package umn.ac.id.bookingin;

public class UserPlaceInfo {
    private String placeName;
    private String placeLoc;
    private String placeDesc;

    public UserPlaceInfo(){

    }
    /*public UserPlaceInfo(String placeName, String placeLoc, String placeDesc){
        this.placeName = placeName;
        this.placeLoc = placeLoc;
        this.placeDesc = placeDesc;
    }*/

    public String getPlaceName(){
        return placeName;
    }

    public void setPlaceName(String placeName){
        this.placeName = placeName;
    }

    public String getPlaceLoc(){
        return placeLoc;
    }

    public void setPlaceLoc(String placeLoc){
        this.placeLoc = placeLoc;
    }

    public String getPlaceDesc(){
        return placeDesc;
    }

    public void setPlaceDesc(String placeDesc){
        this.placeDesc = placeDesc;
    }
}
