package com.example.atry.simplysalary.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.atry.simplysalary.model.bean.GroupInfo;
import com.example.atry.simplysalary.model.bean.InvationInfo;
import com.example.atry.simplysalary.model.bean.User;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.SPUtils;
import com.example.atry.simplysalary.utils.Uiutils;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMucSharedFile;

import java.util.List;


/**
 * 李维: TZZ on 2020-01-06 18:55
 * 邮箱: 3182430026@qq.com
 * 全局事件的监听类
 */
public class EventListener {
    private Context mContext;
    private final LocalBroadcastManager mManager;

    public EventListener(Context context){
        mContext = context;
        //创建一个发送广播的管理者对象
        mManager = LocalBroadcastManager.getInstance(context);
        //注册一个联系人变化的监听
        EMClient.getInstance().contactManager().setContactListener(mEMContactListener);
        //注册群变化的监听

        EMClient.getInstance().groupManager().addGroupChangeListener(emGroupChangeListener);
    }

    //联系变化的监听
    private final EMContactListener mEMContactListener = new EMContactListener() {
       // 联系人增加后执行的方法
        @Override
        public void onContactAdded(String s) {
            //从服务器去获取联系人的信息
            User user   = new User();
            user.setPhonenumber(s);
            //数据库中的数据更新
            Model.getInstance().getDbManager().getContactTableDao().saveContact(user,true);
            //发送广播
            mManager.sendBroadcast(new Intent(ConstantValues.CONTACT_CHANGED));
        }
        //联系人删除后执行的方法
        @Override
        public void onContactDeleted(String hxid) {
            //去服务器删除联系人信息

            //数据库中的数据更新
            //删除联系人表中的数据
            Model.getInstance().getDbManager().getContactTableDao().deleteContactByHxId(hxid);
            //可以删除邀请信息
            //Model.getInstance().getDbManager().getInviteTableDao().removeInvitation(hxid);
            //发送联系人变化的广播
            mManager.sendBroadcast(new Intent(ConstantValues.CONTACT_CHANGED));
        }
        //接收到联系人的新邀请
        @Override
        public void onContactInvited(String hxid, String reason) {
            //服务区存储联系人的信息

            //在邀请的表中添加邀请数据
            InvationInfo info = new InvationInfo();
            User user = new User();
            user.setPhonenumber(hxid);
            info.setUser(user);
            info.setReason(reason);
            info.setStatus(InvationInfo.InvitationStatus.NEW_INVITE);
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(info);
            //让邀请栏的小红点出现
            //小红点处理
            SPUtils.getInstance().save(ConstantValues.IS_NEW_INVITE,true);
            //发送广播
            mManager.sendBroadcast(new Intent(ConstantValues.CONTACT_INVITE_CHANGED));

            Log.i("TAG","收到邀请");

        }
        //别人同意了你的好友邀请
        @Override
        public void onFriendRequestAccepted(String hxid) {
            //数据库更新
            InvationInfo info = new InvationInfo();
            User user = new User();
            user.setPhonenumber(hxid);
            info.setUser(user);
            info.setStatus(InvationInfo.InvitationStatus.INVTE_ACCEPT_BY_PEER);
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(info);
            //红点的处理
            SPUtils.getInstance().save(ConstantValues.IS_NEW_INVITE,true);
            //发送广播
            mManager.sendBroadcast(new Intent(ConstantValues.CONTACT_INVITE_CHANGED));
        }
        //别人拒绝了你的好友邀请
        @Override
        public void onFriendRequestDeclined(String s) {
            //红点的处理
            SPUtils.getInstance().save(ConstantValues.IS_NEW_INVITE,true);
            //发送广播
            mManager.sendBroadcast(new Intent(ConstantValues.CONTACT_INVITE_CHANGED));
        }
    };

    EMGroupChangeListener emGroupChangeListener = new EMGroupChangeListener() {
        //收到群邀请
        @Override
        public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {
            //数据更新
            InvationInfo invationinfo = new InvationInfo();
            invationinfo.setReason(reason);
            invationinfo.setGroupInfo(new GroupInfo(groupName,groupId,inviter));
            invationinfo.setStatus(InvationInfo.InvitationStatus.NEW_GROUP_INVITE);
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invationinfo);
            //红点处理
            SPUtils.getInstance().save(ConstantValues.IS_NEW_INVITE,true);
            //发送广播
            mManager.sendBroadcast(new Intent(ConstantValues.SECTION_CHANGED));
        }
        //收到群申请通知

