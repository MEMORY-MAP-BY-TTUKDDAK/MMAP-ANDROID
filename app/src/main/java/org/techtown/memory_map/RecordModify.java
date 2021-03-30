package org.techtown.memory_map;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaActionSound;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;

import com.bumptech.glide.Glide;

import org.techtown.memory_map.RecordView;
import org.techtown.memory_map.RoundImageView;
import org.techtown.memory_map.ServiceApi;

public class RecordModify extends AppCompatActivity {

    private RoundImageView edit_image;
    private Context context;
    private static final int GALLERY_REQUEST_CODE = 301;
    private static final int REQUEST_EXTERNAL_STORAGE_PERMISSION = 401;
    private ServiceApi serviceApi;

    Button Date_edit;
    Date Selected_date;
    EditText address_input;
    TextView address_result;
    Button save_button;
    EditText text_input;

    String token;
    String birthDateStr;
    String filepath;

    String original_locate; //원래 위치
    String original_text; //원래 텍스트
    Bitmap original_bitmap; //원래 이미지

    Bitmap bitmap;
    int markerIdx;
    int resetDate;
    Uri photoUri;
    HashMap map;

    String city = "";
    String country = "";
    String detailAddress = "";
    String text = "";
    double lat;
    double lon;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_modify);
        Intent intent = getIntent();
        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        token = sharedPreferences.getString("token","");

        //달력 버튼 누를 시 달력 띄워줌
        Date_edit = findViewById(R.id.date_edit);
        Date_edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        String temp = intent.getStringExtra("date");
        resetDate = Integer.parseInt(temp); //원래 날짜 보존을 위한 변수 resetDate
        markerIdx = intent.getIntExtra("markerIdx", 0);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_PERMISSION);
            }
        } else {

        }

        edit_image = findViewById(R.id.edit_Image);
        edit_image.setRadius(25f);

        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });

        text_input = findViewById(R.id.text_input);
        address_input = findViewById(R.id.location_input);
        save_button = findViewById(R.id.edit_saveButton);
        address_result = findViewById(R.id.location_result);
        map = new HashMap<String, RequestBody>();
        final Geocoder geocoder = new Geocoder(this);

        //기존 레코드 정보를 받아온다.
        DateFormat dateStringFormat = new SimpleDateFormat("yyyyMMdd");
        Date responseDate = new Date();
        try {
            responseDate = dateStringFormat.parse(temp);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        Date_edit.setText(dateFormat.format(responseDate)); //날짜

        Glide.with(edit_image.getContext()).load(intent.getStringExtra("image")).into(edit_image); //이미지
        BitmapDrawable drawable = (BitmapDrawable) edit_image.getDrawable();
        original_bitmap = drawable.getBitmap();

        // 텍스트내용
        original_text = intent.getStringExtra("content");
        text_input.setText(original_text);

        //주소 받아오기
        original_locate = intent.getStringExtra("location_detail");
        address_input.setText(original_locate);

        save_button.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    save_button.setBackgroundResource(R.drawable.button_login);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    save_button.setBackgroundResource(R.drawable.before_button_click);
                }
                return false;
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Address> list = null;
                List<Address> citylist = null;

                String str = address_input.getText().toString();
                if((str == null) || (str.length() == 0)){
                    str = original_locate;
                } //아무것도 변동이 안되었을 경우 원래 값 그대로 지오코딩


                text = text_input.getText().toString();
                if((text == null) || (text.length() == 0)) {
                    text = original_text;
                } //변동 x시 원래 텍스트 그대로 넣기

                if(bitmap == null) {
                    bitmap = original_bitmap;
                    photoUri = getImageUri(edit_image.getContext(), bitmap);
                } //사진 수정 X시 원래 이미지 그대로 넣기

                try {
                    list = geocoder.getFromLocationName
                            (str,
                                    10);
                } catch (IOException e) {
                    e.printStackTrace();
                } // 여기까지 지오코더의 에러 잡는 try-catch

                if (str.length() == 0 || str == null || text.length() == 0 || text == null || bitmap == null) {
                    Toast.makeText(save_button.getContext(), "모든 정보를 기재해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    if (list != null) {
                        //String city = "";
                        //String country = "";
                        //String detailAddress = "";
                        if (list.size() == 0) {
                            Toast.makeText(address_input.getContext(), "올바른 주소를 입력해주세요", Toast.LENGTH_SHORT).show();
                        } else {
                            Address address = list.get(0);
                            lat = address.getLatitude();
                            lon = address.getLongitude();

                            try {
                                citylist = geocoder.getFromLocation(
                                        lat,
                                        lon,
                                        10
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (citylist != null) {
                                if (list.size() == 0) {
                                    Log.e("reverseGeocoding", "해당 도시 없음");
                                } else {
                                    city = citylist.get(0).getAdminArea();
                                    country = citylist.get(0).getCountryName();
                                    detailAddress = citylist.get(0).getAddressLine(0);
                                    RequestBody town = RequestBody.create(MediaType.parse("text/plain"),city);
                                    RequestBody nation = RequestBody.create(MediaType.parse("text/plain"),country);
                                    RequestBody texts = RequestBody.create(MediaType.parse("text/plain"),text);
                                    RequestBody lats = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(lat));
                                    RequestBody lons = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(lon));
                                    RequestBody date = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(resetDate));
                                    RequestBody locate = RequestBody.create(MediaType.parse("text/plain"), detailAddress);

                                    map.put("city", town);
                                    map.put("country", nation);
                                    map.put("text", texts);
                                    map.put("date", date);
                                    map.put("lattitude", lats);
                                    map.put("longtitude", lons);
                                    map.put("location", locate);
                                    StartModify(map);
                                }
                            }

                        }
                    }
                }
            }
        });
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private void StartModify(HashMap map) {
        File file = new File(filepath); // filepath = photoUri.getPath()
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(photoUri);
        }catch(IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray());
        MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("postImg", file.getName() ,requestBody);

        serviceApi.modifyRecord(token, markerIdx, uploadFile, map).enqueue(new Callback<ModifyResponse>() {
            @Override
            public void onResponse(Call<ModifyResponse> call, Response<ModifyResponse> response) {
                ModifyResponse result = response.body();
                String temp = response.toString();
                System.out.println(temp);
                if(result.getStatus() == 200){
                    Toast.makeText(save_button.getContext(),"수정이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    System.out.println(result.getStatus() + " " + result.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ModifyResponse> call, Throwable t) {
                Toast.makeText(save_button.getContext(), "통신에러",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDateDialog() {
        birthDateStr = Date_edit.getText().toString();
        Calendar calendar = Calendar.getInstance();
        Date curBirthDate = new Date();
        try {
            curBirthDate = dateFormat.parse(birthDateStr);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        calendar.setTime(curBirthDate);

        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, birthDateSetListener, curYear, curMonth, curDay);
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener birthDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(Calendar.YEAR, year);
            selectedCalendar.set(Calendar.MONTH, monthOfYear);
            selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Date curDate = selectedCalendar.getTime();
            resetDate = year * 10000 + (monthOfYear + 1) * 100 + dayOfMonth;
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
            photoUri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                edit_image.setImageBitmap(bitmap);
                Cursor cursor = getContentResolver().query(Uri.parse(photoUri.toString()), null, null, null, null);
                if(cursor == null) {
                    filepath = photoUri.getPath();
                } else {
                    cursor.moveToFirst();
                    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    filepath = cursor.getString(idx);
                    cursor.close();
                }

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