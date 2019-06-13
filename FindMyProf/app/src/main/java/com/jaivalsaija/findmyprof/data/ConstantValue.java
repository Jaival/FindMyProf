package com.jaivalsaija.findmyprof.data;

public class ConstantValue {
    private static final String Root_Url = "http://192.168.43.226/findmyprof/scripts/";

    //Login Url
    public static final String Url_Log_Stu = Root_Url + "loginStudent.php";
    public static final String Url_Log_Pro = Root_Url + "loginProfessor.php";
    public static final String Url_get_Prof = Root_Url + "getProfessorRegistrationDevice.php";
    public static final String Url_Change_Student_Password = Root_Url + "changeStudentPassword.php";
    public static final String Url_Change_Professor_Password = Root_Url + "changeProfessorPassword.php";
    public static final String Url_Reg_Prof_Token = Root_Url + "registerProfessorDevice.php";
    public static final String Url_Reg_Stud_Token = Root_Url + "registerStudentDevice.php";
    public static final String Url_Stud_Send_Notif = Root_Url + "singleStudentPush.php";
    public static final String Url_Prof_Send_Notif = Root_Url + "singleProfessorPush.php";

//    public static final String Url_Log_Pro = Root_Url + "loginProfessor.php";
//    public static final String Url_add_Time = Root_Url + "addTime.php";
//    public static final String Url_update_Time = Root_Url + "updateTime.php";

}
