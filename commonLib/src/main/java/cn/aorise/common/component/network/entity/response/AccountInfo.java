package cn.aorise.common.component.network.entity.response;

import java.io.Serializable;

/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 登录实体数据类
 *     version: 1.0
 * </pre>
 */
@Deprecated
public class AccountInfo implements Serializable {
    private String account;
    private String id;
    private String sex;

    public AccountInfo() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "account='" + account + '\'' +
                ", id='" + id + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
