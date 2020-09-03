package TwoReport.com.project.UsuarioPackage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import TwoReport.com.project.Database.DataBaseHandler;
import TwoReport.com.project.Location;
import TwoReport.com.project.R;
import TwoReport.com.project.ReportCrime;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment implements OnMapReadyCallback {
    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    Geocoder geocoder;
    List<Address> addresses;
    HashMap<String, String> info_user;
    Location location;
    TextView conn;
    Handler handler;
    Runnable r;
    Handler handlerGps;
    Runnable rGps;
    FusedLocationProviderClient fusedLocationProviderClient;
    Marker mainMarker = null;
    Button embedGpsBtn;
    Button iButtonGpsBtn;
    ArrayList<Marker> GuardiasMarkers= new ArrayList();;
    Marker ibuttonMarker = null;
    final String COLOR_PRESSED = "#3DDC84";
    final String COLOR_NOT_PRESSED = "#7F7F7F";
    Marker[] markerArray = {null};

    public ReportFragment(HashMap<String, String> info_user){
        this.info_user = info_user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        embedGpsBtn = (Button) view.findViewById(R.id.btnGps);
        iButtonGpsBtn =(Button) view.findViewById(R.id.btnIbutton);
        Button report = (Button) view.findViewById(R.id.btnReport);
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(-2.145961, -79.96472, 1);
            String add = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
        }
        conn = (TextView) view.findViewById(R.id.textConectivity);
        location = new Location(0,0);
        embedGpsBtn.setPressed(true);
        iButtonGpsBtn.setPressed(false);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                killHandler(handler,r);
//                getActivity().finish();
                Intent i = new Intent(getContext(), ReportCrime.class);
                i.putExtra("info_user",info_user);
                i.putExtra("user_latitud",location.getLatitud());
                i.putExtra("user_longitud",location.getLongitud());
                startActivity(i);
//                Toast.makeText(getActivity(), "REPORTADO", Toast.LENGTH_LONG).show();
            }
        });
        checkConn();


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        checkGps();


        return view;
    }

    @Override
    public void onDestroyView() {
        killHandler(handler,r);
        super.onDestroyView();
    }

    public void killHandler(Handler handler,Runnable r){
        handler.removeCallbacks(r);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        DataBaseHandler db = new DataBaseHandler(FirebaseDatabase.getInstance());
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


        embedGpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                embedGpsBtn.setPressed(true);
                iButtonGpsBtn.setPressed(false);
                embedGpsBtn.setBackgroundColor(Color.parseColor(COLOR_PRESSED));
                iButtonGpsBtn.setBackgroundColor(Color.parseColor(COLOR_NOT_PRESSED));
                Toast.makeText(getContext(),"Gps Embebido Activado",Toast.LENGTH_LONG).show();
                handler.postDelayed(rGps, 1000);
                db.getGeoLocalizacion(info_user.get("user_id"),mMap,getResources(),location,false,GuardiasMarkers,markerArray);
            }
        });

        iButtonGpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mMap.clear();
                embedGpsBtn.setPressed(false);
                iButtonGpsBtn.setPressed(true);
                embedGpsBtn.setBackgroundColor(Color.parseColor(COLOR_NOT_PRESSED));
                iButtonGpsBtn.setBackgroundColor(Color.parseColor(COLOR_PRESSED));
                Toast.makeText(getContext(),"Gps iButton Activado",Toast.LENGTH_LONG).show();
                handler.removeCallbacks(rGps);
                if (mainMarker != null){
                    mainMarker.remove();
                }

                db.getGeoLocalizacion(info_user.get("user_id"),mMap,getResources(),location,true,GuardiasMarkers,markerArray);
            }
        });
        firstGpsEmbed();

    }

    public void checkGps(){
        handlerGps = new Handler();
        rGps = new Runnable() {
            public void run() {
                try{
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
                        @Override
                        public void onComplete(@NonNull Task<android.location.Location> task) {
                            android.location.Location location = task.getResult();
                            if (location != null){
                                LatLng posicion = new LatLng(location.getLatitude(),location.getLongitude());
                                if (mainMarker != null){
                                    mainMarker.remove();
                                }
                                mainMarker = mMap.addMarker(new MarkerOptions().position(posicion).title("Estas En el Empalme"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(posicion));
                                mMap.setMinZoomPreference(18);
                            }
                        }
                    });
                }else{
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }}catch (NullPointerException e){}


                handler.postDelayed(this, 1000);
            }
        };


    }

    public void firstGpsEmbed(){
        DataBaseHandler db = new DataBaseHandler(FirebaseDatabase.getInstance());
        embedGpsBtn.setPressed(true);
        iButtonGpsBtn.setPressed(false);
        embedGpsBtn.setBackgroundColor(Color.parseColor(COLOR_PRESSED));
        iButtonGpsBtn.setBackgroundColor(Color.parseColor(COLOR_NOT_PRESSED));
        Toast.makeText(getContext(),"Gps Embebido Activado",Toast.LENGTH_LONG).show();
        handler.postDelayed(rGps, 1000);
        db.getGeoLocalizacion(info_user.get("user_id"),mMap,getResources(),location,false,GuardiasMarkers,markerArray);
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
//    private BitmapDescriptor getBitmapDescriptor(int id) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            VectorDrawable vectorDrawable = (VectorDrawable) getResources().getDrawable(id);
//            int h = vectorDrawable.getIntrinsicHeight();
//            int w = vectorDrawable.getIntrinsicWidth();
//            vectorDrawable.setBounds(0, 0, w, h);
//            Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//            Canvas canvas = new Canvas(bm);
//            vectorDrawable.draw(canvas);
//            return BitmapDescriptorFactory.fromBitmap(bm);
//        } else {
//            return BitmapDescriptorFactory.fromResource(id);
//        }
//    }


}