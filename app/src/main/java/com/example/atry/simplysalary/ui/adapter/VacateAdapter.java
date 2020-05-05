package com.example.atry.simplysalary.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.model.bean.SectionUser;
import com.example.atry.simplysalary.model.bean.Vacate;
import com.example.atry.simplysalary.model.interface_package.OnItemClickListener;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class VacateAdapter extends BaseAdapter {
    private List<Vacate> mvacates = new ArrayList<>();
    private Context mcontext;
    private OnItemClickListener mOnItemClickListener;
    public VacateAdapter(Context context){
        this.mcontext = context;
    }
    @Override
    public int getCount() {
        return mvacates.size();
    }

    @Override
    public Object getItem(int position) {
        return mvacates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder= null;
        if(null == convertView){
            convertView = View.inflate(mcontext, R.layout.item_vacate,null);
            holder = new ViewHolder();
            holder.tv_uphone_vacate = convertView.findViewById(R.id.tv_uphone_vacate);
            holder.tv_type = convertView.findViewById(R.id.tv_type);
            holder.tv_term = convertView.findViewById(R.id.tv_term);
            holder.tv_status_vacate = convertView.findViewById(R.id.tv_status_vacate);
            holder.tv_begintime = convertView.findViewById(R.id.tv_begintime);
            holder.tv_endtime = convertView.findViewById(R.id.tv_endtime);
            holder.tv_applytime_vacate = convertView.findViewById(R.id.tv_applytime_vacate);
            holder.btn_refuse_vacate = convertView.findViewById(R.id.btn_refuse_vacate);
            holder.btn_agree_vacate = convertView.findViewById(R.id.btn_agree_vacate);
            int flag = SPUtils.getInstance().getInt(ConstantValues.LOGIN_IDENTITY,0);
            if(0 == flag){
                holder.btn_agree_vacate.setVisibility(View.GONE);
                holder.btn_refuse_vacate.setVisibility(View.GONE);
            }else {
                holder.btn_agree_vacate.setVisibility(View.VISIBLE);
                holder.btn_refuse_vacate.setVisibility(View.VISIBLE);
            }
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Vacate vacate = mvacates.get(position);
        holder.tv_uphone_vacate.setText(vacate.getU_phone());
        holder.tv_type.setText(number2str(vacate.getV_type()));
        holder.tv_term.setText(vacate.getV_term());
        holder.tv_begintime.setText(vacate.getV_btime());
        holder.tv_endtime.setText(vacate.getV_etime());
        holder.tv_applytime_vacate.setText(vacate.getV_rtime());
        if("1".equals(vacate.getV_status())){
            holder.tv_status_vacate.setText("待审核");
            int flag = SPUtils.getInstance().getInt(ConstantValues.LOGIN_IDENTITY,0);
            if(0 == flag){
                holder.btn_agree_vacate.setVisibility(View.GONE);
                holder.btn_refuse_vacate.setVisibility(View.GONE);
            }else {
                holder.btn_agree_vacate.setVisibility(View.VISIBLE);
                holder.btn_refuse_vacate.setVisibility(View.VISIBLE);
            }
        }else if("2".equals(vacate.getV_status())){
            holder.tv_status_vacate.setText("拒绝");
            holder.btn_agree_vacate.setVisibility(View.GONE);
            holder.btn_refuse_vacate.setVisibility(View.GONE);
        }else if("3".equals(vacate.getV_status())){
            holder.tv_status_vacate.setText("同意");
            holder.btn_agree_vacate.setVisibility(View.GONE);
            holder.btn_refuse_vacate.setVisibility(View.GONE);
        }
        holder.btn_refuse_vacate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v,position);
            }
        });
        holder.btn_agree_vacate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v,position);
            }
        });
        return convertView;
    }
    //建点击button的事件放置外部
    public void setselfItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }
    static class ViewHolder{
        TextView tv_uphone_vacate;
        TextView tv_type;
        TextView tv_term;
        TextView tv_begintime;
        TextView tv_endtime;
        TextView tv_applytime_vacate;
        TextView tv_status_vacate;
        Button btn_refuse_vacate,btn_agree_vacate;

    }
    public void refresh(List<Vacate> vacates){
        if(vacates != null && vacates.size() >= 0){
            mvacates.clear();
            mvacates.addAll(vacates);
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
