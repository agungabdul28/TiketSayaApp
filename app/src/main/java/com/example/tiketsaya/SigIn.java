package com.example.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigIn extends AppCompatActivity {

    TextView btn_new_account ;
    Button btn_sig_in;
    EditText xusername , xpassword ;

    DatabaseReference reference ;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig_in);

        btn_new_account = findViewById(R.id.btn_new_account);
        btn_sig_in = findViewById(R.id.btn_sig_in);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);

        btn_sig_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ubah state menjadi loading
                btn_sig_in.setEnabled(false);
                btn_sig_in.setText("Loading ...");

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if (username.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Username kosong!",Toast.LENGTH_LONG).show();
                    // ubah state menjadi loading
                    btn_sig_in.setEnabled(true);
                    btn_sig_in.setText("SIGN IN");
                }else {
                    if (password.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password kosong!",Toast.LENGTH_LONG).show();
                        // ubah state menjadi loading
                        btn_sig_in.setEnabled(true);
                        btn_sig_in.setText("SIGN IN");
                    }
                    else {
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    //ambil data
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    //validasi
                                    if (password.equals(passwordFromFirebase)){


                                        //save usename_key to local
                                        // menyimpan data pada local storag
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY , MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xusername.getText().toString());
                                        editor.apply();

                                        // moveact
                                        Intent gotohome = new Intent(SigIn.this,HomeAct.class);
                                        startActivity(gotohome);


                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password tidak ada",Toast.LENGTH_LONG).show();
                                        // ubah state menjadi loading
                                        btn_sig_in.setEnabled(true);
                                        btn_sig_in.setText("SIGN IN");
                                    }


                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Username tidak ada",Toast.LENGTH_LONG).show();
                                    // ubah state menjadi loading
                                    btn_sig_in.setEnabled(true);
                                    btn_sig_in.setText("SIGN IN");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Database erorr ",Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }


            }
        });

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregister = new Intent(SigIn.this,RegisterOne.class);
                startActivity(gotoregister);

            }
        });
    }
}
