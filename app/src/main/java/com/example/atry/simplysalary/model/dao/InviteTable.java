package com.example.atry.simplysalary.model.dao;

/**
 * 李维: TZZ on 2020-01-06 15:13
 * 邮箱: 3182430026@qq.com
 *
 * 邀请信息的表
 */
public class InviteTable {
    public static final  String TAB_NAME = "tab_name";

    public static final  String COL_USER_HXID = "user_hxid"; //用户的环信id
    public static final  String COL_USER_NAME = "user_name"; //用户的名称

    public static final  String COL_GROUP_HXID = "group_hxid"; //群组环信id
    public static final  String COL_GROUP_NAME = "group_name";  //群组名称


    public static final  String COL_REASON = "reason";  //邀请的原因
    public static final  String COL_STATUS = "status";  //邀请的原因

    public static final String CREATE_INVITE = "create table "
            +TAB_NAME+" ("
            +COL_USER_HXID+" varchar(11),"
            +COL_USER_NAME+" text,"
            +COL_GROUP_HXID+" varchar(11),"
            +COL_GROUP_NAME+" text,"
            +COL_REASON+" text,"
            +COL_STATUS+" integer);";

}
