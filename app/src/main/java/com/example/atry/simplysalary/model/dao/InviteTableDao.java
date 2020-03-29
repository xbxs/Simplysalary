package com.example.atry.simplysalary.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.atry.simplysalary.model.bean.GroupInfo;
import com.example.atry.simplysalary.model.bean.InvationInfo;
import com.example.atry.simplysalary.model.bean.User;
import com.example.atry.simplysalary.model.db.ContactsDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 李维: TZZ on 2020-01-06 15:52
 * 邮箱: 3182430026@qq.com
 */
public class InviteTableDao {
    ContactsDBHelper mContactsDBHelper;
    public InviteTableDao(ContactsDBHelper contactsDBHelper){
        mContactsDBHelper = contactsDBHelper;
    }
    //添加申请
    public void addInvitation(InvationInfo invationInfo){
        //获取数据库连接
        SQLiteDatabase database = mContactsDBHelper.getWritableDatabase();
        //执行添加的语句
        ContentValues values  = new ContentValues();
        values.put(InviteTable.COL_REASON,invationInfo.getReason());
        values.put(InviteTable.COL_STATUS,invationInfo.getStatus().ordinal());

        User user = invationInfo.getUser();
        if(user != null) {
            values.put(InviteTable.COL_USER_HXID, invationInfo.getUser().getPhonenumber());
            values.put(InviteTable.COL_USER_NAME, invationInfo.getUser().getName());
        }else {
            values.put(InviteTable.COL_GROUP_NAME, invationInfo.getGroupInfo().getGroupName());
            values.put(InviteTable.COL_GROUP_HXID, invationInfo.getGroupInfo().getGroupId());
            values.put(InviteTable.COL_USER_HXID,invationInfo.getGroupInfo().getInvatePerson());
        }
        long number = database.replace(InviteTable.TAB_NAME,null,values);
        Log.i("TAG","插入数据库："+number);
    }
    //获取所有邀请信息
    public List<InvationInfo> getInvitations(){
        //得到数据库的连接
        SQLiteDatabase  database = mContactsDBHelper.getReadableDatabase();
        List<InvationInfo> list = new ArrayList<>();
        //执行查找语句
        String sql  = "select * from "+InviteTable.TAB_NAME;
        Cursor cursor = database.rawQuery(sql,null);
        if(cursor != null) {
            while (cursor.moveToNext()) {
                InvationInfo invationInfo = new InvationInfo();
                invationInfo.setReason(cursor.getString(cursor.getColumnIndex(InviteTable.COL_REASON)));
                invationInfo.setStatus(int2InviteStatus(cursor.getInt(cursor.getColumnIndex(InviteTable.COL_STATUS))));

                String groupId = cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_HXID));
                if (groupId == null) {
                    User user = new User();
                    user.setPhonenumber(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_HXID)));
                    user.setName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_NAME)));
//                    user.setPicture(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PICTURE)));
//                    user.setFlag(cursor.getInt(cursor.getColumnIndex(ContactTable.COL_FLAG)));
//                    user.setBossnumber(cursor.getInt(cursor.getColumnIndex(ContactTable.COL_BOSSNUMBER)));
//                    user.setDepartment(cursor.getString(cursor.getColumnIndex(ContactTable.COL_DEPARTMENT)));
                    invationInfo.setUser(user);
                } else {
                    GroupInfo groupInfo = new GroupInfo();
                    groupInfo.setGroupId(cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_HXID)));
                    groupInfo.setGroupName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_NAME)));
                    groupInfo.setInvatePerson(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_HXID)));
                    invationInfo.setGroupInfo(groupInfo);
                }
                list.add(invationInfo);
            }
            //关闭资源
            cursor.close();
        }


        //返回数据
        Log.i("TAG","从数据库中读取数据:"+list.size());
        return list;

    }

    //将int 类型状态转换成邀请的状态
    private InvationInfo.InvitationStatus int2InviteStatus(int intStatus){
        if(intStatus == InvationInfo.InvitationStatus.NEW_INVITE.ordinal()){
            return InvationInfo.InvitationStatus.NEW_INVITE;
        }
        if(intStatus == InvationInfo.InvitationStatus.INVITE_ACCEPT.ordinal()){
            return InvationInfo.InvitationStatus.INVITE_ACCEPT;
        }
        if(intStatus == InvationInfo.InvitationStatus.INVTE_ACCEPT_BY_PEER.ordinal()){
            return InvationInfo.InvitationStatus.INVTE_ACCEPT_BY_PEER;
        }
        if(intStatus == InvationInfo.InvitationStatus.NEW_GROUP_INVITE.ordinal()){
            return InvationInfo.InvitationStatus.NEW_GROUP_INVITE;
        }

        if(intStatus == InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION.ordinal()){
            return InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION;
        }
        if(intStatus == InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED.ordinal()){
            return InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED;
        }
        if(intStatus == InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED.ordinal()){
            return InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED;
        }
        if(intStatus == InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED.ordinal()){
            return InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED;
        }
        if(intStatus == InvationInfo.InvitationStatus.GROUP_ACCERP_DECLINED.ordinal()){
            return InvationInfo.InvitationStatus.GROUP_ACCERP_DECLINED;
        }
        if(intStatus == InvationInfo.InvitationStatus.GROUP_ACCERP_INVITE.ordinal()){
            return InvationInfo.InvitationStatus.GROUP_ACCERP_INVITE;
        }
        return null;
    }
    //删除邀请
    public void removeInvitation(String hxid){
        if(hxid == null){
            return;
        }
        //获取数据库连接
        SQLiteDatabase db = mContactsDBHelper.getReadableDatabase();
        //执行删除语句
        db.delete(InviteTable.TAB_NAME,InviteTable.COL_USER_HXID+"=?",new String[]{hxid});
    }

    //更新邀请状态
    public void updateInvitationStatus(InvationInfo.InvitationStatus invitationStatus,String hxid){
        if(hxid == null){
            return;
        }
        //获取数据库连接
        SQLiteDatabase database = mContactsDBHelper.getWritableDatabase();
        //执行更新的操作
        ContentValues values = new ContentValues();
        values.put(InviteTable.COL_STATUS,invitationStatus.ordinal());

        database.update(InviteTable.TAB_NAME,values,InviteTable.COL_USER_HXID+"=?",new String[]{hxid});
    }
}
