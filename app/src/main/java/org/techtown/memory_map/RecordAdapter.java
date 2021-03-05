package org.techtown.memory_map;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> implements OnRecordItemClickListener {
    //ArrayList <Record> items = new ArrayList<Record>();
    private List<Record> items;

    OnRecordItemClickListener listener;


    @NonNull
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.record_xml, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.ViewHolder holder, int position) {
        //Record item = items.get(position);
        Record item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /*
    public void addItem(Record item) {
        items.add(item);
    }

    public void setItems(ArrayList<Record> items) {
        this.items = items;
    }

    public Record getItem(int position) {
        return items.get(position);
    }

    */

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

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView list_ImageView;

        TextView location_record;
        TextView contents_record;

        LinearLayout layout1;


        public ViewHolder (View itemView, final OnRecordItemClickListener listener) {
            super(itemView);

            list_ImageView = itemView.findViewById(R.id.list_ImageView);

            location_record = itemView.findViewById(R.id.location_record);
            contents_record = itemView.findViewById(R.id.contents_record);

            layout1 = itemView.findViewById(R.id.layout1);

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
                list_ImageView.setVisibility(View.VISIBLE);
                list_ImageView.setImageURI(Uri.parse("file://" + picturePath));
            } else {
                //이미지가 없을 경우 이미지 넣어두기?

            }

            //contents_record.setText(item.getContents());
            location_record.setText(item.getCity() + ", " + item.getCountry());
        }
    }
}
