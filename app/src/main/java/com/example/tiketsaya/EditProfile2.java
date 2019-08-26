package com.example.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EditProfile2 extends AppCompatActivity {
    ImageView photo_edit_profile ;

    LinearLayout btn_back ;

    Button btn_save ;

    EditText xnama_lengkap , xbio , xusername , xpassword , xemail_address ;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getUsernameLocal();

        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);

        photo_edit_profile = findViewById(R.id.photo_edit_profile);

        xnama_lengkap = findViewById(R.id.xnama_lengkap);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xnama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                xbio.setText(dataSnapshot.child("bio").getValue().toString());
                xusername.setText(dataSnapshot.child("username").getValue().toString());
                xpassword.setText(dataSnapshot.child("password").getValue().toString());
                xemail_address.setText(dataSnapshot.child("email").getValue().toString());
                Picasso.with(EditProfile2.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_edit_profile);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("username").setValue(xusername.getText().toString());
                        dataSnapshot.getRef().child("nama_lengkap").setValue(xnama_lengkap.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(xpassword.getText().toString());
                        dataSnapshot.getRef().child("bio").setValue(xbio.getText().toString());
                        dataSnapshot.getRef().child("email").setValue(xemail_address.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                /// move act
                Intent gotoprofile = new Intent(EditProfile2.this,MyProfile.class);
                startActivity(gotoprofile);
            }
        });



    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
