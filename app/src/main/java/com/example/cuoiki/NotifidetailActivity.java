package com.example.cuoiki;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class NotifidetailActivity extends AppCompatActivity {
    TextView txtTitle , txtNotiInfo  ;
    ImageView img , back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifi_detail);
        anhxa();
        setNotiInfo();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        txtTitle = findViewById(R.id.txtTitle);
        txtNotiInfo = findViewById(R.id.txtinfo);
        img = findViewById(R.id.imgnoti);
        back = findViewById(R.id.back);
    }

    private void setNotiInfo() {
        String name = getIntent().getStringExtra("name");
        String notification_info = getIntent().getStringExtra("info");
        String notifi_ing = getIntent().getStringExtra("img");
        String date_create = getIntent().getStringExtra("date");
        Log.i("TAG1", "setNotiInfo: " + name+ notification_info+notifi_ing+date_create);
        txtTitle.setText(name.toString());
        txtNotiInfo.setText(notification_info.toString());
        String imageUrl = notifi_ing ;

        Glide.with(this)
                .load(imageUrl)
                .into(img);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}