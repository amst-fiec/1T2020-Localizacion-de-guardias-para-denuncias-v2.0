package TwoReport.com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class CrimeInfoActivity extends AppCompatActivity implements OnMapReadyCallback {
    String victim_name;
    String victim_email;
    String victim_photo;
    String victim_phone;
    String issue;
    double lat;
    double lon;

    TextView textVictimName;
    TextView textVictimEmail;
    TextView textVictimIssue;
    TextView textVictimPhone;
    ImageView imagePhoto;

    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_info);

        textVictimName = findViewById(R.id.txtVictimName);
        textVictimEmail = findViewById(R.id.txtVictimEmail);
        textVictimIssue = findViewById(R.id.txtVictimIssue);
        textVictimPhone = findViewById(R.id.txtVictimPhone);
        imagePhoto = findViewById(R.id.imv_foto);

        Intent i = getIntent();
        HashMap<String, String> info_crime = (HashMap<String, String>) i.getSerializableExtra("info_crime");
        victim_name = info_crime.get("victim_name");
        victim_email = info_crime.get("victim_email");
        victim_photo = info_crime.get("victim_photo");
        victim_phone = info_crime.get("victim_phone");
        issue = info_crime.get("victim_issue");
        lat = Double.parseDouble(info_crime.get("victim_lat"));
        lon = Double.parseDouble(info_crime.get("victim_lon"));

        textVictimName.setText(victim_name);
        textVictimEmail.setText(victim_email);
        textVictimPhone.setText(victim_phone);
        textVictimIssue.setText(issue);

        Picasso.with(getApplicationContext()).load(victim_photo).into(imagePhoto);

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng crime = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(crime).title("Marker in ESPOL"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(crime));
        mMap.setMinZoomPreference(18);
    }
}