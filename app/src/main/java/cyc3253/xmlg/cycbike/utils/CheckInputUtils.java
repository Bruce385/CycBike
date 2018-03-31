package cyc3253.xmlg.cycbike.utils;


/**
 * Created by CYC on 2018/1/4.
 */

public class CheckInputUtils {

    public static String checkPhoneNum(String phoneNum) {
        if (phoneNum == null || phoneNum.trim().equals("")) {
            return "电话号码不能为空";
        } else if (!phoneNum.matches("^[1][3-8]+\\d{9}$")) {
            return "请输入真实有效的电话号码";
        }
        return null;
    }

    public static String checkPassword(String password) {
        if (password == null || password.trim().equals("")) {
            return "密码不能为空";
        } else if (!password.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{7,15}$")) {
            return "密码必须是字母掺杂数字一共7~15位";
        }
        return null;
    }

    public static String checkNickName(String nickName) {
        if (nickName == null || nickName.trim().equals(""))
            return "用户名不能为空";
        return null;
    }

    public static String checkEmailBox(String emailBox) {
        if (emailBox == null || emailBox.trim().equals("")) {
            return "邮箱地址不能为空";
        } else if (!emailBox.matches("^[0-9A-Za-z][\\.-_0-9A-Za-z]*@[0-9A-Za-z]+(\\.[0-9A-Za-z]+)+$")) {
            return "请输入正确的邮箱地址";
        }
        return null;
    }

}
