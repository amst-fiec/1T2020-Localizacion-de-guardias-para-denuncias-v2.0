package TwoReport.com.project;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment implements OnMapReadyCallback {
    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    Geocoder geocoder;
    List<Address> addresses;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        Button report = (Button) view.findViewById(R.id.btnReport);
        final TextView location = (TextView) view.findViewById(R.id.txtLocation);
//        getMapAsync(this);
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
//        return rootView;
//        geocoder = new Geocoder(getActivity(), Locale.getDefault());
//        try {
//            addresses = geocoder.getFromLocation(-2.145961, -79.96472,1);
//            String add = addresses.get(0).getAddressLine(0);
//            location.setText(add);
//        } catch (IOException e) {
//            location.setText("No se pudo :(");
//        }



        report.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                Toast.makeText(getActivity(),"REPORTADO",Toast.LENGTH_LONG).show();
                String ojo = (String) getQueryHttpResponse();
                location.setText(ojo);
            }
        });
        return view;
    }


    public Object getQueryHttpResponse() {
        OkHttpClient httpClient = new OkHttpClient();
        String url = "https://maps.googleapis.com/maps/api/geocode/json";
        HttpUrl.Builder httpBuider = HttpUrl.parse(url).newBuilder();
        httpBuider.addQueryParameter("latlng","40.714224,-73.961452");
        httpBuider.addQueryParameter("key",getString(R.string.google_maps_key));

        Request request = new Request.Builder().url(httpBuider.build()).build();

        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            Log.e(TAG, "error in getting response get request with query string okhttp");
        }
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }



}