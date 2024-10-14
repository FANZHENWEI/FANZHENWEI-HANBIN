package com.example.dietplanapp.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.dietplanapp.R;
import com.example.dietplanapp.bean.Food;
import com.example.dietplanapp.callback.ICallback;
import com.example.dietplanapp.utils.SPUtils;

import java.util.List;
import java.util.stream.Collectors;


public class FoodSelectAdapter extends BaseAdapter {

        private List<Food> list;
    private ICallback callbackListener;

        private ListView listview;
    private Context mContext;
        private int type;
        public FoodSelectAdapter(Context context, List<Food> list, int type, ICallback listener) {
            super();
            this.list = list;
            mContext=context;
            this.type=type;
            setCallbackListener(listener);
        }
    // 设置回调
    public void setCallbackListener(ICallback listener) {
        this.callbackListener = listener;
    }
    // 发送通知的方法
    public void notifyMessage(int type,Boolean status) {
        if (callbackListener != null) {
            callbackListener.callbackNetStatus(type,status);
        }
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
                        R.layout.food_list_item, null);
                holder = new ViewHolder();
                holder.iv = (ImageView) convertView.findViewById(R.id.tv_stu_pic);
                holder.tv_objid = (TextView) convertView.findViewById(R.id.tv_obj_id);
                holder.tv_cal = (TextView) convertView.findViewById(R.id.tv_calorie);
                holder.tv_stu_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tv_stu_type = (TextView) convertView.findViewById(R.id.tv_desc);

                holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final Food myOrder = list.get(position);
            holder.tv_objid.setText(myOrder.getObjectId()+"");
            holder.tv_cal.setText(myOrder.kcal+"Kcal");
            holder.tv_stu_name.setText(myOrder.name);
            holder.tv_stu_type.setText( myOrder.num+"g");
            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    List <Food>foodList = SPUtils.getDataList("food_"+type,Food.class);
                    List <Food> delFoodList = foodList.stream().filter(food -> !food.name.equals(myOrder.name)).collect(Collectors.toList());
                    SPUtils.setDataList("food_"+type,delFoodList);
                    notifyMessage(type,true);
                }
            });
            return convertView;
    }
    class ViewHolder {
            ImageView iv,iv_delete;
            TextView tv_cal,tv_stu_name,tv_stu_type,tv_stu_price,tv_stu_date,tv_stu_status;
        TextView tv_rec_name,tv_rec_phone,tv_rec_addr;
            TextView tv_objid,tv_status2,tv_status3,tv_status4,tv_status5;
        }

}
