package com.example.atry.simplysalary.model.bean;

/**
 * 李维: TZZ on 2020-01-06 13:14
 * 邮箱: 3182430026@qq.com
 *
 * 邀请信息的bean
 */
public class InvationInfo {
    private User mUser;     //联系人
    private GroupInfo mGroupInfo; //群组

    private String reason;         //邀请原因

    private InvitationStatus mStatus;   //邀请的状态
    public InvationInfo(){

    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public GroupInfo getGroupInfo() {
        return mGroupInfo;
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        mGroupInfo = groupInfo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public InvitationStatus getStatus() {
        return mStatus;
    }

    public void setStatus(InvitationStatus status) {
        mStatus = status;
    }

    public InvationInfo(User user, GroupInfo groupInfo, String reason, InvitationStatus status) {
        mUser = user;
        mGroupInfo = groupInfo;
        this.reason = reason;
        mStatus = status;
    }

    public enum InvitationStatus{
        //联系人邀请信息状态
        NEW_INVITE, //新邀请
        INVITE_ACCEPT,//接收邀请
        INVTE_ACCEPT_BY_PEER,//邀请被接收

        //群邀请信息的状态

        //收到邀请去加入群
        NEW_GROUP_INVITE,
        //收到申请群加入
        NEW_GROUP_APPLICATION,
        //群邀请已经被对方接收
        GROUP_INVITE_ACCEPTED,
        //群邀请已经被拒绝
        GROUP_INVITE_DECLINED,
        //群邀请已经被批准
        GROUP_APPLICATION_ACCEPTED,
        //接收了群邀请
        GROUP_ACCERP_INVITE,
        //群邀请被拒绝
        GROUP_APPLICATION_DECLINED,
        //拒绝了群邀请
        GROUP_ACCERP_DECLINED,
        //批准了群加入
        GROUP_ACCERP_APPLICATION

    }
}
