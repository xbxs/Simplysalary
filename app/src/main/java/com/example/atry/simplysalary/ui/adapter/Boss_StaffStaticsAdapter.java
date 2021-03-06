package com.example.atry.simplysalary.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.model.bean.SectionUser;
import com.example.atry.simplysalary.model.bean.StaffSalary;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.SPUtils;

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
            holder.tv_statics_wage = convertView.findViewById(R.id.tv_statics_wage);
            holder.tv_statics_shift = convertView.findViewById(R.id.tv_statics_shift);
            convertView.setTag(holder);
        }else{
            holder = (BossStaffStaticsHolder) convertView.getTag();
        }

        StaffSalary staffSalary = mSalarys.get(position);
        String month = staffSalary.getS_rtime().substring(5,7);
        String day = staffSalary.getS_rtime().substring(8,10);
        holder.tv_statics_time.setText(month+"月"+day+"日");
        holder.tv_statics_term.setText(staffSalary.getS_term());
        if("-1".equals(staffSalary.getS_wage())) {
            String rshift = staffSalary.getS_shift();
            holder.tv_statics_wage.setVisibility(View.GONE);
            holder.tv_statics_shift.setText(number2str(staffSalary.getS_shift()));
        }else{
            String rshift = staffSalary.getS_shift();
            String shift = "早班";
            if ("1".equals(rshift)) {
                shift = "早班";
            } else if ("2".equals(rshift)) {
                shift = "中班";
            } else if ("3".equals(rshift)) {
                shift = "晚班";
            }
            holder.tv_statics_shift.setText(shift);
            holder.tv_statics_wage.setText(staffSalary.getS_wage());
        }
        return convertView;
    }

    static class BossStaffStaticsHolder{
        private TextView tv_statics_time;
        private TextView tv_statics_term;
        private TextView tv_statics_wage;
        private TextView tv_statics_shift;
    }

    public void refresh(List<StaffSalary> salaries){
        if(null !=salaries && salaries.size() >=0){
            mSalarys.clear();
            mSalarys.addAll(salaries);
            notifyDataSetChanged();
        }

    }

    public String number2str(String str){
        if("1".equals(str)){
            str = "事假";
        }else if("2".equals(str)){
            str = "病假";
        }else if("3".equals(str)){
            str = "婚假";
        }else if("4".equals(str)){
            str = "丧假";
        }else{
            str = "其他";
        }

        return str;
    }


}
