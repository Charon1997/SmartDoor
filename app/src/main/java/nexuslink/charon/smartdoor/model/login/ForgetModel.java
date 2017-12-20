package nexuslink.charon.smartdoor.model.login;

import nexuslink.charon.smartdoor.contract.login.ForgetContract;

import static nexuslink.charon.smartdoor.utils.Constant.CODE_LENGTH;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/19 19:27
 * 修改人：Charon
 * 修改时间：2017/12/19 19:27
 * 修改备注：
 */

public class ForgetModel implements ForgetContract.Model {
    private String userId;
    private String code;
    private String password;
    private String password2;

    public ForgetModel(String userId, String code, String password, String password2) {
        this.userId = userId;
        this.code = code;
        this.password = password;
        this.password2 = password2;
    }

    public ForgetModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    @Override
    public boolean checkUserId(String userId) {
        return userId.length() == 11;
    }

    @Override
    public boolean checkPasswordEquals(String password, String password2) {
        return password.equals(password2) && !"".equals(password);
    }

    @Override
    public boolean checkCode(String code) {
        return code.length() == CODE_LENGTH;
    }

    @Override
    public boolean checkPasswordLength(String password) {
        return password.length() >= 6;
    }

    @Override
    public String isLegit(String userId, String code, String password, String password2) {
        if (checkUserId(userId)){
            if (checkCode(code)) {
                if (checkPasswordEquals(password, password2)) {
                    if (checkPasswordLength(password)) {
                        return "正确";
                    } else {
                        return "密码长度低于6位";
                    }
                } else {
                    return "密码不一致";
                }
            } else {
                return "验证码输入错误";
            }
        }else {
            return "号码输入错误";
        }
    }

}
