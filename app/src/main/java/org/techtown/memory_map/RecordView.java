package org.techtown.memory_map;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class RecordView extends Fragment {

    RecyclerView recyclerView;
    RecordAdapter adapter;

    Context context;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recordview, container, false);

        initUI(rootView);

        return rootView;
    }


    private void initUI(ViewGroup rootView) {
        recyclerView = rootView.findViewById(R.id.list_Recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecordAdapter();

        adapter.addItem(new Record(0, "Los Angeles, US", "", "", "미국", "capture1.jpg", "2월 1일"));

        adapter.addItem(new Record(1, "Seoul, South Korea", "", "", "한국", "capture1.jpg", "2월 3일"));

        adapter.addItem(new Record(2, "Tokyo, Japan", "", "", "일본", "capture1.jpg", "2월 5일"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnRecordItemClickListener() {
            @Override
            public void onItemClick(RecordAdapter.ViewHolder holder, View view, int position) {
                Record item = adapter.getItem(position);
                Toast.makeText(getContext(), "아이템 선택됨: " + item.getContents(), Toast.LENGTH_LONG).show();
            }
        });
    }
}