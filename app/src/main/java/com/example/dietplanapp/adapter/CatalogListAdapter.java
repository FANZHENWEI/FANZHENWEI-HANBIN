package com.example.dietplanapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dietplanapp.R;
import com.example.dietplanapp.bean.Option;

import java.util.List;

public class CatalogListAdapter extends BaseAdapter {

    private List<Option> list;
    private GridView gridView;

    private int clickTemp = -1;

    public CatalogListAdapter(List<Option> list) {
        super();
        this.list = list;


    }

    //标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (gridView == null) {
            gridView = (GridView) parent;
        }
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_option, null);
            holder = new ViewHolder();
            holder.item_tv_id = (TextView) convertView.findViewById(R.id.text);
            holder.iv = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Option meal = list.get(position);
        holder.item_tv_id.setText(meal.getId());
        holder.iv.setImageResource(meal.getIv());

        return convertView;
    }


    class ViewHolder {
        ImageView iv;
        TextView item_tv_id;
    }


}
