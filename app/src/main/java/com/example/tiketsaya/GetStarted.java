package com.example.tiketsaya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStarted extends AppCompatActivity {

    Button btn_sig_in , btn_create_acount ;
    Animation ttb , btt;
    ImageView emblem_logo;
    TextView text_opening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);


        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        emblem_logo = findViewById(R.id.emblem_logo);
        text_opening = findViewById(R.id.text_opening);

        btn_sig_in = findViewById(R.id.btn_sig_in);
        btn_create_acount = findViewById(R.id.btn_create_acount);


        emblem_logo.startAnimation(ttb);
        text_opening.startAnimation(ttb);
        btn_sig_in.startAnimation(btt);
        btn_create_acount.startAnimation(btt);

        btn_sig_in = findViewById(R.id.btn_sig_in);
        btn_create_acount = findViewById(R.id.btn_create_acount);

        btn_sig_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosign = new Intent(GetStarted.this,SigIn.class);
                startActivity (gotosign);
            }
        });

        btn_create_acount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregister = new Intent(GetStarted.this,RegisterOne.class);
                startActivity(gotoregister);
            }
        });

    }
}
