package netty.javaserialize;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private String name;
    private long userId;
    private String email;
    private String mobile;
    private String remark;

    private UserInfo(Builder builder) {
        setName(builder.name);
        setUserId(builder.userId);
        setEmail(builder.email);
        setMobile(builder.mobile);
        setRemark(builder.remark);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public static final class Builder {
        private String name;
        private long userId;
        private String email;
        private String mobile;
        private String remark;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder userId(long val) {
            userId = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder mobile(String val) {
            mobile = val;
            return this;
        }

        public Builder remark(String val) {
            remark = val;
            return this;
        }

        public UserInfo build() {
            return new UserInfo(this);
        }
    }
}
