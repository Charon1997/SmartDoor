package nexuslink.charon.smartdoor.model.login;

import java.io.Serializable;

import nexuslink.charon.smartdoor.contract.login.LoginContract;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/12/19 19:26
 * 修改人：Charon
 * 修改时间：2017/12/19 19:26
 * 修改备注：
 */

public class LoginModel implements LoginContract.Model,Serializable {
    private String username;
    private String password;
    private boolean isPay;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public LoginModel() {
    }

    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
