package org.techtown.memory_map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class RecordView extends Fragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipe;
    RecordAdapter adapter;
    TextView user_record;

    private ServiceApi serviceApi;
    private RecordResponse dataList;
    private List<Record> data;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recordview, container, false);
        swipe = rootView.findViewById(R.id.swipe);
        initUI(rootView);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                initUI(rootView);
                swipe.setRefreshing(false);
            }
        });
        return rootView;
    }


    private void initUI(ViewGroup rootView) {
        recyclerView = rootView.findViewById(R.id.list_Recycler);
        user_record = rootView.findViewById(R.id.user_record);
        Fragment fragment = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        data = new ArrayList<>();

        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", MODE_PRIVATE);
        int userIdx = sharedPreferences.getInt("userIdx", 0);

        Call<NameResponse> nameResponseCall = serviceApi.getName(userIdx);

        nameResponseCall.enqueue(new Callback<NameResponse>() {
            @Override
            public void onResponse(Call<NameResponse> call, Response<NameResponse> response) {
                NameResponse nameList = response.body();
                if (nameList.getStatus() == 200) {
                    String name = nameList.getData().get(0).getName();
                    user_record.setText(name);
                }
                else {
                    Toast.makeText(getContext(), nameList.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NameResponse> call, Throwable t) {
                Log.e("이름 불러오기 실패", t.getMessage());
                t.printStackTrace();
            }
        });

        Call<RecordResponse> call = serviceApi.getData(userIdx);

        call.enqueue(new Callback<RecordResponse>() {
            @Override
            public void onResponse(Call<RecordResponse> call, Response<RecordResponse> response) {
                dataList = response.body();
                if (dataList.getStatus() == 200) {
                    data = dataList.getBody();
                    adapter = new RecordAdapter(getContext(), data);
                    System.out.println(adapter.getItemCount() + "");

                    recyclerView.setAdapter(adapter);

                    adapter.setOnItemClickListener(new OnRecordItemClickListener() {
                        @Override
                        public void onItemClick(RecordAdapter.ViewHolder holder, View view, int position) {
                            Record item = adapter.getItem(position);
                            final Context context = getContext();

                            Bundle bundle = new Bundle();
                            bundle.putInt("id", position);

                            Intent mainIntent = new Intent(context, Record_check.class);
                            mainIntent.putExtras(bundle);
                            mainIntent.putExtra("date", Integer.toString(item.getDate()));
                            mainIntent.putExtra("content", item.getText());
                            mainIntent.putExtra("location", item.getCity() + " " + item.getCountry());
                            mainIntent.putExtra("location_detail", item.getCountry() + ", " + item.getCity());
                            mainIntent.putExtra("image", item.getImg());

                            context.startActivity(mainIntent);
                        }
                    });

                    adapter.setOnRecordModifyClickListener(new OnRecordModifyListener() {
                        @Override
                        public void onItemModifyClick(RecordAdapter.ViewHolder holder, View view, int position) {
                            Context context = getContext();
                            Record record = adapter.getItem(position);
                            int marker_idx = adapter.getItem(position).getMarkerIdx();
                            Intent intent = new Intent(context, RecordModify.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("date", Integer.toString(record.getDate()));
                            intent.putExtra("content", record.getText());
                            intent.putExtra("location", record.getCity() + " " + record.getCountry());
                            intent.putExtra("location_detail", record.getLocation());
                            intent.putExtra("image", record.getImg());
                            intent.putExtra("markerIdx", marker_idx);
                            intent.putExtra("position", position);
                            context.startActivity(intent);
                        }
                    });

                }
                else if (dataList.getStatus() == 400) {
                    Toast.makeText(getContext(), dataList.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecordResponse> call, Throwable t) {
                Log.e("기록 불러오기 실패", t.getMessage());
                t.printStackTrace();
            }
        });

    }

}