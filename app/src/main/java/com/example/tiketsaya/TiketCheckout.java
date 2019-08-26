package com.example.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class TiketCheckout extends AppCompatActivity {

    Button btn_buy_tiket , btn_mines , btn_plus;
    TextView textjumlah_tiket , texttotalharga , textmybalance , nama_wisata , ketentuan ,lokasi ;
    Integer valuejumlahtiket = 1;
    Integer mybalance = 200;
    Integer valuetotalharga = 20;
    Integer valuehargatiket = 0;
    LinearLayout btn_back;
    ImageView money_notice;

    Integer sisa_balance = 0 ;

    DatabaseReference reference , reference2 , reference3 , reference4 ;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String date_wisata ;
    String time_wisata ;

    //generet nomor integer secara random
    //karena akan membuat no trasaksi secara uniq
    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket_checkout);

        getUsernameLocal();

        // mengambil data dari Intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        btn_mines = findViewById(R.id.btn_mines);
        btn_plus = findViewById(R.id.btn_plus);
        textjumlah_tiket = findViewById(R.id.textjumlah_tiket);
        texttotalharga = findViewById(R.id.texttotalharga);
        textmybalance = findViewById(R.id.textmybalance);
        money_notice = findViewById(R.id.money_notice);

        btn_buy_tiket = findViewById(R.id.btn_buy_tiket);

        btn_back = findViewById(R.id.btn_back);

        // setting value baru untuk komponen
        textjumlah_tiket.setText(valuejumlahtiket.toString());

        // secara default hide button mine
        btn_mines.animate().alpha(0).setDuration(300).start();
        btn_mines.setEnabled(false);
        money_notice.setVisibility(View.GONE);

        // mengambil data users dri firebase
        reference2 =  FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textmybalance.setText("US$ " +mybalance+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //memgambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("WISATA").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // menimpa data yang ada dgn yg baru
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());

                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();

                valuehargatiket = Integer.valueOf(dataSnapshot.child("Harga_tiket").getValue().toString());

                valuetotalharga = valuehargatiket * valuejumlahtiket;
                texttotalharga.setText("US$ " + valuetotalharga+"");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuejumlahtiket+=1;
                textjumlah_tiket.setText(valuejumlahtiket.toString());
                if (valuejumlahtiket > 1){
                    btn_mines.animate().alpha(1).setDuration(300).start();
                    btn_mines.setEnabled(true);
                }
                valuetotalharga = valuehargatiket * valuejumlahtiket;
                texttotalharga.setText("US$ " + valuetotalharga+"");
                if (valuetotalharga > mybalance){
                    btn_buy_tiket.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_buy_tiket.setEnabled(false);
                    textmybalance.setTextColor(Color.parseColor("#D1206B"));
                    money_notice.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_mines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuejumlahtiket-=1;
                textjumlah_tiket.setText(valuejumlahtiket.toString());
                if (valuejumlahtiket < 2){
                    btn_mines.animate().alpha(0).setDuration(300).start();
                    btn_mines.setEnabled(false);
                }
                valuetotalharga = valuehargatiket * valuejumlahtiket;
                texttotalharga.setText("US$ " + valuetotalharga+"");
                if (valuetotalharga < mybalance){
                    btn_buy_tiket.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_buy_tiket.setEnabled(true);
                    textmybalance.setTextColor(Color.parseColor("#203DD1"));
                    money_notice.setVisibility(View.GONE);
                }
            }
        });



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(TiketCheckout.this,TiketDetailAct.class);
                startActivity(back);
            }
        });

        btn_buy_tiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // menyimpan data users ke firebase dan membuat tabke baru "Myticket"
                reference3 =  FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new).child(nama_wisata.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString()+ nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(valuejumlahtiket.toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);

                        Intent gotopaytiket = new Intent(TiketCheckout.this,SuccesTiket.class);
                        startActivity(gotopaytiket);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                // mengambil data users dri firebase
                reference4 =  FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = mybalance - valuetotalharga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }
    public  void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
