package TwoReport.com.project.AdministradorPackage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.database.FirebaseDatabase;

import TwoReport.com.project.Database.DataBaseHandler;
import TwoReport.com.project.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    Handler handler;
    Runnable r;
    TextView conn;
    GoogleMap mMap;
    private SupportMapFragment mMapFragment;



    public MapFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        conn = view.findViewById(R.id.textConectivityAdmin);
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapAdmin);
        mMapFragment.getMapAsync(this);

        checkConn();
        return view;
    }


    @Override
    public void onDestroyView() {
        killHandler(handler,r);
        super.onDestroyView();
    }

    public void killHandler(Handler handler, Runnable r){
        handler.removeCallbacks(r);
    }

    public void checkConn(){
        handler = new Handler();

        r = new Runnable() {
            public void run() {
                ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo nInfo = cm.getActiveNetworkInfo();
                boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
                if (connected == true){
                    conn.setVisibility(View.INVISIBLE);
                    conn.setHeight(0);
                }else{
                    conn.setVisibility(View.VISIBLE);
                    conn.setHeight(80);
                }


                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        DataBaseHandler db = new DataBaseHandler(FirebaseDatabase.getInstance());
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        db.getMarksDenuncias(getContext(),mMap,getResources());
    }
}