package com.hazz.kuangji.mvp.model;

import java.io.Serializable;
import java.util.List;

public class Mill {

        public String total;
        public String yesterday;
        public String coin_name;
        public MachineListBean machine_list;

        public static class MachineListBean {

            public int pageNum;
            public int pageSize;
            public String total;
            public List<ListBean> list;
            public List<ListBean> boost_list;

            public static class ListBean implements Serializable {
                /**
                 * id : 82
                 * user_id : 132
                 * product_id : 1
                 * price : 100.00000000
                 * coin : USDT
                 * type : 1
                 * remain : 1790
                 * number : null
                 * state : 1
                 * create_at : 2020-04-05 03:47:55
                 * updated_at : 2020-04-18 15:43:21
                 * mobile :
                 * contractor :
                 * address :
                 * product : FIL算力服务器
                 * outcome_coin : FIL
                 * revenue : 210.00000000
                 * yesterday : 35.00000000
                 */

                public String id;
                public String user_id;
                public String product_id;
                public String price;
                public String product_price;
                public String coin;
                public String type;
                public String remain;
                public Object number;
                public String state;
                public String create_at;
                public String updated_at;
                public String mobile;
                public String contractor;
                public String address;
                public String product;
                public String outcome_coin;
                public String revenue;
                public String yesterday;
                public String miner_number;
                public String storage;
                public String buy_storage;
                public String hide_contract;//后台显示状态
                public String is_sign;//合同签名状态
                public String contract_path;//合同地址
                public int miner_type;//0  原服务器     1加速服务器
                public String build_remain;//剩余建设期
                public String seal_remain;//剩余封装
                public String pledge_amount;//质押总数
                public String gas_amount;//gas总数
                public String gas_consume;//gas消耗
            }

    }
}
