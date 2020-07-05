package TwoReport.com.project;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class DataBase {
    ArrayList<LatLng> guardias = new ArrayList<LatLng>();

    public DataBase(){}

    public void setGuardias(){
        Random rand = new Random();
        int numGuardias = rand.nextInt(10)+1;
        for (int i = 0;i<numGuardias;i++){
            double x = (-2.148600  + Math.random() * (-2.146500- (-2.148600)));
            double y = (-79.966500 + Math.random() * (-79.964500 - (-79.966500)));
            LatLng latlng = new LatLng(x,y);
            guardias.add(latlng);
        }

    }

    public ArrayList<LatLng> getGuardias(){
        setGuardias();
        return guardias;
    }

}
