package org.techtown.memory_map;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText idInput;
    EditText pwInput;
    Button sign_in_button;

    private ServiceApi service;

    public static final int REQUEST_CODE_CREATE_ACCOUNT = 101;
    public static final int REQUEST_CODE_MENU = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.no_account_yet);
        idInput = findViewById(R.id.input_id_Main);
        pwInput = findViewById(R.id.input_pw_Main);
        sign_in_button = findViewById(R.id.signInBtn_Main);
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
                startActivityForResult(intent, REQUEST_CODE_CREATE_ACCOUNT);
            }
        });

        service = RetrofitClient.getClient().create(ServiceApi.class);

        sign_in_button.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    sign_in_button.setBackgroundResource(R.drawable.button_login);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    sign_in_button.setBackgroundResource(R.drawable.before_button_click);
                }
                return false;
            }
        });

        sign_in_button.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if((idInput.length() == 0) || (pwInput.length() == 0)){
                    Toast.makeText(getApplicationContext(), "must enter all information",Toast.LENGTH_SHORT).show();
                } else {
                    startLogin(new LoginData(idInput.getText().toString(), pwInput.getText().toString()));

                }
            }
        }));
    }

    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                System.out.println(response.toString());
                try {
                    if (result.getStatus() == 200) {
                        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", result.data.getAccessToken());
                        editor.putInt("userIdx", result.data.getUserIdx());
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), ChooseMenu.class);
                        startActivityForResult(intent, REQUEST_CODE_MENU);
                        Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else if (result.getStatus() == 400) {
                        Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러", t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
