package org.techtown.memory_map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class SettingView extends Fragment {
    Spinner spinner;
    TextView choice;
    TextView sign_out;
    TextView delete_account;
    ServiceApi serviceApi;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settingview, container, false);
        spinner = root.findViewById(R.id.spinner_language);
        choice = root.findViewById(R.id.spinner_choice);
        sign_out = root.findViewById(R.id.sign_out_btn);
        delete_account = root.findViewById(R.id.delete_account_text);

        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        int userIdx = sharedPreferences.getInt("userIdx", 0);

        final String[] fields = {"한국어", "English"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, fields);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice.setText(fields[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(sign_out.getContext(), "로그아웃되었습니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(intent);

            }
        });

        delete_account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(delete_account.getContext(),R.style.MinLengthDialogTheme);
                builder.setMessage("정말로 삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startDeleteAcc(userIdx);
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        return root;
    }

    private void startDeleteAcc(int userIdx){
        serviceApi.deleteAccount(new DelAccData(userIdx)).enqueue(new Callback<AccDeleteResponse>() {
            @Override
            public void onResponse(Call<AccDeleteResponse> call, Response<AccDeleteResponse> response) {
                AccDeleteResponse delResponse = response.body();

                if (delResponse.getStatus() == 200) {
                    Toast.makeText(getContext(), "계정이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), delResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccDeleteResponse> call, Throwable t) {
                Log.e("계정 삭제 오류", t.getMessage());
                Toast.makeText(getContext(), "계정 삭제 오류", Toast.LENGTH_SHORT).show();
            }
        });

    }
}