package TwoReport.com.project;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private String nombreCompleto;
    private String emailCompleto;
    private Uri uri;
    private TextView nombretxt;
    private TextView emailtxt;
    private ImageView imageView;
    public ProfileFragment(String nombreCompleto,String emailCompleto, Uri uri) {
        // Required empty public constructor
        this.nombreCompleto = nombreCompleto;
        this.emailCompleto = emailCompleto;
        this.uri = uri;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nombretxt = (TextView) view.findViewById(R.id.txtNombreCompleto);
        emailtxt = (TextView) view.findViewById(R.id.txtEmail);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        nombretxt.setText(nombreCompleto);
        emailtxt.setText(emailCompleto);
        Glide.with(getActivity()).load(uri).into(imageView);
        return view;
    }
}
