package TwoReport.com.project;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import java.util.HashMap;

public class Login extends AppCompatActivity {
    static final int GOOGLE_SIGN_IN = 123;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    Button btn_login;
    EditText txtEmail;
    EditText txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        if(msg != null){
            if(msg.equals("cerrarSesion")){
                cerrarSesion();
                }
            }


        txtEmail= findViewById(R.id.txtUsuario);
        txtPass= findViewById(R.id.txtContra);
    }

    private void cerrarSesion() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                task -> updateUI(null));
        }

    public void iniciarSesionGoogle(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        if(verConexionInternet()){
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(
                    data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("TAG", "Fallo el inicio de sesión con google.", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),
                null);
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
                } else {
                System.out.println("error");
                updateUI(null);
                }
            });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

            HashMap<String, String> info_user = new HashMap<String, String>();
            info_user.put("user_name", user.getDisplayName());
            info_user.put("user_email", user.getEmail());
            info_user.put("user_photo", String.valueOf(user.getPhotoUrl()));
            info_user.put("user_id", user.getUid());
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("info_user", info_user);
            startActivity(intent);
            } else {
            System.out.println("sin registrarse");
            }
    }

    public boolean verConexionInternet(){
        try {
            ConnectivityManager con = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert con != null;
            NetworkInfo networkInfo = con.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }else{
                Toast.makeText(Login.this,"No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                return false;
            }
        }catch (NullPointerException n){
            return false;
        }
    }

    public void inicioSesion(View view){
        if(!this.validarInicioSesión()){
            Toast.makeText(this, "Por favor revise su usuario y contraseña",Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(txtEmail.toString(),txtPass.toString()).addOnCompleteListener(this, task -> {
                if(task.isSuccessful()){

                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                }else{

                    Toast.makeText(Login.this, "Por favor revise que sus credenciales sean correctas.",
                            Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> Toast.makeText(Login.this, ""+e.getMessage(),
                    Toast.LENGTH_SHORT).show());
        }

    }

    public boolean validarInicioSesión(){
        if (TextUtils.isEmpty(txtEmail.getText())||TextUtils.isEmpty(txtPass.getText())){
            Toast.makeText(Login.this, "Por favor ingrese su usuario y contraseña",
                    Toast.LENGTH_SHORT).show();
            txtEmail.setFocusable(true);
            txtPass.setFocusable(true);

        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()){
            Toast.makeText(Login.this,"El email ingresado no es válido", Toast.LENGTH_SHORT).show();
            txtEmail.setFocusable(true);
            return false;
        }
        return true;
    }
}