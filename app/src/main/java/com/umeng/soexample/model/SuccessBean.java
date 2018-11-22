package com.umeng.soexample.model;

public class SuccessBean {

    /**
     * msg : 登录成功
     * code : 0
     * data : {"age":null,"appkey":"b40d20089f2d7369","appsecret":"A07C14D9111158D2BA5D9BDAF63BCB95","createtime":"2018-11-08T14:12:20","email":null,"fans":null,"follow":null,"gender":null,"icon":null,"latitude":null,"longitude":null,"mobile":"13383727318","money":null,"nickname":null,"password":"61DC43069DBE219A9F992E53899B2D92","praiseNum":null,"token":"C642430271C66ED4E1B19DEC1748BECC","uid":21014,"userId":null,"username":"13383727318"}
     */

    private String msg;
    private String code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * age : null
         * appkey : b40d20089f2d7369
         * appsecret : A07C14D9111158D2BA5D9BDAF63BCB95
         * createtime : 2018-11-08T14:12:20
         * email : null
         * fans : null
         * follow : null
         * gender : null
         * icon : null
         * latitude : null
         * longitude : null
         * mobile : 13383727318
         * money : null
         * nickname : null
         * password : 61DC43069DBE219A9F992E53899B2D92
         * praiseNum : null
         * token : C642430271C66ED4E1B19DEC1748BECC
         * uid : 21014
         * userId : null
         * username : 13383727318
         */

        private String age;
        private String appkey;
        private String appsecret;
        private String createtime;
        private String email;
        private String fans;
        private String follow;
        private String gender;
        private String icon;
        private String latitude;
        private String longitude;
        private String mobile;
        private String money;
        private String nickname;
        private String password;
        private String praiseNum;
        private String token;
        private String uid;
        private String userId;
        private String username;

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAppkey() {
            return appkey;
        }

        public void setAppkey(String appkey) {
            this.appkey = appkey;
        }

        public String getAppsecret() {
            return appsecret;
        }

        public void setAppsecret(String appsecret) {
            this.appsecret = appsecret;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFans() {
            return fans;
        }

        public void setFans(String fans) {
            this.fans = fans;
        }

        public String getFollow() {
            return follow;
        }

        public void setFollow(String follow) {
            this.follow = follow;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(String praiseNum) {
            this.praiseNum = praiseNum;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
