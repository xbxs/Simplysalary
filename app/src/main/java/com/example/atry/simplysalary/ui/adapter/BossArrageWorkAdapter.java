package com.example.atry.simplysalary.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.model.bean.Schedule;
import com.example.atry.simplysalary.model.bean.Vacate;

import java.util.ArrayList;
import java.util.List;

public class BossArrageWorkAdapter extends BaseAdapter {
    private Context mcontext;
    private List<Schedule> mlist = new ArrayList<>();
    public BossArrageWorkAdapter(Context context){
        this.mcontext = context;
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArrageWorkHolder holder = null;
        if(null == convertView){
            holder = new ArrageWorkHolder();
            convertView = View.inflate(mcontext, R.layout.item_arragework,null);
            holder.tv_name = convertView.findViewById(R.id.tv_name_arragework);
            convertView.setTag(holder);
        }else{
            holder = (ArrageWorkHolder) convertView.getTag();
        }
        Schedule schedule = mlist.get(position);
        holder.tv_name.setText(schedule.getU_phone());
        return convertView;
    }

    static class ArrageWorkHolder{
        TextView tv_name;
    }
    public void refresh(List<Schedule> schedules){
        if(schedules != null && schedules.size() >= 0){
            mlist.clear();
            mlist.addAll(schedules);
            notifyDataSetChanged();
        }
    }
}
