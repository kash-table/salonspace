package com.example.salonspace;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<ReserveInfo> itemList;
    private Context context;
    private View.OnClickListener onClickItem;
    private String selected_date = "";
    static class ReserveInfo{
        String day;
        String date;
        String today;
    }
    public MyAdapter(Context context, ArrayList<ReserveInfo> itemList, View.OnClickListener onClickItem) {
        this.context = context;
        this.itemList = itemList;
        this.onClickItem = onClickItem;
    }

    public void setSelected_date(String selected_date) {
        this.selected_date = selected_date;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_tv, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReserveInfo item = itemList.get(position);

        if (!selected_date.equals(item)) {
            holder.txt_date.setTextColor(Color.parseColor("#000000"));
        } else
        {
            holder.txt_date.setTextColor(Color.parseColor("#FE5722"));
        }

        holder.txt_date.setText(item.date);
        holder.txt_date.setTag(item.date);
        holder.txt_date.setOnClickListener(onClickItem);

        holder.txt_day.setText(item.day);
        holder.txt_day.setTag(item.day);
        // holder.txt_day.setOnClickListener(onClickItem);

        holder.txt_today.setText(item.today);
        holder.txt_today.setTag(item.today);
        // holder.txt_today.setOnClickListener(onClickItem);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_day; // 요일
        public TextView txt_date; // 날짜
        public TextView txt_today; // 오늘인지

        public ViewHolder(View itemView) {
            super(itemView);

            txt_day = itemView.findViewById(R.id.item_day);
            txt_date = itemView.findViewById(R.id.item_date);
            txt_today = itemView.findViewById(R.id.item_isToday);
        }
    }
}
