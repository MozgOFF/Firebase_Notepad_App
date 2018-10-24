package com.example.midterm_lab3;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> mItemList;

    public ItemsAdapter(List<Item> itemList) {
        this.mItemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item,
                viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.bindItem(mItemList.get(i));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bindItem(Item item) {
            LinearLayout LinearLayout = itemView.findViewById(R.id.linearLayout);
//            Spinner Spinner = itemView.findViewById(R.id.spinner);
            TextView titleLable = itemView.findViewById(R.id.title);
            TextView conentLable = itemView.findViewById(R.id.content);
            TextView spinnerLable = itemView.findViewById(R.id.priority);
            TextView idLable = itemView.findViewById(R.id.itemId);

            idLable.setText(item.getmItemId());
            titleLable.setText(item.getmTitle());
            conentLable.setText(item.getmContent());
            spinnerLable.setText(item.getmPriority());

            switch (item.getmPriority()) {
                case "Low":
                    LinearLayout.setBackgroundColor(Color.parseColor("#28c2e0"));
                    break;
                case "Medium":
                    LinearLayout.setBackgroundColor(Color.parseColor("#d7e028"));
                    break;
                case "High":
                    LinearLayout.setBackgroundColor(Color.parseColor("#e02828"));
                    break;
            }
        }
    }
}
