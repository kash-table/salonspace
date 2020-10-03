package com.example.salonspace;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_time extends RecyclerView.Adapter<MyAdapter_time.ViewHolder> {

    private ArrayList<String> itemList;
    private Context context;
    private View.OnClickListener onClickItem;
    private String selected_tag = "";

    public MyAdapter_time(Context context, ArrayList<String> itemList, View.OnClickListener onClickItem) {
        this.context = context;
        this.itemList = itemList;
        this.onClickItem = onClickItem;
    }

    public void setSelected_tag(String selected_tag) {
        this.selected_tag = selected_tag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_time, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = itemList.get(position);
        Log.e("test", item + "/" + selected_tag);
        if (!selected_tag.equals(item)) {
            holder.txt_time.setTextColor(Color.parseColor("#000000"));
        } else
        {
            holder.txt_time.setTextColor(Color.parseColor("#FE5722"));
        }
        holder.txt_time.setText(item);
        holder.txt_time.setTag(item);
        holder.txt_time.setOnClickListener(onClickItem);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_time; // 요일

        public ViewHolder(View itemView) {
            super(itemView);

            txt_time = itemView.findViewById(R.id.item_time);

        }
    }
}
