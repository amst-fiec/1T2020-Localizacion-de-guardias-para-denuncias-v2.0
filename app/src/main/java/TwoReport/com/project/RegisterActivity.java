package TwoReport.com.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import TwoReport.com.project.Database.DataBaseHandler;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String user_name;
    String user_email;
    String user_photo;
    String user_id;
    String user_phone;
    private TextView nombretxt;
    private TextView emailtxt;
    private ImageView photoView;
    private EditText phoneView;
    private Spinner spinner;
    public String tipoDeUsuarioSeleccionado;
    private EditText tokenText;
    private TextView tokenView;
    HashMap<String, String> info_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent i = getIntent();
        info_user = (HashMap<String, String>) i.getSerializableExtra("info_user");
        user_name = info_user.get("user_name");
        user_email = info_user.get("user_email");
        user_photo = info_user.get("user_photo");
        user_id = info_user.get("user_id");
        user_phone = info_user.get("user_phone");

        nombretxt = (TextView) findViewById(R.id.txtNombreCompletoRA);
        emailtxt = (TextView) findViewById(R.id.txtEmailRA);
        photoView = (ImageView) findViewById(R.id.imv_fotoRA);
        phoneView = (EditText) findViewById(R.id.txtPhoneRA);
        nombretxt.setText(user_name);
        emailtxt.setText(user_email);
        phoneView.setText(user_phone);
        Picasso.with(this).load(user_photo).into(photoView);

        spinner = (Spinner) findViewById(R.id.spinerTipoUsuario);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tiposDeUsuario, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        tokenText = findViewById(R.id.txtToken);
        tokenView = findViewById(R.id.tokenViews);


    }


    public void registrar(View v){
        DataBaseHandler db = new DataBaseHandler(FirebaseDatabase.getInstance());
        if (spinner.getSelectedItem().toString().equals("Usuarios")){
            Usuario user;
            if (user_phone == null){
                user = new Usuario(user_name,user_email,phoneView.getText().toString(),user_photo);
            }else{
                user = new Usuario(user_name,user_email,user_phone,user_photo);
            }
            db.registrarUsuario(user_id,user,this,info_user);
        }else if(spinner.getSelectedItem().toString().equals("Guardias")){
            Guardia guardia;
            if (user_phone == null){
                guardia = new Guardia(user_name,user_email,phoneView.getText().toString(),user_photo,0.0,0.0);
            }else{
                guardia = new Guardia(user_name,user_email,user_phone,user_photo,0.0,0.0);
            }
            db.registrarGuardia(user_id,guardia,this,tokenText.getText().toString(),info_user);
        }else if(spinner.getSelectedItem().toString().equals("Administradores")){
            Administrador admin;
            if (user_phone == null){
                admin = new Administrador(user_name,user_email,phoneView.getText().toString(),user_photo);
            }else{
                admin = new Administrador(user_name,user_email,user_phone,user_photo);
            }
            db.registrarAdministrador(user_id,admin,this,tokenText.getText().toString(),info_user);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        tipoDeUsuarioSeleccionado = (String) adapterView.getItemAtPosition(i);
        if (tipoDeUsuarioSeleccionado.equals("Usuarios")){
            tokenView.setVisibility(View.INVISIBLE);
            tokenText.setVisibility(View.INVISIBLE);
        }
        else{
            tokenView.setVisibility(View.VISIBLE);
            tokenText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}