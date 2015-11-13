package daros14.teams;


import android.util.Log;

public class Team {
    private String mName;
    private String mCapitalLetter;
    private String mAddress;
    private String mLatLon;
    private String mJersey;


    public Team(String name, String address,
                String latLon, String jersey) {
        mName = name;
        mAddress = address;
        mLatLon = latLon;
        mJersey = jersey;
        //mCapitalLetter = name.substring(0).toUpperCase();
        mCapitalLetter = name.substring(0, 1).toUpperCase();
        Log.i("Equipo", mCapitalLetter);
    }

    public String getName() {
        return mName;
    }
    public String getCapitalLetter() {
        return mCapitalLetter;
    }
    public String getAddress() {
        return mAddress;
    }
    public String getLatLon() {
        return mLatLon;
    }
    public String getJersey() {
        return mJersey;
    }

}