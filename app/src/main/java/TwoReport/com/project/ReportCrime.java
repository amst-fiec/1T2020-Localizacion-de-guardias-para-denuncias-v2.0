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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class ReportCrime extends AppCompatActivity {
    private EditText txtDesc;
    private EditText txtfecha;
    private String tipoDelito;
    private Spinner spinner;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user= FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.activity_report_crime);
        spinner = (Spinner) findViewById(R.id.spinnerTipoDelito);
        txtDesc=findViewById(R.id.txtDescripcion);
        txtfecha=findViewById(R.id.txtFecha);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipoCrimen_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void reportar(View view){
        tipoDelito=(String) spinner.getSelectedItem();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Denuncia").child(user.getUid());
        Location ubicacion=new Location(-2.333333, -79.33333);
        CrimeInfo denuncia = new CrimeInfo(txtDesc.getText().toString(),txtfecha.getText().toString(),tipoDelito);
        myRef.push().setValue(denuncia);

        Toast.makeText(getApplicationContext(),"REPORTADO", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }



}