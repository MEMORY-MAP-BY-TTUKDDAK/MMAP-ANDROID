package org.techtown.memory_map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount extends AppCompatActivity {
    Button button;
    EditText id_input;
    EditText pw_input;
    EditText username_input;
    EditText email_input;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        button=findViewById(R.id.createButton);
        id_input = findViewById(R.id.input_id);
        pw_input = findViewById(R.id.input_pw);
        username_input = findViewById(R.id.input_username);
        email_input = findViewById(R.id.input_email);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((id_input.length() == 0) ||(pw_input.length() == 0) || (username_input.length() == 0) || (email_input.length() == 0)){
                    Toast.makeText(getApplicationContext(), "please enter all information",Toast.LENGTH_SHORT).show();
                } else {
                    String id = id_input.getText().toString();
                    String pw = pw_input.getText().toString();
                    String username = username_input.getText().toString();
                    String email = email_input.getText().toString();
                    Intent resultIntent = new Intent();
                    finish();
                }
            }
        });
    }
}
