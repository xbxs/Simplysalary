package com.example.atry.simplysalary.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.model.bean.SectionUser;
import com.example.atry.simplysalary.model.bean.StaffSalary;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class Boss_StaffStaticsAdapter extends BaseAdapter {
    private Context mcontext;
    private List<StaffSalary> mSalarys = new ArrayList<>();
    public Boss_StaffStaticsAdapter(Context context){
        this.mcontext = context;
    }
    @Override
    public int getCount() {
        return mSalarys.size();
    }

    @Override
    public Object getItem(int position) {
        return mSalarys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BossStaffStaticsHolder holder = null;
        if(convertView == null){
            convertView = View.inflate(mcontext, R.layout.item_bossstaffstatics,null);
            holder = new BossStaffStaticsHolder();
            holder.tv_statics_time = convertView.findViewById(R.id.tv_statics_time);
            holder.tv_statics_term = convertView.findViewById(R.id.tv_statics_term);
            holder.tv_statics_shift = convertView.findViewById(R.id.tv_statics_shift);
            convertView.setTag(holder);
        }else{
            holder = (BossStaffStaticsHolder) convertView.getTag();
        }

        StaffSalary staffSalary = mSalarys.get(position);
        holder.tv_statics_time.setText(staffSalary.getS_rtime());
        holder.tv_statics_term.setText(staffSalary.getS_term());
        holder.tv_statics_shift.setText(staffSalary.getS_shift());
        return convertView;
    }

    static class BossStaffStaticsHolder{
        private TextView tv_statics_time;
        private TextView tv_statics_term;
        private TextView tv_statics_shift;
    }

    public void refresh(List<StaffSalary> salaries){
        if(null !=salaries && salaries.size() >=0){
            mSalarys.clear();
            mSalarys.addAll(salaries);
            notifyDataSetChanged();
        }

    }


}
