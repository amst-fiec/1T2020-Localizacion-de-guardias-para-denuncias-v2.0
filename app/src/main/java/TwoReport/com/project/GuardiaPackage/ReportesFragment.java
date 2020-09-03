package TwoReport.com.project.GuardiaPackage;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
//        printCardView(getContext(),"Winter Alava", "Me robaron la mochila","Parqueadero de FIEC","https://lh3.googleusercontent.com/a-/AOh14Gj_TlIcw7uTFc0GneXRTNjLlpl5ANu5uO1lHArMyA=s96-c","03/09/2020 17:49:05");
        return view;
    }

    public void getDenuncias(){
        DataBaseHandler db = new DataBaseHandler(FirebaseDatabase.getInstance());
        db.getDenuncias(getContext(),linearLayoutCards);
    }


}