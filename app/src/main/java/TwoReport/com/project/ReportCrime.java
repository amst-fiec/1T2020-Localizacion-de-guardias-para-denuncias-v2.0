package TwoReport.com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import TwoReport.com.project.Database.DataBaseHandler;
import TwoReport.com.project.UsuarioPackage.MainActivity;

public class ReportCrime extends AppCompatActivity {
    private TextView t;
    HashMap<String, String> info_user;
    Location location;
    Spinner spinner;
    EditText txtDescripcion;
    EditText txtLugar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_crime);
        spinner = (Spinner) findViewById(R.id.spinnerTipoDelito);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipoCrimen_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        txtLugar = (EditText) findViewById(R.id.txtDescripcionLugar);


        Intent i = getIntent();
        info_user = (HashMap<String, String>) i.getSerializableExtra("info_user");
        location = new Location((Double) i.getSerializableExtra("user_latitud"),(Double) i.getSerializableExtra("user_longitud"));

    }

    public void reportar(View view){
        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Denuncia");

        Toast.makeText(getApplicationContext(),"REPORTADO", Toast.LENGTH_LONG).show();
        DataBaseHandler db = new DataBaseHandler(FirebaseDatabase.getInstance());
        Date date = Calendar.getInstance().getTime();
        db.enviarReporte(info_user,location,txtDescripcion.getText().toString(),txtLugar.getText().toString(),date,spinner.getSelectedItem().toString(),this);

    }



}