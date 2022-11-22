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

import com.cookandroid.flowerdesign.R;
import com.cookandroid.flowerdesign.model.User;
import com.cookandroid.flowerdesign.utils.Apis;
import com.cookandroid.flowerdesign.utils.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "Signup";
    Service service;
    EditText id,email,password;
    Button signupBtn;
    TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id=findViewById(R.id.id);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signupBtn=findViewById(R.id.signupbtn);
        loginText=findViewById(R.id.logintext);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=new User();
                user.setUserid(id.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());
                if(user.getUserid().trim().length()==0 || user.getUserid().equals("")
                    || user.getEmail().trim().length()==0 || user.getEmail().equals("")
                    || user.getPassword().trim().length()==0 || user.getPassword().equals("")){
                    Toast.makeText(SignupActivity.this, "다시 입력해주세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    signup(user);
                }
            }
        });
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

    public void signup(User user){
        service= Apis.getPersonaService();
        Call<User> call=service.signup(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    final User msg = response.body();
                    if(msg.getMsg()==0){
                        Toast.makeText(SignupActivity.this, "아이디가 이미 존재합니다.",
                                Toast.LENGTH_SHORT).show();
                    }else if(msg.getMsg()==1){
                        Toast.makeText(SignupActivity.this, "회원가입이 완료되었습니다.",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(SignupActivity.this, "서버에 오류가 있습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error:",t.getMessage());
            }
        });
    }
}