        @Override
        public void onRequestToJoinReceived(String groupId, String groupName, String applicant, String reason){
            //数据更新
            InvationInfo invationinfo = new InvationInfo();
            invationinfo.setReason(reason);
            invationinfo.setGroupInfo(new GroupInfo(groupName,groupId,applicant));
            invationinfo.setStatus(InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION);
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invationinfo);
           //红点处理
           SPUtils.getInstance().save(ConstantValues.IS_NEW_INVITE,true);
            //发送广播
            mManager.sendBroadcast(new Intent(ConstantValues.SECTION_CHANGED));
        }
        //收到 群申请被接受
        @Override
        public void onRequestToJoinAccepted(String groupId, String groupName, String accepter) {
            InvationInfo invationinfo = new InvationInfo();
            invationinfo.setGroupInfo(new GroupInfo(groupName,groupId,accepter));
            invationinfo.setStatus(InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED);
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invationinfo);
            //红点处理
            SPUtils.getInstance().save(ConstantValues.IS_NEW_INVITE,true);
            //发送广播
            mManager.sendBroadcast(new Intent(ConstantValues.SECTION_CHANGED));
        }
        //收到 群申请被拒绝
        @Override
        public void onRequestToJoinDeclined(String groupId, String groupName, String decliner, String reason) {
            InvationInfo invationinfo = new InvationInfo();
            invationinfo.setReason(reason);
            invationinfo.setGroupInfo(new GroupInfo(groupName,groupId,decliner));
            invationinfo.setStatus(InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED);
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invationinfo);
            //红点处理
            SPUtils.getInstance().save(ConstantValues.IS_NEW_INVITE,true);
            //发送广播
            mManager.sendBroadcast(new Intent(ConstantValues.SECTION_CHANGED));
        }
        //收到 群邀请被同意
        @Override
        public void onInvitationAccepted(String groupId, String inviter, String reason) {
            InvationInfo invationinfo = new InvationInfo();
            invationinfo.setReason(reason);
            invationinfo.setGroupInfo(new GroupInfo(groupId,groupId,inviter));
            invationinfo.setStatus(InvationInfo.InvitationStatus.GROUP_ACCERP_INVITE);
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invationinfo);
            //红点处理
            SPUtils.getInstance().save(ConstantValues.IS_NEW_INVITE,true);
            //发送广播
            mManager.sendBroadcast(new Intent(ConstantValues.SECTION_CHANGED));
        }

        @Override
        public void onInvitationDeclined(String groupId, String inviter, String reason) {
            InvationInfo invationinfo = new InvationInfo();
            invationinfo.setReason(reason);
            invationinfo.setGroupInfo(new GroupInfo(groupId,groupId,inviter));
            invationinfo.setStatus(InvationInfo.InvitationStatus.GROUP_ACCERP_DECLINED);
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invationinfo);
            //红点处理
            SPUtils.getInstance().save(ConstantValues.IS_NEW_INVITE,true);
            //发送广播
            mManager.sendBroadcast(new Intent(ConstantValues.SECTION_CHANGED));
        }

        @Override
        public void onUserRemoved(String s, String s1) {
            //红点处理
            SPUtils.getInstance().save(ConstantValues.IS_NEW_INVITE,true);
            //发送广播
            mManager.sendBroadcast(new Intent(ConstantValues.SECTION_CHANGED));
        }

        @Override
        public void onGroupDestroyed(String s, String s1) {

        }
        //群邀请 自动被接受
        @Override
        public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String inviteMessage) {
//            InvationInfo invationinfo = new InvationInfo();
//            invationinfo.setReason(inviteMessage);
//            invationinfo.setGroupInfo(new GroupInfo(groupId,groupId,inviter));
//            invationinfo.setStatus(InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED);
//            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invationinfo);
//            //红点处理
//            SPUtils.getInstance().save(ConstantValues.IS_NEW_INVITE,true);
//            //发送广播
//            mManager.sendBroadcast(new Intent(ConstantValues.SECTION_CHANGED));
            onInvitationAccepted(groupId,inviter,inviteMessage);
        }

        @Override
        public void onMuteListAdded(String s, List<String> list, long l) {

        }

        @Override
        public void onMuteListRemoved(String s, List<String> list) {

        }

        @Override
        public void onAdminAdded(String s, String s1) {

        }

        @Override
        public void onAdminRemoved(String s, String s1) {

        }

        @Override
        public void onOwnerChanged(String s, String s1, String s2) {

        }

        @Override
        public void onMemberJoined(String s, String s1) {

        }

        @Override
        public void onMemberExited(String s, String s1) {

        }

        @Override
        public void onAnnouncementChanged(String s, String s1) {

        }

        @Override
        public void onSharedFileAdded(String s, EMMucSharedFile emMucSharedFile) {

        }

        @Override
        public void onSharedFileDeleted(String s, String s1) {

        }
    };
}
