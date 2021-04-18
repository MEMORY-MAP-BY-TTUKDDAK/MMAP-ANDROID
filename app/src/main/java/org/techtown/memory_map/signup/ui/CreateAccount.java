package org.techtown.memory_map;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.Service;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateAccount extends AppCompatActivity {
    Button button;
    EditText id_input;
    EditText pw_input;
    EditText username_input;
    EditText email_input;

    public static String id;
    public static String pw;
    public static String username;
    public static String email;
    private ServiceApi serviceApi;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        button=findViewById(R.id.createButton);
        id_input = findViewById(R.id.input_id);
        id_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {

                } else {
                    if(id_input.length() < 8) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this,R.style.MinLengthDialogTheme);
                        builder.setMessage("ID는 8자 이상이어야 합니다.");
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                }
            }
        });
        pw_input = findViewById(R.id.input_pw);
        pw_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {

                } else {
                    if(pw_input.length() < 8) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this,R.style.MinLengthDialogTheme);
                        builder.setMessage("비밀번호는 8자 이상이어야 합니다.");
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                }
            }
        });

        username_input = findViewById(R.id.input_username);
        email_input = findViewById(R.id.input_email);

        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        button.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    button.setBackgroundResource(R.drawable.button_login);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    button.setBackgroundResource(R.drawable.before_button_click);
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((id_input.length() == 0) ||(pw_input.length() == 0) || (username_input.length() == 0) || (email_input.length() == 0)){
                    Toast.makeText(getApplicationContext(), "필요한 정보를 모두 기입해주세요.",Toast.LENGTH_SHORT).show();
                } else if((id_input.length() < 8) || (pw_input.length() < 8)) {
                    Toast.makeText(getApplicationContext(), "ID 및 비밀번호는 8자이상이어야 합니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    id = id_input.getText().toString();
                    pw = pw_input.getText().toString();
                    username = username_input.getText().toString();
                    email = email_input.getText().toString();
                    startJoin(new JoinData(id,pw,username,email));
                    Intent resultIntent = new Intent();
                    //finish();
                }
            }
        });


    }

    private void startJoin(JoinData data) {
        serviceApi.userJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(CreateAccount.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if(result.getStatus() == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(CreateAccount.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
