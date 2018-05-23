package com.updayup.yuchance.createmvp.Model;

/**
 * Created by yuchance on 2018/3/26.
 */

public class LoginBean {


    /**
     * result : success
     * message : 用户认证成功
     * code : 0
     * body : {"phonenumber":"18210722011","userId":"593e66fd8cfe2343c24d0a10","isRealname":"0","hasPassword":"1","access_token":"130bc9ec-4d55-4f73-b68d-7d374784f976","validperiod":"864000","acount":"593e66fd8cfe2343c24d0a10","hxPassword":"4d22055","isDepartmentConsumer":"0","isBindWechatAccount":"0"}
     */

    private String result;
    private String message;
    private String code;
    private BodyBean body;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * phonenumber : 18210722011
         * userId : 593e66fd8cfe2343c24d0a10
         * isRealname : 0
         * hasPassword : 1
         * access_token : 130bc9ec-4d55-4f73-b68d-7d374784f976
         * validperiod : 864000
         * acount : 593e66fd8cfe2343c24d0a10
         * hxPassword : 4d22055
         * isDepartmentConsumer : 0
         * isBindWechatAccount : 0
         */

        private String phonenumber;
        private String userId;
        private String isRealname;
        private String hasPassword;
        private String access_token;
        private String validperiod;
        private String acount;
        private String hxPassword;
        private String isDepartmentConsumer;
        private String isBindWechatAccount;

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getIsRealname() {
            return isRealname;
        }

        public void setIsRealname(String isRealname) {
            this.isRealname = isRealname;
        }

        public String getHasPassword() {
            return hasPassword;
        }

        public void setHasPassword(String hasPassword) {
            this.hasPassword = hasPassword;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getValidperiod() {
            return validperiod;
        }

        public void setValidperiod(String validperiod) {
            this.validperiod = validperiod;
        }

        public String getAcount() {
            return acount;
        }

        public void setAcount(String acount) {
            this.acount = acount;
        }

        public String getHxPassword() {
            return hxPassword;
        }

        public void setHxPassword(String hxPassword) {
            this.hxPassword = hxPassword;
        }

        public String getIsDepartmentConsumer() {
            return isDepartmentConsumer;
        }

        public void setIsDepartmentConsumer(String isDepartmentConsumer) {
            this.isDepartmentConsumer = isDepartmentConsumer;
        }

        public String getIsBindWechatAccount() {
            return isBindWechatAccount;
        }

        public void setIsBindWechatAccount(String isBindWechatAccount) {
            this.isBindWechatAccount = isBindWechatAccount;
        }
    }
}
