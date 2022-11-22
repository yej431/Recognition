package com.cookandroid.flowerdesign.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.flowerdesign.MainActivity;
import com.cookandroid.flowerdesign.R;
import com.cookandroid.flowerdesign.model.User;
import com.cookandroid.flowerdesign.utils.Apis;
import com.cookandroid.flowerdesign.utils.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Login";
    private Service service;
    private EditText id,password;
    private Button btnLogin;
    private TextView signupText;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id=findViewById(R.id.id);
        password=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btnLogin);
        signupText=findViewById(R.id.signupText);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=new User();
                user.setUserid(id.getText().toString());
                user.setPassword(password.getText().toString());
                if(user.getUserid().trim().length()==0 || user.getUserid().equals("")
                        || user.getPassword().trim().length()==0 || user.getPassword().equals("")){
                    Toast.makeText(LoginActivity.this, "다시 입력해주세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    login(user);
                }
            }
        });
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    public void login(User user){
        sessionManager=new SessionManager(getApplicationContext());
        service= Apis.getPersonaService();
        Call<User> call=service.login(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                final User msg = response.body();
                if(msg.getMsg()==2){
                    Toast.makeText(LoginActivity.this, "아이디가 존재하지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }else if(msg.getMsg()==1){
                    sessionManager.setLogin(true);
                    sessionManager.setUsername(msg.getUserid());
                    Log.d(TAG, "tag: "+sessionManager.getUsername());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else if(msg.getMsg()==0){
                    Toast.makeText(LoginActivity.this, "비밀번호가 일치하지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error:",t.getMessage());
            }
        });

        if(sessionManager.getLogin()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }
}