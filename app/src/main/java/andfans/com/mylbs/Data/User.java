package andfans.com.mylbs.Data;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/*
 * Created by 兆鹏 on 2016/12/4.
 */
public class User extends BmobObject {
    private String userNickname;
    private String telePhone;
    private String userPassword;
    private BmobFile userPic;
    public User(){}
    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public BmobFile getUserPic() {
        return userPic;
    }

    public void setUserPic(BmobFile userPic) {
        this.userPic = userPic;
    }
}
