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

public class SuccesRegister extends AppCompatActivity {


    Animation app_main , ttb , btt ;
    Button btn_register;
    ImageView pic_succes_regis;
    TextView tv_selfie , tv_statemen ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_register);



        app_main = AnimationUtils.loadAnimation(this, R.anim.app_main);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        btn_register = findViewById(R.id.btn_register);
        pic_succes_regis =findViewById(R.id.pic_succes_regis);
        tv_selfie = findViewById(R.id.tv_selfie);
        tv_statemen =findViewById(R.id.tv_statemen);


        pic_succes_regis.startAnimation(app_main);
        btn_register.startAnimation(btt);
        tv_selfie.startAnimation(ttb);
        tv_statemen.startAnimation(ttb);







        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(SuccesRegister.this,HomeAct.class);
                startActivity(gotohome);
            }
        });

    }
}
