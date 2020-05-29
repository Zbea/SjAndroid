package com.hazz.kuangji.socket;

public class CoinDetail {


    /**
     * ch : market.trxusdt.detail
     * ts : 1581942750713
     * tick : {"id":202177165295,"low":0.020053,"high":0.023492,"open":0.023388,"close":0.020885,"vol":3.671022526309392E7,"amount":1.7046954815874972E9,"version":202177165295,"count":245648}
     */

    public String ch;
    public long ts;
    public TickBean tick;

    public static class TickBean {
        /**
         * id : 202177165295
         * low : 0.020053
         * high : 0.023492
         * open : 0.023388
         * close : 0.020885
         * vol : 3.671022526309392E7
         * amount : 1.7046954815874972E9
         * version : 202177165295
         * count : 245648
         */

        public long id;
        public String low;
        public String high;
        public String open;
        public String close;
        public String vol;
        public String amount;
        public String version;
        public int count;

        @Override
        public String toString() {
            return "TickBean{" +
                    "id=" + id +
                    ", low='" + low + '\'' +
                    ", high='" + high + '\'' +
                    ", open='" + open + '\'' +
                    ", close='" + close + '\'' +
                    ", vol='" + vol + '\'' +
                    ", amount='" + amount + '\'' +
                    ", version='" + version + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
