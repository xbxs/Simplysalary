package com.example.atry.simplysalary.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.model.bean.InvationInfo;
import com.example.atry.simplysalary.model.bean.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 李维: TZZ on 2020-01-07 13:08
 * 邮箱: 3182430026@qq.com
 * <p>
 * 邀请信息列表的适配器
 */
public class InviteAdapter extends BaseAdapter {
    private Context mContext;
    private List<InvationInfo> mList = new ArrayList<>();
    private OnInviteListener mOnInviteListener;
    public InviteAdapter(Context context,OnInviteListener onInviteListener) {
        mContext = context;
        this.mOnInviteListener = onInviteListener;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_invite, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        InvationInfo info = mList.get(i);
        //得到联系人，判断是联系人请求还是群邀请
        User user = info.getUser();
        //先将按钮隐藏
        holder.btnInviteAccept.setVisibility(View.GONE);
        holder.btnInviteReject.setVisibility(View.GONE);
        if(user != null){
            //名称展示
            holder.tvInvitePhonenumber.setText(user.getPhonenumber());
            //原因
            if(info.getStatus() == InvationInfo.InvitationStatus.NEW_INVITE){//新的邀请
                //有新邀请时才显示
                holder.btnInviteAccept.setVisibility(View.VISIBLE);
                holder.btnInviteReject.setVisibility(View.VISIBLE);
                if(info.getReason() == null){
                    holder.tvInviteReason.setText("添加好友");
                }else{
                    holder.tvInviteReason.setText(info.getReason());
                }
                holder.btnInviteAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnInviteListener.OnAccept(info);
                    }
                });

                holder.btnInviteReject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnInviteListener.OnReject(info);
                    }
                });
            }else if(info.getStatus() == InvationInfo.InvitationStatus.INVITE_ACCEPT){
                if(info.getReason() == null){
                    holder.tvInviteReason.setText("接收邀请");
                }else{
                    holder.tvInviteReason.setText(info.getReason());
                }
            }else if(info.getStatus() == InvationInfo.InvitationStatus.INVTE_ACCEPT_BY_PEER){
                if(info.getReason() == null){
                    holder.tvInviteReason.setText("邀请被接受");
                }else{
                    holder.tvInviteReason.setText(info.getReason());
                }
            }
        }else{
            holder.tvInvitePhonenumber.setText(info.getGroupInfo().getInvatePerson());
            switch (info.getStatus()){
                //你的群申请已经被接受
                case GROUP_APPLICATION_ACCEPTED:
                    holder.tvInviteReason.setText("你的群申请已经被接受");
                    break;
                //你的群邀请已经被接收
                case GROUP_INVITE_ACCEPTED:
                    holder.tvInviteReason.setText("你的群邀请已经被接收");
                    break;
                //你的群申请被拒绝
                case GROUP_APPLICATION_DECLINED:
                    holder.tvInviteReason.setText("你的群申请被拒绝");
                    break;
                //你的群邀请被拒绝
                case GROUP_INVITE_DECLINED:
                    holder.tvInviteReason.setText("你的群邀请被拒绝");
                    break;
                //你收到群邀请
                case NEW_GROUP_INVITE:
                    holder.btnInviteAccept.setVisibility(View.VISIBLE);
                    holder.btnInviteReject.setVisibility(View.VISIBLE);

                    holder.tvInviteReason.setText("你收到群邀请");
                    //接收群邀请
                    holder.btnInviteAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onIviteAccept(info);
                        }
                    });
                    //拒绝群邀请
                    holder.btnInviteReject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onIviteReject(info);
                        }
                    });
                    break;
                //你收到了群申请
                case NEW_GROUP_APPLICATION:
                    holder.btnInviteAccept.setVisibility(View.VISIBLE);
                    holder.btnInviteReject.setVisibility(View.VISIBLE);
                    holder.tvInviteReason.setText("你收到群申请");
                    //批准群申请
                    holder.btnInviteAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onApplicationAccept(info);
                        }
                    });
                    //拒绝群申请
                    holder.btnInviteReject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onApplicationReject(info);
                        }
                    });
                    break;
                //你接受了群邀请
                case GROUP_ACCERP_INVITE:
                    holder.tvInviteReason.setText("你接受了群邀请");
                    break;
                //你批准了群加入
                case GROUP_ACCERP_APPLICATION:
                    holder.tvInviteReason.setText("你批准了群加入");
                    break;
            }
        }

        return convertView;
    }



    //刷新数据得方法
    public void refresh(List<InvationInfo> invationInfos) {
        if (invationInfos != null && invationInfos.size() >= 0) {
            mList.clear();
            mList.addAll(invationInfos);
            notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        @BindView(R.id.iv_invite_photo)
        ImageView ivInvitePhoto;
        @BindView(R.id.tv_invite_phonenumber)
        TextView tvInvitePhonenumber;
        @BindView(R.id.tv_invite_reason)
        TextView tvInviteReason;
        @BindView(R.id.btn_invite_accept)
        Button btnInviteAccept;
        @BindView(R.id.btn_invite_reject)
        Button btnInviteReject;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnInviteListener{
        //联系人接收按钮的点击事件
        public void OnAccept(InvationInfo invationInfo);
        //联系人接收按钮的点击事件
        public void OnReject(InvationInfo invationInfo);

        //接受邀请按钮的处理
        void onIviteAccept(InvationInfo invationInfo);
        //拒绝邀请按钮的处理
        void onIviteReject(InvationInfo invationInfo);

        //接受申请按钮处理
        void onApplicationAccept(InvationInfo invationInfo);
        //拒绝申请按钮处理
        void onApplicationReject(InvationInfo invationInfo);

    }
}
