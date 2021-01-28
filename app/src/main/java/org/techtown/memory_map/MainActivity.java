package org.techtown.memory_map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    public static final int REQUEST_CODE_CREATE_ACCOUNT = 101;
    public static final int REQUEST_CODE_MENU = 201;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.no_account_yet);
        idInput=findViewById(R.id.input_id_Main);
        pwInput=findViewById(R.id.input_pw_Main);
        sign_in_button = findViewById(R.id.signInBtn_Main);
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
                startActivityForResult(intent, REQUEST_CODE_CREATE_ACCOUNT);
            }
        });

        sign_in_button.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if((idInput.length() == 0) || (pwInput.length() == 0)){
                    Toast.makeText(getApplicationContext(), "must enter all information",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(), ChooseMenu.class);
                    startActivityForResult(intent, REQUEST_CODE_MENU);
                }
            }
        }));
    }

}
