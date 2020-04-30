package com.example.atry.simplysalary.model.dao;

/**
 * 李维: TZZ on 2019-12-29 13:46
 * 邮箱: 3182430026@qq.com
 */
public class UserAccountTable {
    public static  final  String TAB_NAME = "tab_account";
    public static  final  String COL_PHONEID = "phonenumber";
    public static  final  String COL_NAME = "name";
    public static  final  String COL_PICTURE = "picture";
    public static  final  String COL_FLAG = "flag";
    public static  final  String COL_PW = "pw";
    public static  final  String COL_BAS = "u_bas";
    public static  final  String COL_WAGE = "u_wage";
    public static  final  String COL_DEPARTMENT = "department";

    public static final  String CREATE_USER = "create table "
            +TAB_NAME+"("
            +COL_PHONEID +" varchar(11) primary key,"
            +COL_PW +" text,"
            +COL_NAME +" text,"
            +COL_PICTURE+" text,"
            +COL_FLAG +" integer,"
            +COL_BAS+" integer,"
            +COL_WAGE +" integer,"
            +COL_DEPARTMENT +" text);";


}
