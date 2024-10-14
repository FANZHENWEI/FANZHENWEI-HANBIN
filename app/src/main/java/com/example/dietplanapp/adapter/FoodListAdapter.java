package com.example.dietplanapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dietplanapp.R;
import com.example.dietplanapp.bean.Food;
import com.example.dietplanapp.bean.FoodDetails;

import java.util.List;


public class FoodListAdapter extends BaseAdapter {

        private List<FoodDetails> list;

        private ListView listview;
    private Context mContext;

        public FoodListAdapter(Context context, List<FoodDetails> list) {
            super();
            this.list = list;
            mContext=context;


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
            if (listview == null) {
                listview = (ListView) parent;
            }
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.calorie_list_item, null);
                holder = new ViewHolder();
                holder.iv = (ImageView) convertView.findViewById(R.id.tv_stu_pic);
                holder.tv_objid = (TextView) convertView.findViewById(R.id.tv_obj_id);
                holder.tv_cal = (TextView) convertView.findViewById(R.id.tv_calorie);
                holder.tv_stu_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tv_stu_type = (TextView) convertView.findViewById(R.id.tv_desc);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final FoodDetails myOrder = list.get(position);
            holder.tv_objid.setText(myOrder.getObjectId()+"");
          //  holder.tv_cal.setText(myOrder.num+"毫克");
            holder.tv_stu_name.setText(myOrder.name);
            holder.tv_stu_type.setText( myOrder.kcal+"Kcal/100g");

            return convertView;
    }
    class ViewHolder {
            ImageView iv;
            TextView tv_cal,tv_stu_name,tv_stu_type,tv_stu_price,tv_stu_date,tv_stu_status;
        TextView tv_rec_name,tv_rec_phone,tv_rec_addr;
            TextView tv_objid,tv_status2,tv_status3,tv_status4,tv_status5;
        }

}
