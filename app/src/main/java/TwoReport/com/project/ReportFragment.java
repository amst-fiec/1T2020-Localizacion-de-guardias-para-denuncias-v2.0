package TwoReport.com.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.VectorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment implements OnMapReadyCallback {
    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    Geocoder geocoder;
    List<Address> addresses;
    DataBase dataBase = new DataBase();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        Button report = (Button) view.findViewById(R.id.btnReport);
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(-2.145961, -79.96472, 1);
            String add = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
        }


        report.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                Toast.makeText(getActivity(), "REPORTADO", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng espol = new LatLng(-2.147207, -79.965874);
        mMap.addMarker(new MarkerOptions().position(espol).title("Marker in ESPOL"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(espol));
        for (LatLng latlng : dataBase.getGuardias()) {
            mMap.addMarker(new MarkerOptions().position(latlng).icon(getBitmapDescriptor(R.drawable.ic_guardia)));
        }
        mMap.setMinZoomPreference(18);
    }

    private BitmapDescriptor getBitmapDescriptor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            VectorDrawable vectorDrawable = (VectorDrawable) getResources().getDrawable(id);
            int h = vectorDrawable.getIntrinsicHeight();
            int w = vectorDrawable.getIntrinsicWidth();
            vectorDrawable.setBounds(0, 0, w, h);
            Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bm);
            vectorDrawable.draw(canvas);
            return BitmapDescriptorFactory.fromBitmap(bm);
        } else {
            return BitmapDescriptorFactory.fromResource(id);
        }
    }

}