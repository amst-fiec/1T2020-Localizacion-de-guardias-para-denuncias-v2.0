package TwoReport.com.project.GuardiaPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import TwoReport.com.project.Database.DataBaseHandler;
import TwoReport.com.project.Login;
import TwoReport.com.project.R;
import TwoReport.com.project.UsuarioPackage.ProfileFragment;
import TwoReport.com.project.UsuarioPackage.ReportFragment;
import static TwoReport.com.project.R.id.bottomNavigationView;
import static TwoReport.com.project.R.id.bottomNavigationViewGuard;

public class GuardMainActivity extends AppCompatActivity {
    String user_name;
    String user_email;
    String user_photo;
    String user_id;
    String user_phone;
    HashMap<String, String> info_user;
    TextView bannerGuardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_main);

        Intent i = getIntent();
        info_user = (HashMap<String, String>) i.getSerializableExtra("info_user");
        user_name = info_user.get("user_name");
        user_email = info_user.get("user_email");
        user_photo = info_user.get("user_photo");
        user_id = info_user.get("user_id");
        user_phone = info_user.get("user_phone");
        BottomNavigationView bottomNavigation= findViewById(bottomNavigationViewGuard);
        bottomNavigation.setOnNavigationItemSelectedListener(navListenerGuard);
        getSupportFragmentManager().beginTransaction().replace(R.id.containerGuard, new ReportesFragment(info_user,getApplicationContext())).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListenerGuard = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()){
                case R.id.reportesFragment:
                    selectedFragment = new ReportesFragment(info_user,getApplicationContext());
                    transaction.replace(R.id.containerGuard, selectedFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;

                case R.id.outApp:
                    cerrarSesion();
                    //finish();
                    //System.exit(0);
            }

            return true;
        };
    };

    public void cerrarSesion(){
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(this, Login.class);
        intent.putExtra("msg", "cerrarSesion");
        startActivity(intent);
    }




}