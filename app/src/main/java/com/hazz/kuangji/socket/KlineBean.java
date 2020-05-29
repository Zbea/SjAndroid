package com.hazz.kuangji.socket;

import java.util.List;

public class KlineBean {

    /**
     * id : id10
     * rep : market.ethbtc.kline.1min
     * status : ok
     * data : [{"amount":725.4051,"open":0.027642,"close":0.027636,"high":0.027654,"id":1581762480,"count":105,"low":0.027624,"vol":20.0517368446},{"amount":14.1867,"open":0.027636,"close":0.027604,"high":0.027636,"id":1581762540,"count":41,"low":0.027604,"vol":0.391723495},{"amount":66.8458,"open":0.027612,"close":0.027641,"high":0.02766,"id":1581762600,"count":59,"low":0.027612,"vol":1.8468239923},{"amount":23.1135,"open":0.027641,"close":0.027651,"high":0.027659,"id":1581762660,"count":19,"low":0.027641,"vol":0.6389463595},{"amount":61.3801,"open":0.027659,"close":0.027648,"high":0.027665,"id":1581762720,"count":47,"low":0.027632,"vol":1.6968861787},{"amount":1.958,"open":0.027645,"close":0.02766,"high":0.027666,"id":1581762780,"count":19,"low":0.027644,"vol":0.0541487596},{"amount":5.6983,"open":0.027653,"close":0.027644,"high":0.02766,"id":1581762840,"count":17,"low":0.02764,"vol":0.1575598174},{"amount":31.6747,"open":0.027638,"close":0.027619,"high":0.027656,"id":1581762900,"count":53,"low":0.027619,"vol":0.8753411834},{"amount":17.4567,"open":0.027619,"close":0.027609,"high":0.027625,"id":1581762960,"count":40,"low":0.027608,"vol":0.4821624796},{"amount":35.5537,"open":0.027607,"close":0.027591,"high":0.027607,"id":1581763020,"count":55,"low":0.027585,"vol":0.98094821},{"amount":24.065,"open":0.027591,"close":0.027586,"high":0.027597,"id":1581763080,"count":42,"low":0.027583,"vol":0.6639769316},{"amount":9.7964,"open":0.027593,"close":0.027599,"high":0.0276,"id":1581763140,"count":33,"low":0.027593,"vol":0.2703723364},{"amount":100.4001,"open":0.027595,"close":0.027601,"high":0.027601,"id":1581763200,"count":49,"low":0.027587,"vol":2.7709793278},{"amount":15.0421,"open":0.027601,"close":0.027564,"high":0.027601,"id":1581763260,"count":79,"low":0.027564,"vol":0.4149357421},{"amount":55.8163,"open":0.027564,"close":0.027564,"high":0.027574,"id":1581763320,"count":92,"low":0.027544,"vol":1.538189711},{"amount":64.9621,"open":0.027563,"close":0.027532,"high":0.027563,"id":1581763380,"count":55,"low":0.027532,"vol":1.7897636149},{"amount":55.6628,"open":0.027541,"close":0.027574,"high":0.02759,"id":1581763440,"count":90,"low":0.027535,"vol":1.534388973},{"amount":31.7081,"open":0.027583,"close":0.027581,"high":0.027601,"id":1581763500,"count":62,"low":0.027581,"vol":0.8748988416},{"amount":129.6162,"open":0.027581,"close":0.02762,"high":0.02762,"id":1581763560,"count":52,"low":0.027581,"vol":3.5779972814},{"amount":19.5853,"open":0.027611,"close":0.027615,"high":0.02762,"id":1581763620,"count":77,"low":0.027594,"vol":0.5406673951},{"amount":41.5847,"open":0.027613,"close":0.027619,"high":0.02762,"id":1581763680,"count":65,"low":0.027594,"vol":1.1478676844},{"amount":4.8453,"open":0.027619,"close":0.027612,"high":0.027619,"id":1581763740,"count":41,"low":0.027595,"vol":0.1337745017},{"amount":254.4278,"open":0.02761,"close":0.027591,"high":0.02761,"id":1581763800,"count":91,"low":0.027576,"vol":7.0203477157},{"amount":117.7766,"open":0.027593,"close":0.027596,"high":0.027608,"id":1581763860,"count":64,"low":0.02758,"vol":3.249161136},{"amount":88.9411,"open":0.027604,"close":0.027598,"high":0.027609,"id":1581763920,"count":43,"low":0.027591,"vol":2.4546455107},{"amount":1.5387,"open":0.027594,"close":0.027599,"high":0.0276,"id":1581763980,"count":5,"low":0.027594,"vol":0.0424678934},{"amount":38.6609,"open":0.0276,"close":0.027619,"high":0.027627,"id":1581764040,"count":50,"low":0.0276,"vol":1.0675993548},{"amount":118.3345,"open":0.02762,"close":0.027618,"high":0.027624,"id":1581764100,"count":27,"low":0.027611,"vol":3.267441401},{"amount":5.468,"open":0.027619,"close":0.027598,"high":0.027622,"id":1581764160,"count":30,"low":0.027598,"vol":0.1509542894},{"amount":6.9656,"open":0.027597,"close":0.027606,"high":0.027608,"id":1581764220,"count":30,"low":0.027594,"vol":0.1922448686},{"amount":13.9987,"open":0.027602,"close":0.027605,"high":0.027605,"id":1581764280,"count":27,"low":0.0276,"vol":0.3864227505}]
     */

    public String id;
    public String rep;
    public String status;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * amount : 725.4051
         * open : 0.027642
         * close : 0.027636
         * high : 0.027654
         * id : 1581762480
         * count : 105
         * low : 0.027624
         * vol : 20.0517368446
         */

        public double amount;
        public double open;
        public double close;
        public double high;
        public int id;
        public int count;
        public double low;
        public double vol;
    }
}
