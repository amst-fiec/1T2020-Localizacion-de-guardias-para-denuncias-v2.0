package TwoReport.com.project.UsuarioPackage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import TwoReport.com.project.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    String user_name;
    String user_email;
    String user_photo;
    private TextView nombretxt;
    private TextView emailtxt;
    private ImageView photoView;
    public ProfileFragment(String nombreCompleto,String emailCompleto, String uri) {
        // Required empty public constructor
        this.user_name = nombreCompleto;
        this.user_email = emailCompleto;
        this.user_photo = uri;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nombretxt = (TextView) view.findViewById(R.id.txtNombreCompleto);
        emailtxt = (TextView) view.findViewById(R.id.txtEmail);
        photoView = (ImageView) view.findViewById(R.id.imv_foto);
        nombretxt.setText(user_name);
        emailtxt.setText(user_email);
//        Glide.with(getActivity()).load(uri).into(imageView);
        Picasso.with(getContext()).load(user_photo).into(photoView);
        return view;
    }
}
