package com.example.atry.simplysalary.model.dao;

/**
 * 李维: TZZ on 2020-01-06 13:28
 * 邮箱: 3182430026@qq.com
 */

//联系人表的建表语句
public class ContactTable {
    public static  final  String TAB_NAME = "tab_account";
    public static  final  String COL_PHONEID = "phonenumber";
    public static  final  String COL_NAME = "name";
    public static  final  String COL_PICTURE = "picture";
    public static  final  String COL_FLAG = "flag";
    public static  final  String COL_BOSSNUMBER = "bossnumber";
    public static  final  String COL_DEPARTMENT = "department";

    public static final String COL_IS_CONTACT = "is_contact";//是否是联系人

    public static final  String CREATE_USER = "create table "
            +TAB_NAME+"("
            +COL_PHONEID +" varchar(11) primary key,"
            +COL_NAME +" text,"
            +COL_PICTURE+" text,"
            +COL_FLAG +" integer,"
            +COL_BOSSNUMBER +" varchar(11),"
            +COL_DEPARTMENT +" text,"
            +COL_IS_CONTACT +" integer);";


}
