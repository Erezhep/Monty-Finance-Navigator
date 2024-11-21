package com.example.montyapp.templates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.montyapp.R;

import java.util.List;

public class ItemsHistory extends BaseAdapter {
    private final Context context;
    private final List<Item> items;

    // Конструктор
    public ItemsHistory(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.items_history, parent, false);
        }

        // Находим элементы макета
        TextView text1 = convertView.findViewById(R.id.text1);
        TextView text4 = convertView.findViewById(R.id.text4);

        // Устанавливаем данные для текущего элемента
        Item currentItem = items.get(position);
        text1.setText(currentItem.getTitle());  // Название

        String priceText = String.format("%.2f", currentItem.getPrice());
        if (currentItem.getPrice() > 0){
            priceText = "+" + priceText;
        }
        text4.setText(priceText);

        if (currentItem.getPrice() > 0){
            text4.setTextColor(context.getResources().getColor(R.color.green)); // Цена
        }else{
            text4.setTextColor(context.getResources().getColor(R.color.red));
        }
        return convertView;
    }
}
