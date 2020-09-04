package TwoReport.com.project.GuardiaPackage;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import TwoReport.com.project.Database.DataBaseHandler;
import TwoReport.com.project.R;


public class ReportesFragment extends Fragment {

    public HashMap<String, String> info_user;
    public TextView bannerGuardView;
    public LinearLayout linearLayoutCards;
    public Context context;
    public Handler handler;
    public Runnable r;
    TextView conn;
    TextView noHayDelitosView;
    public ReportesFragment() {
        // Required empty public constructor
    }

    public ReportesFragment(HashMap<String, String> info_user,Context context){
        this.info_user = info_user;
        this.context = context;

    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reportes, container, false);
        bannerGuardView = view.findViewById(R.id.txtBannerGuard);
        bannerGuardView.setText("Guardia: "+info_user.get("user_name"));
        linearLayoutCards = view.findViewById(R.id.linearLayoutCards);
        conn = view.findViewById(R.id.textConectivityGuard);
        noHayDelitosView = new TextView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        noHayDelitosView.setText("AUN NO HAY DELITOS REPORTADOS");
        noHayDelitosView.setGravity(Gravity.CENTER_HORIZONTAL);
        noHayDelitosView.setTextColor(Color.GRAY);
        noHayDelitosView.setTextSize(20);
        noHayDelitosView.setLayoutParams(layoutParams);

        getDenuncias();
        checkConn();
        return view;
    }

    public void getDenuncias(){
        DataBaseHandler db = new DataBaseHandler(FirebaseDatabase.getInstance());
        db.getDenuncias(getContext(),linearLayoutCards,noHayDelitosView);
    }

    @Override
    public void onDestroyView() {
        killHandler(handler,r);
        super.onDestroyView();
    }

    public void killHandler(Handler handler,Runnable r){
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


}