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

public class SuccesTiket extends AppCompatActivity {

    Animation app_main , ttb , btt ;
    Button btn_my_dashboard ,btn_view_tiket ;
    ImageView success_tiketbuy ;
    TextView textselamat , textberhasil ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_tiket);

        app_main = AnimationUtils.loadAnimation(this, R.anim.app_main);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);


        btn_my_dashboard = findViewById(R.id.btn_my_dashboard);
        btn_view_tiket = findViewById(R.id.btn_view_tiket);
        success_tiketbuy = findViewById(R.id.success_tiketbuy);
        textselamat = findViewById(R.id.textselamat);
        textberhasil = findViewById(R.id.textberhasil);

        // select

        btn_my_dashboard.startAnimation(btt);
        btn_view_tiket.startAnimation(btt);
        success_tiketbuy.startAnimation(app_main);
        textselamat.startAnimation(ttb);
        textberhasil.startAnimation(ttb);

        btn_view_tiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(SuccesTiket.this,MyProfile.class);
                startActivity(gotoprofile);
            }
        });

        btn_my_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtodashboard = new Intent(SuccesTiket.this,HomeAct.class);
                startActivity(backtodashboard);
            }
        });

    }
}
