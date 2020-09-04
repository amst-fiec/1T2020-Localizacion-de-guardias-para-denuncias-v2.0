package TwoReport.com.project.Database;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import TwoReport.com.project.Administrador;
import TwoReport.com.project.AdministradorPackage.AdminMainActivity;
import TwoReport.com.project.CardCrime;
import TwoReport.com.project.CrimeInfo;
import TwoReport.com.project.CrimeInfoActivity;
import TwoReport.com.project.Guardia;
import TwoReport.com.project.GuardiaPackage.GuardMainActivity;
import TwoReport.com.project.Location;
import TwoReport.com.project.Login;
import TwoReport.com.project.R;
import TwoReport.com.project.RegisterActivity;
import TwoReport.com.project.Usuario;
import TwoReport.com.project.UsuarioPackage.MainActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DataBaseHandler {
    FirebaseDatabase db;

    public DataBaseHandler(FirebaseDatabase db) {
        this.db = db;
    }

    public void iniciarSesion(Context contexto,String uid,HashMap<String,String> info_user,GoogleSignInClient mGoogleSignInClient){
        DatabaseReference UserRef = this.db.getReference("Usuarios/"+uid);
        DatabaseReference GuardRef = this.db.getReference("Guardias/"+uid);
        DatabaseReference AdminRef = this.db.getReference("Administradores/"+uid);

        UserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Intent intent = new Intent(contexto, MainActivity.class);
                    intent.putExtra("info_user", info_user);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    contexto.startActivity(intent);
                }else{
                    GuardRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                            if (snapshot1.exists()){
                                Intent intent = new Intent(contexto, GuardMainActivity.class);
                                intent.putExtra("info_user", info_user);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                contexto.startActivity(intent);
                            }else{
                                AdminRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                        if(snapshot2.exists()){
                                            Intent intent = new Intent(contexto, AdminMainActivity.class);
                                            intent.putExtra("info_user", info_user);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            contexto.startActivity(intent);
                                        }else{
                                            Toast.makeText(contexto,"Usuario no registrado",Toast.LENGTH_LONG).show();
                                            FirebaseAuth.getInstance().signOut();
                                            mGoogleSignInClient.signOut();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void registrarUsuario(String uid, Usuario usuario, Context contexto,HashMap<String, String> info_user){
        DatabaseReference userRef = this.db.getReference("Usuarios/"+uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Toast.makeText(contexto,"Este correo ya ha sido usado",Toast.LENGTH_LONG).show();
                }else{
                    userRef.setValue(usuario)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Write was successful!
                                    Toast.makeText(contexto,"Usuario registrado correctamente",Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(contexto,MainActivity.class);
                                    i.putExtra("info_user",info_user);
                                    contexto.startActivity(i);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Write failed
                                    Toast.makeText(contexto,"Fallo en registro de Usuario",Toast.LENGTH_LONG).show();

                                }
                            });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void registrarGuardia(String uid, Guardia guardia, Context contexto,String token,HashMap<String, String> info_user){
        DatabaseReference userRef = this.db.getReference("Guardias/"+uid);
        DatabaseReference tokenRef = this.db.getReference("Tokens/"+token);
        tokenRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot0) {
                if (snapshot0.exists()){
                    if (snapshot0.getValue().toString().equals("Guardias")){
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    Toast.makeText(contexto,"Este correo ya ha sido usado",Toast.LENGTH_LONG).show();
                                }else{
                                    userRef.setValue(guardia)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // Write was successful!
                                                    Toast.makeText(contexto,"Guardia registrado correctamente",Toast.LENGTH_LONG).show();
                                                    Intent i = new Intent(contexto,GuardMainActivity.class);
                                                    i.putExtra("info_user",info_user);
                                                    contexto.startActivity(i);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Write failed
                                                    Toast.makeText(contexto,"Fallo en registro de Guardia",Toast.LENGTH_LONG).show();
                                                }
                                            });
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void registrarAdministrador(String uid, Administrador administrador, Context contexto,String token,HashMap<String, String> info_user){
        DatabaseReference userRef = this.db.getReference("Administradores/"+uid);
        DatabaseReference tokenRef = this.db.getReference("Tokens/"+token);
        tokenRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot0) {
                if (snapshot0.exists()){
                    if (snapshot0.getValue().toString().equals("Administradores")){
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    Toast.makeText(contexto,"Este correo ya ha sido usado",Toast.LENGTH_LONG).show();
                                }else{
                                    userRef.setValue(administrador)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // Write was successful!
                                                    Toast.makeText(contexto,"Administrador registrado correctamente",Toast.LENGTH_LONG).show();
                                                    Intent i = new Intent(contexto,AdminMainActivity.class);
                                                    i.putExtra("info_user",info_user);
                                                    contexto.startActivity(i);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Write failed
                                                    Toast.makeText(contexto,"Fallo en registro de Administrador",Toast.LENGTH_LONG).show();
                                                }
                                            });
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }else{
                        Toast.makeText(contexto,"Token invalido",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(contexto,"Token invalido",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void getPhoneNumer(String uid, TextView phoneView){
        DatabaseReference userRef = this.db.getReference().child("Usuarios").child(uid).child("telefono");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String number = snapshot.getValue().toString();
                phoneView.setText(number);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getGeoLocalizacion(Context context,String uid, GoogleMap mMap, Resources resources, Location location, boolean incluirUsuario, ArrayList<Marker> guardiasMarkers,Marker[] markerArray,Location locationByGpsEmbed){
        DatabaseReference ubicacionRef = this.db.getReference("Ubicacion/"+uid);
        DatabaseReference guardiasRef = this.db.getReference("Guardias/");
        ArrayList<Marker> markersGuardias = new ArrayList();
        ValueEventListener valueEventListenerGuardias = new ValueEventListener() {
            ArrayList<Marker> markers = new ArrayList();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (Marker m:markers){
                    m.remove();

                }
//                for (Marker m1:guardiasMarkers){
//                    m1.remove();
//                }
                boolean cercanos = false;
                for (DataSnapshot guardSnapShot:snapshot.getChildren()){
                    System.out.println(guardSnapShot);
                    Double latitud = (Double) guardSnapShot.child("latitud").getValue();
                    Double longitud = (Double) guardSnapShot.child("longitud").getValue();
                    LatLng latLngGuard = new LatLng(latitud,longitud);
                    Marker marker = mMap.addMarker(new MarkerOptions().position(latLngGuard).icon(getBitmapDescriptor(R.drawable.ic_guardia,resources)));
                    markers.add(marker);
                    guardiasMarkers.add(marker);
                    markersGuardias.add(marker);
                    Double lat1=marker.getPosition().latitude;
                    Double lat2=location.getLatitud();
                    Double long1=marker.getPosition().longitude;
                    Double long2=location.getLongitud();
                    Double R = 6371e3;
                    Double phi1 = lat1 * Math.PI/180;
                    Double phi2 = lat2 * Math.PI/180;
                    Double deltaphi = (lat2-lat1) * Math.PI/180;
                    Double deltalambda = (long2-long1) * Math.PI/180;

                    Double a = Math.sin(deltaphi/2) * Math.sin(deltaphi/2) + Math.cos(phi1) * Math.cos(phi2) * Math.sin(deltalambda/2) * Math.sin(deltalambda/2);
                    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                    Double d = R * c;

                    cercanos = cercanos || (d<100);
                    System.out.println("DISTANCIA: \n");
                    System.out.println(d);
                }
                if(cercanos==false){
                    if(incluirUsuario==true){
                    Toast.makeText(context,"No existen guardias cercanos a 100 mts",Toast.LENGTH_LONG).show();
                    }
                }



            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };

        ValueEventListener valueEventListenerUsuario = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                        if (markerArray[0] != null){
                            markerArray[0].remove();
                        }
                        Double latitud = (Double) snapshot.child("latitud").getValue();
                        Double longitud = (Double) snapshot.child("longitud").getValue();
                        location.setLatitud(latitud);
                        location.setLongitud(longitud);
                        LatLng posicion = new LatLng(latitud, longitud);

                        markerArray[0] = mMap.addMarker(new MarkerOptions().position(posicion).title("Estas Aquí"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(posicion));
                        mMap.setMinZoomPreference(18);
                        if(!incluirUsuario){
                            if(markerArray[0] != null){
                            markerArray[0].remove();}
//                            location.setLongitud(locationByGpsEmbed.getLongitud());
//                            location.setLatitud(locationByGpsEmbed.getLatitud());
                        }



                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}

        };

        guardiasRef.addValueEventListener(valueEventListenerGuardias);
        ubicacionRef.addValueEventListener(valueEventListenerUsuario);
    }

    public void isRegister(Context contexto, String uid, HashMap<String, String> info_user, GoogleSignInClient googleSignInClient,FirebaseAuth mAuth){
        DatabaseReference guardRef = this.db.getReference("Guardias/"+uid);
        DatabaseReference adminRef = this.db.getReference("Administradores/"+uid);
        DatabaseReference userRef = this.db.getReference("Usuarios/"+uid);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Toast.makeText(contexto,"El usuario ya está registrado", Toast.LENGTH_LONG).show();
                    mAuth.signOut();
                    googleSignInClient.signOut();

                }else{
                    guardRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                            if (snapshot1.exists()){
                                Toast.makeText(contexto,"El usuario ya está registrado", Toast.LENGTH_LONG).show();
                                mAuth.signOut();
                                googleSignInClient.signOut();

                            }else{
                                adminRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                        if(snapshot2.exists()){
                                            Toast.makeText(contexto,"El usuario ya está registrado", Toast.LENGTH_LONG).show();
                                            mAuth.signOut();
                                            googleSignInClient.signOut();
                                        }else{
                                            Intent i = new Intent(contexto, RegisterActivity.class);
                                            i.putExtra("info_user", info_user);
                                            contexto.startActivity(i);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void enviarReporte(HashMap<String, String> info_user, Location ubicacion, String descripcion,String lugar, Date date, String tipoDeCrimen,Context contexto){
        Usuario user = new Usuario(info_user.get("user_name"),info_user.get("user_email"),info_user.get("user_phone"),info_user.get("user_photo"));
        final String DATE_FORMAT = "dd-MM-yyyy kk:mm:ss";
        Long tsLong = System.currentTimeMillis()/1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String fecha = dateFormat.format(date);
        CrimeInfo reporte = new CrimeInfo(user,ubicacion,descripcion,lugar,fecha,tipoDeCrimen,tsLong);
        this.db.getReference().child("Denuncia").push().setValue(reporte)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(contexto,"REPORTADO", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(contexto, MainActivity.class);
                intent.putExtra("info_user",info_user);
                contexto.startActivity(intent);
            }
        });
    }

    public void getDenuncias(Context context, LinearLayout linearLayout, TextView noHayDelitosView){
//        linearLayout.setPadding(0,0,0,50);


        this.db.getReference().child("Denuncia").orderByChild("tsLong")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            linearLayout.removeAllViews();
                            System.out.println("Snapshot del guardia y denuncias\n");
//                        System.out.println(snapshot);
                            System.out.println("Impresion en orden:  ");
                            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                CrimeInfo crime = dataSnapshot.getValue(CrimeInfo.class);

                                System.out.println(crime);

                                CardCrime car = printCardView(context,crime);
                                car.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(context, CrimeInfoActivity.class);
                                        HashMap<String, String> info_crime = new HashMap<String, String>();

                                        info_crime.put("victim_name",car.getCrime().getUsuario().getNombreCompleto());
                                        info_crime.put("victim_email",car.getCrime().getUsuario().getEmail());
                                        info_crime.put("victim_photo",car.getCrime().getUsuario().getPhotoUrl());
                                        info_crime.put("victim_phone",car.getCrime().getUsuario().getTelefono());
                                        info_crime.put("victim_issue",car.getCrime().getDescripcion());
                                        info_crime.put("victim_lat",String.valueOf(car.getCrime().getUbicacion().getLatitud()));
                                        info_crime.put("victim_lon",String.valueOf(car.getCrime().getUbicacion().getLongitud()));
                                        i.putExtra("info_crime",info_crime);
                                        context.startActivity(i);
                                    }
                                });
                                linearLayout.addView(car);
                            }
                            System.out.println("FIN DE IMPRESION");
                        }else{
                            linearLayout.removeAllViews();
                            linearLayout.addView(noHayDelitosView);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context,"No tiene conexión a internet",Toast.LENGTH_LONG).show();
                    }
                });

    }

    public CardCrime printCardView(Context context, CrimeInfo crime ){
        String nombre = crime.getUsuario().getNombreCompleto();
        String descripcion = crime.getDescripcion();
        String lugar= crime.getLugar();
        String uriPhoto = crime.getUsuario().getPhotoUrl();
        String fecha = crime.getFecha();
        CardCrime cardView = new CardCrime(context,crime);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = 80;

        cardView.setLayoutParams(layoutParams);
        cardView.setRadius(15);
        cardView.setCardBackgroundColor(Color.parseColor("#E4E6E8"));
//        cardView.setMaxCardElevation(30);
//        cardView.setMaxCardElevation(6);
        LinearLayout linearLayoutH = new LinearLayout(context);
        linearLayoutH.setGravity(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParamsH = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutH.setWeightSum(4);
        linearLayoutH.setLayoutParams(layoutParamsH);

        ImageView perfil = new ImageView(context);
        LinearLayout.LayoutParams layoutParamsIV = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsIV.weight = 1;
        perfil.setLayoutParams(layoutParamsIV);
        linearLayoutH.addView(perfil);
        perfil.setMinimumHeight(250);
//        layoutParamsIV.gravity = Gravity.START;
        Picasso.with(context).load(uriPhoto).into(perfil);

        LinearLayout linearLayoutH1 = new LinearLayout(context);
        linearLayoutH1.setGravity(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParamsLL = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsLL.weight = 3;
        linearLayoutH1.setPadding(40,15,5,0);
        linearLayoutH1.setLayoutParams(layoutParamsLL);


        TextView tx = new TextView(context);
        tx.setLayoutParams(layoutParams);
        String a = fecha+"\n"+nombre+"\n"+descripcion+"\n"+lugar;
        tx.setText(a);
        tx.setTextColor(Color.parseColor("#F47F25"));
        tx.setGravity(Gravity.CENTER);
//        TextView txname = new TextView(getContext());
//        txname.setLayoutParams(layoutParams);
//        txname.setTextColor(Color.BLACK);
//        txname.setText(nombre);

        linearLayoutH1.addView(tx);
//        linearLayoutH1.addView(txname);
//
//        TextView txdescripcion = new TextView(getContext());
//        txdescripcion.setLayoutParams(layoutParams);
//        txdescripcion.setText(descripcion);
//        txdescripcion.setTextColor(Color.BLACK);
//        linearLayoutH1.addView(txdescripcion);

        linearLayoutH.addView(linearLayoutH1);


        cardView.addView(linearLayoutH);
        return cardView;
//        linearLayoutCards.addView(cardView);
    }

    public void getMarksDenuncias(Context context, GoogleMap mMap, Resources resources){
        DatabaseReference delitosRef = this.db.getReference().child("Denuncia");
        delitosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mMap.clear();
                if (snapshot.exists()){
                    for(DataSnapshot snapshot1:snapshot.getChildren()){

                        CrimeInfo denuncia = snapshot1.getValue(CrimeInfo.class);
                        System.out.println(denuncia);
                        Location location = denuncia.getUbicacion();
                        LatLng latLng = new LatLng(location.getLatitud(),location.getLongitud());
                        mMap.addMarker(new MarkerOptions().position(latLng).icon(getBitmapDescriptor(R.drawable.ic_person,resources)).title(denuncia.getUsuario().getNombreCompleto()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.setMinZoomPreference(18);
                    }
                }else{
                    Toast.makeText(context,"Aún no hay denuncias registradas",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private BitmapDescriptor getBitmapDescriptor(int id, Resources resources) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            VectorDrawable vectorDrawable = (VectorDrawable) resources.getDrawable(id);
            int h = vectorDrawable.getIntrinsicHeight();
            int w = vectorDrawable.getIntrinsicWidth();
            vectorDrawable.setBounds(0, 0, w, h);
            Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bm);
            vectorDrawable.draw(canvas);
            return BitmapDescriptorFactory.fromBitmap(bm);
        } else {
            return BitmapDescriptorFactory.fromResource(id);
        }
    }

}
