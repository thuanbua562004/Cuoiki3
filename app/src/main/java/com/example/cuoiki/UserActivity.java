package com.example.cuoiki;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;

public class UserActivity extends AppCompatActivity {
    public TextView txtHoTen ,txtMssv,txtNamsinh, txtQuequan,txtNganhhoc,txtEmail;
    ArrayList<SinhVien> arrayList =new ArrayList<>();
    String url ="http://192.168.1.41/QLSV/user.php";
    public  String mssv ;
    ImageButton news,user,home ,menu;
    ImageView imgUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitietsinhvien);
        anhxa();
        mssv = getMssv();
        Log.i("TAG1", "onCreate: "+mssv);
       if(!mssv.isEmpty()){
           getThongTinSv(url , mssv);
       }
        menubar();
    }
    public void getThongTinSv(String url, String mssv) {
        arrayList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.i("TAG1", "onResponse: "+ jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        SinhVien sv = new SinhVien(
                                obj.getString("username"),
                                obj.getString("password"),
                                obj.getString("mssv"),
                                obj.getString("quequan"),
                                obj.getString("ngaysinh"),
                                obj.getString("nganhhoc"),
                                obj.getString("hoten"),
                                obj.getString("email"),
                                obj.getString("img")
                        );
                        arrayList.add(sv);
                    }
                    SinhVien sv = arrayList.get(0);
                    Log.i("TAG1", "onResponse: "+ arrayList.toString());
                    SharedPreferences sharedPref = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("key", mssv);
                    editor.putString("quequan", sv.getQuequan().toString());
                    editor.putString("ngaysinh",sv.getNgaysinh().toString());
                    editor.putString("email", sv.getEmail().toString());
                    editor.putString("hoten", sv.getHoten().toString());
                    editor.putString("nganhhoc",sv.getNganhhoc().toString());
                    editor.putString("img",sv.getImg().toString());
                    editor.apply();
                    //////////set thong tin len layout
                    setThongTin(sv);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG1", error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mssv", mssv);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private String getMssv() {
        SharedPreferences sharedPref = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String mssv = sharedPref.getString("key", "");
        return mssv ;
    }

    private void setThongTin(SinhVien sv) {
        txtMssv.setText( "MSSV: "+sv.getMssv());
        txtHoTen.setText("Ho Ten: "+sv.getHoten());
        txtNamsinh.setText("Ngay Sinh: "+sv.getNgaysinh());
        txtQuequan.setText("Que Quan: "+sv.getQuequan());
        txtNganhhoc.setText("Nganh Hoc: "+sv.getNganhhoc());
        txtEmail.setText("Email: "+ sv.getEmail());
        String imageUrl = "http://192.168.1.41/QLSV/" + sv.getImg().toString() ;
        if(sv.getImg().toString().equals("null")){

        }else {
            Glide.with(this)
                    .load(imageUrl)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(imgUser);
        }
    }
    public void menubar(){
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this , NotificationActivity.class));
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this , MenuUser.class));
            }
        });

    }
    public void anhxa() {
        user = findViewById(R.id.user);
        home = findViewById(R.id.home);
        news  =findViewById(R.id.news);
        menu = findViewById(R.id.menu);
        txtEmail = findViewById(R.id.txtemail);
        txtMssv = findViewById(R.id.txtmssv);
        txtHoTen = findViewById(R.id.txthoten);
        txtNamsinh = findViewById(R.id.txtnamsinh);
        txtNganhhoc = findViewById(R.id.txtnganhhoc);
        txtQuequan = findViewById(R.id.txtquequan);
        txtEmail = findViewById(R.id.txtemail);
        imgUser = findViewById(R.id.imgacount);
    }
}