package org.techtown.memory_map;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RecordView extends Fragment {

    RecyclerView recyclerView;
    RecordAdapter adapter;

    Context context;

    private ServiceApi serviceApi;
    private RecordResponse dataList;
    private List<Record> data;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recordview, container, false);

        initUI(rootView);

        return rootView;
    }


    private void initUI(ViewGroup rootView) {
        recyclerView = rootView.findViewById(R.id.list_Recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //adapter = new RecordAdapter();
        data = new ArrayList<>();

        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        Call<RecordResponse> call = serviceApi.getData();

        call.enqueue(new Callback<RecordResponse>() {
            @Override
            public void onResponse(Call<RecordResponse> call, Response<RecordResponse> response) {
                dataList = response.body();

                //Toast.makeText(RecordView.this, dataList.getMessage(), Toast.LENGTH_SHORT).show();
                if (dataList.getStatus() == 200) {
                    data = dataList.getBody();

                    adapter = new RecordAdapter(getContext(), data);

                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<RecordResponse> call, Throwable t) {
                Log.e("기록 불러오기 실패", t.getMessage());
                t.printStackTrace();
            }
        });

        /*
        adapter.addItem(new Record(0, "Los Angeles, US", "", "", "미국", "capture1.jpg", "2월 1일"));

        adapter.addItem(new Record(1, "Seoul, South Korea", "", "", "한국", "capture1.jpg", "2월 3일"));

        adapter.addItem(new Record(2, "Tokyo, Japan", "", "", "일본", "capture1.jpg", "2월 5일"));

        recyclerView.setAdapter(adapter);
         */


        adapter.setOnItemClickListener(new OnRecordItemClickListener() {
            @Override
            public void onItemClick(RecordAdapter.ViewHolder holder, View view, int position) {
                Record item = adapter.getItem(position);
                Toast.makeText(getContext(), "아이템 선택됨: " + item.getText(), Toast.LENGTH_LONG).show();
            }
        });
    }

}