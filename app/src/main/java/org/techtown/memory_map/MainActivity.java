package org.techtown.memory_map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText idInput;
    EditText pwInput;
    public static final int REQUEST_CODE_CREATE_ACCOUNT = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.no_account_yet);
        idInput=findViewById(R.id.input_id_Main);
        pwInput=findViewById(R.id.input_pw_Main);

        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
                startActivityForResult(intent, REQUEST_CODE_CREATE_ACCOUNT);
            }
        });
    }

}
