/*package org.techtown.memory_map;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
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


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import org.techtown.memory_map.RecordView;
import org.techtown.memory_map.RoundImageView;
import org.techtown.memory_map.ServiceApi;

public class RecordModify extends Fragment {

    private RoundImageView edit_image;
    private Context context;
    private static final int GALLERY_REQUEST_CODE = 301;
    private static final int REQUEST_EXTERNAL_STORAGE_PERMISSION = 401;

    private ServiceApi serviceApi;

    RecordView recordView = new RecordView();

    Button Date_edit;
    Date Selected_date;
    EditText address_input;
    TextView address_result;
    Button save_button;
    EditText text_input;
    String token;
    String birthDateStr;
    Bitmap bitmap;
    int userIdx;
    int resetDate;
    Uri photoUri;
    File file;

    RequestBody fileBody;

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_editview, container, false);

        Date_edit = rootView.findViewById(R.id.date_edit);
        Date_edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        Date curDate = new Date();
        setSelectedDate(curDate);
        Calendar cal = Calendar.getInstance();
        //resetDate : 날짜를 수정하지 않았을 경우 EditView에서 자동으로 현재날짜 보낼목적으로 ..
        //여기서는 날짜 변경 X시 원래 날짜 그대로 전송하게끔 후에 수정할것..
        resetDate = cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH) + 1) * 100 + cal.get(Calendar.DAY_OF_MONTH);

        context = container.getContext();
        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
        SharedPreferences sharedPreferences = context.getSharedPreferences("login", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        userIdx = sharedPreferences.getInt("userIdx", -1);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_PERMISSION);
            }
        } else {

        }

        edit_image = rootView.findViewById(R.id.edit_Image);
        edit_image.setRadius(25f);

        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });

        text_input = rootView.findViewById(R.id.text_input);
        address_input = rootView.findViewById(R.id.location_input);
        save_button = rootView.findViewById(R.id.edit_saveButton);
        address_result = rootView.findViewById(R.id.location_result);
        final Geocoder geocoder = new Geocoder(this.getContext());

        //텍스트 창이랑 이미지 창 서버로부터 받아온 정보를 기본으로 넣어둘것
        //text_input.setText(response.toString());
        //edit_image.setImage(response.getImage());
        //address_input.setHint(response.getDetailAddress);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Address> list = null;
                List<Address> citylist = null;

                String str = address_input.getText().toString();
                String text = text_input.getText().toString();

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
                        String city = "";
                        String country = "";
                        String detailAddress = "";
                        if (list.size() == 0) {
                            Toast.makeText(address_input.getContext(), "올바른 주소를 입력해주세요", Toast.LENGTH_SHORT).show();
                        } else {
                            Address address = list.get(0);
                            double lat = address.getLatitude();
                            double lon = address.getLongitude();

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
                                    StartEdit(new EditData(getStringFromBitmap(bitmap), city, country, text, lat, lon, userIdx, resetDate, detailAddress));
                                    //address_result.setText(city + " " + country);
                                }
                            }
                            // RequestBody image = RequestBody.create(MediaType.parse("image/*"), bitmap);
                            //fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                            //MultipartBody.Part.createFormData();
                        }
                    }
                }
            }
        });

        return rootView;
    }

    private void StartEdit(EditData editData) {
        serviceApi.userEdit(token, editData).enqueue(new Callback<EditResponse>() {
            @Override
            public void onResponse(Call<EditResponse> call, Response<EditResponse> response) {
                EditResponse result = response.body();
                String temp = response.toString();
                System.out.println(temp);
                if (result.getStatus() == 200) {
                    Toast.makeText(save_button.getContext(), "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    //address_input.setText(null);
                    //text_input.setText(null);
                    //edit_image.setImageResource(R.drawable.add_pic_button);

                    //아래 코드는 getFragmentManager을 통해 recordView 불러오는거!
                    getFragmentManager().beginTransaction().replace(R.id.container, recordView).commit();
                }
                System.out.println(temp);
            }

            @Override
            public void onFailure(Call<EditResponse> call, Throwable t) {
                Toast.makeText(save_button.getContext(), "통신에러", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDateDialog() {
        //여기서 서버로부터 받아온 데이터 Date형태로 바꿔줄것
        birthDateStr = Date_edit.getText().toString();
        //birthDateStr을 서버로부터 받아온 int를 String으로 할것
        //birthDateStr = response.getDate().toString();

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
        DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.DialogTheme, birthDateSetListener, curYear, curMonth, curDay);
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
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
                //edit_image.setBackground(null);
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

    private String getStringFromBitmap(Bitmap bitmaps) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmaps.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        String img = Base64.encodeToString(b, Base64.DEFAULT);
        return img;
    }
}
*/