package TwoReport.com.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.VectorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    public String guardiaUid;
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
        guardiaUid = info_crime.get("guardia_id");
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
        FirebaseDatabase.getInstance().getReference().child("Guardias").child(guardiaUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Guardia guardia = snapshot.getValue(Guardia.class);
                if (guardia.getLatitud() != null){
                    mMap = googleMap;
                    LatLng crime = new LatLng(lat, lon);
                    LatLng guard = new LatLng(guardia.getLatitud(),guardia.getLongitud());
                    mMap.addMarker(new MarkerOptions().position(crime).title("Marker in ESPOL"));
                    mMap.addMarker(new MarkerOptions().position(guard).icon(getBitmapDescriptor(R.drawable.ic_guardia,getResources())).title("Guardia"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(crime));
                    mMap.setMinZoomPreference(18);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private BitmapDescriptor getBitmapDescriptor(int id, Resources resources) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            VectorDrawable vectorDrawable = (VectorDrawable) resources.getDrawable(id);
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