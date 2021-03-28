package org.techtown.memory_map;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> implements OnRecordItemClickListener {
    //ArrayList <Record> items = new ArrayList<Record>();
    public Context c;
    private List<Record> items;

    ServiceApi serviceApi;

    OnRecordItemClickListener listener;
    OnRecordModifyListener modifyListener; //수정을 위한 listener
    @Override
    public boolean onFailedToRecycleView(@NonNull ViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    public RecordAdapter(Context c, List<Record> items) {
        this.c = c;
        this.items = items;
    }


    @NonNull
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.record_xml, parent, false);

        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        return new ViewHolder(itemView, listener, modifyListener);
        //return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.ViewHolder holder, int position) {
        Record item = items.get(position);
        holder.setItem(item);

        holder.record_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), item.getMarkerIdx() + "번 마커", Toast.LENGTH_LONG).show();
                AlertDialog.Builder ad = new AlertDialog.Builder(view.getContext());
                ad.setTitle("삭제").setMessage("기록을 삭제하시겠습니까?").setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        holder.startDelete(item.getMarkerIdx());
                        notifyDataSetChanged();
                        //Toast.makeText(view.getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "취소하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addItem(Record item) {
        items.add(item);
    }

    public void setItems(List<Record> items) {
        this.items = items;
    }

    public Record getItem(int position) {
        return items.get(position);
    }

    public void setOnItemClickListener(OnRecordItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    public void setOnRecordModifyClickListener(OnRecordModifyListener onRecordModifyListener) {
        this.modifyListener = onRecordModifyListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView list_ImageView;

        ImageView record_edit;
        ImageView record_delete;

        TextView location_record;
        TextView contents_record;
        TextView record_date;

        LinearLayout layout1;
        Context context;

        public ViewHolder (View itemView, final OnRecordItemClickListener listener, OnRecordModifyListener modifyListener) {
            super(itemView);

            this.context = itemView.getContext();
            //각 View 할당
            list_ImageView = itemView.findViewById(R.id.list_ImageView);

            record_edit = itemView.findViewById(R.id.record_edit);
            record_delete = itemView.findViewById(R.id.record_delete);

            location_record = itemView.findViewById(R.id.location_record);
            contents_record = itemView.findViewById(R.id.contents_record);
            record_date = itemView.findViewById(R.id.record_date);

            layout1 = itemView.findViewById(R.id.layout1);


            //기록 수정 버튼 클릭
            record_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (modifyListener != null) {
                        modifyListener.onItemModifyClick(ViewHolder.this, view, position);
                    }

                }

            });
            /*
            //기록 삭제 버튼 클릭
            record_delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(record_delete.getContext());
                    ad.setTitle("삭제").setMessage("기록을 삭제하시겠습니까?").setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(record_delete.getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(record_delete.getContext(), "취소하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }).create().show();
                }
            });
            */
            //각 기록 클릭
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }


        public void setItem(Record item) {
            String contents = item.getText();
            contents_record.setText(contents);

            String picturePath = item.getImg();

            if (picturePath != null && !picturePath.equals("")) {

                Glide.with(contents_record.getContext()).load(picturePath).into(list_ImageView);

            } else {
                //이미지가 없을 경우 이미지 넣어두기?

            }

            record_date.setText(Integer.toString(item.getDate()));
            location_record.setText(item.getCity() + ", " + item.getCountry());
        }

        private void startDelete(int markerIdx) {
            SharedPreferences sharedPreferences = record_delete.getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");

            ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

            serviceApi.deleteRecord(token, new DeleteData(markerIdx)).enqueue(new Callback<DeleteResponse>() {
                @Override
                public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                    DeleteResponse result = response.body();

                    if (result.getStatus() == 200) {
                        Toast.makeText(record_delete.getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(record_delete.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DeleteResponse> call, Throwable t) {
                    Toast.makeText(record_delete.getContext(), "삭제 에러", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}