package com.artshell.mvp.storage.server.model;

/**
 * 用户信息
 * Created by artshell on 2018/3/16.
 */

public class UserInfoResponse extends HttpResult<UserInfoResponse.UserInfo>{
    public static class UserInfo {
        private String name;
        private String address;
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
