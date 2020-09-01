package com.hazz.kuangji.mvp.model;

public class UserInfo {


        /**
         * token : ed3b125759da5b836c61ec80d283dece
         * id : 1
         * username : test
         * type : 0
         * mobile : 1300000000
         * version : 1.0
         * invitation_code : 451291
         * invitation_code_url : http://test.aimsfun.com.au/web/#/register?code=451291
         * wallet_address :
         * asset : {"amount":"0.00000000","coin":"USDT"}
         */

        public String token;
        public String id;
        public String username;
        public String type;
        public String mobile;
        public String version;
        public String invitation_code;
        public String invitation_code_url;
        public String wallet_address;
        public String profile_img;
        public AssetBean asset;

        public static class AssetBean {
            /**
             * amount : 0.00000000
             * coin : USDT
             */

            public String amount;
            public String coin;
        }

}
