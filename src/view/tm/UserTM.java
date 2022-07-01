package view.tm;

import javafx.scene.control.Button;

public class UserTM {
    private String accountType;
    private String userName;
    private String password;
    private Button btn;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public UserTM(String accountType, String userName, String password, Button btn) {
        this.accountType = accountType;
        this.userName = userName;
        this.password = password;
        this.btn = btn;
    }

    public UserTM() {
    }

    @Override
    public String toString() {
        return "UserTM{" +
                "accountType='" + accountType + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", btn=" + btn +
                '}';
    }
}
