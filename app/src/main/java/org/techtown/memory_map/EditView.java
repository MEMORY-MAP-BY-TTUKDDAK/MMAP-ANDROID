package org.techtown.memory_map;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaActionSound;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class EditView extends Fragment {

    private ImageView edit_image;
    private Context context;
    private static final int GALLERY_REQUEST_CODE = 301;
    private static final int REQUEST_EXTERNAL_STORAGE_PERMISSION = 401;


    Button Date_edit;
    Date Selected_date;
    EditText address_input;
    TextView address_result;
    Button save_button;

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_editview, container, false);

        Date_edit = rootView.findViewById(R.id.date_edit);
        Date_edit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        Date curDate = new Date();
        setSelectedDate(curDate);

        context = container.getContext();

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String [] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_PERMISSION);
            }
        } else {

        }

        edit_image = rootView.findViewById(R.id.edit_Image);

        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });

        address_input = rootView.findViewById(R.id.location_input);
        save_button = rootView.findViewById(R.id.edit_saveButton);
        address_result = rootView.findViewById(R.id.location_result);
        final Geocoder geocoder = new Geocoder(this.getContext());
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Address> list = null;

                String str = address_input.getText().toString();
                try {
                    list = geocoder.getFromLocationName
                            (str,
                                    10);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
                }

                if (list != null) {
                    if (list.size() == 0) {
                        address_result.setText("올바른 주소를 입력해주세요. ");
                    } else {
                        Address address = list.get(0);
                        double lat = address.getLatitude();
                        double lon = address.getLongitude();
                        String sss = String.format("위도 : %f, 경도 : %f", lat, lon);
                        address_result.setText(sss);
                    }
                }
            }
        });


        return rootView;
    }

    private void showDateDialog() {
        String birthDateStr = Date_edit.getText().toString();

        Calendar calendar = Calendar.getInstance();
        Date curBirthDate = new Date();
        try {
            curBirthDate = dateFormat.parse(birthDateStr);
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        calendar.setTime(curBirthDate);

        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.DialogTheme, birthDateSetListener,  curYear, curMonth, curDay);
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener birthDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(Calendar.YEAR, year);
            selectedCalendar.set(Calendar.MONTH, monthOfYear);
            selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            Date curDate = selectedCalendar.getTime();
            setSelectedDate(curDate);
        }
    };

    private void setSelectedDate(Date curDate) {
        Selected_date = curDate;

        String selectedDateStr = dateFormat.format(curDate);
        Date_edit.setText(selectedDateStr);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_REQUEST_CODE) {
            Uri photoUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
                edit_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}