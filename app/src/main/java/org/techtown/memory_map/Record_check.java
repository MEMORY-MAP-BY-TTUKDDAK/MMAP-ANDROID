package org.techtown.memory_map;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

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

        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy.MM.dd");
        String intentDate = intent.getStringExtra("date");
        String newDate = "";
        try {
            Date date = dtFormat.parse(intentDate);
            newDate = newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(imageView.getContext()).load(intent.getStringExtra("image")).into(imageView);
        check_location.setText(intent.getStringExtra("location"));
        check_date.setText(newDate);
        check_content.setText(intent.getStringExtra("content"));
        check_detailLocation.setText(intent.getStringExtra("location_detail"));

    }
}
