package org.techtown.memory_map;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Record_check extends AppCompatActivity {

    String contents;

    RoundImageView imageView;
    TextView check_location, check_date, check_content, check_detailLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_check);


        Intent intent = getIntent();

        imageView = findViewById(R.id.check_image);

        check_location = findViewById(R.id.check_location);
        check_date = findViewById(R.id.check_date);
        check_content = findViewById(R.id.check_content);
        check_detailLocation = findViewById(R.id.check_locationDetail);

        //imageView.setImageBitmap(intent.getData("image"));
        check_location.setText(intent.getStringExtra("location"));
        check_date.setText(intent.getStringExtra("date"));
        check_content.setText(intent.getStringExtra("content"));
        check_detailLocation.setText(intent.getStringExtra("location_detail"));

    }
}
