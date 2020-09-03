package TwoReport.com.project.GuardiaPackage;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import TwoReport.com.project.Database.DataBaseHandler;
import TwoReport.com.project.R;


public class ReportesFragment extends Fragment {

    public HashMap<String, String> info_user;
    public TextView bannerGuardView;
    public LinearLayout linearLayoutCards;
    public Context context;
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
        getDenuncias();
        printCardView("Winter Alava", "Me robaron la mochila","Parqueadero de FIEC");
        return view;
    }

    public void getDenuncias(){
        DataBaseHandler db = new DataBaseHandler(FirebaseDatabase.getInstance());
        db.getDenuncias();
    }

    public void printCardView(String nombre, String descripcion, String lugar){
        CardView cardView = new CardView(getContext());
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        cardView.setLayoutParams(layoutParams);
        cardView.setRadius(15);
        cardView.setCardBackgroundColor(Color.GRAY);
        cardView.setMaxCardElevation(30);
        cardView.setMaxCardElevation(6);
        LinearLayout linearLayoutH = new LinearLayout(getContext());
        linearLayoutH.setGravity(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParamsH = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutH.setWeightSum(4);
        linearLayoutH.setLayoutParams(layoutParamsH);

        ImageView perfil = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParamsIV = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsIV.weight = 2;
        perfil.setLayoutParams(layoutParamsIV);
        linearLayoutH.addView(perfil);

        LinearLayout linearLayoutH1 = new LinearLayout(getContext());
        linearLayoutH1.setGravity(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParamsLL = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsLL.weight = 2;
        linearLayoutH1.setLayoutParams(layoutParamsLL);


        TextView tx = new TextView(getContext());
        tx.setLayoutParams(layoutParams);
        tx.setText("Holaaaaaaa");
        tx.setTextColor(Color.BLACK);
        linearLayoutH1.addView(tx);
        linearLayoutH.addView(linearLayoutH1);





        cardView.addView(linearLayoutH);

        linearLayoutCards.addView(cardView);
    }
}