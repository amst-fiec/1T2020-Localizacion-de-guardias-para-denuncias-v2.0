package TwoReport.com.project;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigation= findViewById(R.id.bottomNavigationView);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ReportFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()){
                case R.id.reportFragment:
                    selectedFragment = new ReportFragment();
                    break;
                case R.id.profileFragment:
                    selectedFragment = new ProfileFragment();
                    break;
                case R.id.outApp:
                    finish();
                    System.exit(0);
            }
            transaction.replace(R.id.container, selectedFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        };
    };
}
