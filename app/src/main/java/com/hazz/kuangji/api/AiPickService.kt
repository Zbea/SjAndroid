package com.hazz.kuangji.api

import com.hazz.kuangji.mvp.model.*
import com.hazz.kuangji.net.BaseResult
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*



interface AiPickService{


    @GET("accounts/v0/buycoin/")
    fun getTest(): Observable<BaseResult<Any>>
    /**
     * 文件上传
     */
    @Multipart
    @POST("/api/uploader_img")
    fun upLoadImage(@Part file: MultipartBody.Part): Observable<BaseResult<UploadModel>>
    /**
     * 头像上传
     */
    @Multipart
    @POST("/api/modify_header")
    fun upHeaderImage(@Part file: MultipartBody.Part): Observable<BaseResult<UploadModel>>
    /**
     * 登陆
     */
    @POST("login")
    fun login(@Body request: RequestBody): Observable<BaseResult<UserInfo>>

    /**
     * 获取验证码
     */
    @GET("api/captcha/sms")
    fun sendCode(@Query("mobile" )mobile:String): Observable<Sms>

    /**
     *设置资金密码
     */
    @PUT("find/password")
    fun forgetPwd(@Body request: RequestBody): Observable<BaseResult<Any>>

    /**
     * 修改登陆密码
     */
    @PUT(" reset/password")
    fun resetPwd(@Body request: RequestBody): Observable<BaseResult<Any>>
    /**
     * 注册
     */
    @POST("user/register")
    fun regist(@Body request: RequestBody): Observable<BaseResult<Any>>

    /**
     * 获取行情
     */
    @GET("coins")
    fun getCoin(): Observable<BaseResult<List<Coin>>>

    /**
     * 获取g公告
     */
    @GET("notices")
    fun getMsg(): Observable<BaseResult<List<Msg>>>
    /**
     * 邀请好友
     */
    @GET("sharelog")
    fun getFriends(): Observable<BaseResult<Friends>>

    /**
     * 节点
     */
    @GET("team")
    fun getNode(): Observable<BaseResult<Node>>


    /**
     * 提币
     */
    @POST("trade/withdrawal")
    fun tibi(@Body request: RequestBody): Observable<BaseResult<Any>>


    /**
     * 提币记录
     */
    @GET("trade/withdrawal")
    fun tibiRecord(): Observable<BaseResult<TibiRecord>>
    /**
     * 充值
     */

    /**
     * 充值哦
     */
    @GET("trade/recharge/address")
    fun charge(@Query("coin" )coin:String): Observable<BaseResult<Charge>>
    /**
     * 充值omni
     */
    @GET("accounts/v0/getomniaddress")
    fun charge(): Observable<BaseResult<Charge>>
    /**
     * 充值记录
     */
    @GET("trade/recharge")
    fun chargeRecord(): Observable<BaseResult<ChargeRecord>>
    /**
     * 投资列表
     */
    @GET("trade/products")
    fun touxiList(): Observable<BaseResult<Touzi>>
    /**
     * 投资jilu
     */
    @GET("trade/investment")
    fun touziRecord(): Observable<BaseResult<TouziRecord>>
    /**
     * queren投资
     */
    @POST("trade/investment")
    fun touzi(@Body request: RequestBody): Observable<BaseResult<Any>>
    /**
     * 我的收益
     */
    @GET("revenue")
    fun inComing(): Observable<BaseResult<InComing>>

    /**
     * 我的资产
     */
    @GET("trade/my_asset")
    fun myAsset(): Observable<BaseResult<MyAsset>>

    /**
     * 首页
     */
    @GET("trade/products")
    fun getHome(): Observable<BaseResult<Home>>

    /**
     * 签到
     */
    @POST("trade/sign")
    fun sign(): Observable<BaseResult<Any>>

    /**
     * 立即租用
     */
    @POST("trade/investment")
    fun zuyong(@Body request: RequestBody): Observable<BaseResult<Any>>

    /**
     * 矿机
     */
    @GET("trade/investment")
    fun kuangji(): Observable<BaseResult<Mill>>

    /**
     * 矿机明细
     */
    @GET("trade/statement")
    fun mingxi(@Query("start" )start:String,@Query("end" )end:String): Observable<BaseResult<Mingxi>>

    /**
     * 矿机
     */
    @GET("profile")
    fun getAccount(): Observable<BaseResult<Account>>


    /**
     * 用户协议
     */
    @GET("about")
    fun xieyi(@Query("target")target:String): Observable<BaseResult<Xieyi>>


    /**
     * 签到记录
     */
    @GET("trade/sign_records")
    fun signRecord(): Observable<BaseResult<SignRecord>>


    /**
     * 获取我的币种信息
     */
    @GET("accounts/v0/buycoin/")
    fun getExchange(): Observable<BaseResult<Exchange>>
    /**
     * 提交订单
     */
    @GET("accounts/v0/addorder/{typeCoin}/{num}/{typePay}/{price}")
    fun commitOrder(@Path("typeCoin" ) typeCoin:String, @Path("num" ) num:String, @Path("typePay" ) typePay:String, @Path("price" ) price:String): Observable<BaseResult<ExchangeOrder>>
    /**
     * 订单详情
     */
    @GET("accounts/v0/orderdetails/{orderCode}")
    fun getOrder(@Path("orderCode")code:String): Observable<BaseResult<ExchangeOrder>>
    /**
     * 取消订单
     */
    @GET("accounts/v0/cancelorder/{orderCode}")
    fun cancelOrder(@Path("orderCode")code:String): Observable<BaseResult<Any>>

    /**
     * 我已经付款
     */
    @GET("accounts/v0/confirmorder/{orderCode}")
    fun commitPay(@Path("orderCode")code:String): Observable<BaseResult<Any>>
    /**
     * 获取订单明细 0全部1买入2卖出
     */
    @GET("accounts/v0/allorders/{type}")
    fun getOrderBuyList(@Path("type" ) type:String): Observable<BaseResult<List<ExchangeRecord>>>
    /**
     * 提交卖币订单
     */
    @Multipart
    @POST("user_selling")
    fun commitOrderSale(@QueryMap map:HashMap<String,String>,@Part file: MultipartBody.Part): Observable<BaseResult<ExchangeOrder>>
    /**
     * 提交卖币订单
     */
    @POST("user_selling")
    fun commitOrderSale(@QueryMap map:HashMap<String,String>): Observable<BaseResult<ExchangeOrder>>

    /**
     * 币币兑换
     */
    @FormUrlEncoded
    @POST("accounts/v0/trans/")
    fun commitExchange(@FieldMap map: HashMap<String, String>): Observable<BaseResult<Any>>
    /**
     * 获取卖币订单
     */
    @POST("get_order_detail")
    fun getOrderSale(@Query("order_code" ) type:String): Observable<BaseResult<ExchangeOrder>>

    /**
     * 取消卖币订单
     */
    @POST("cancel_order")
    fun cancelOrderSale(@Query("order_code" ) type:String): Observable<BaseResult<Any>>
    /**
     * 兑换明细
     */
    @GET("accounts/v0/translist/")
    fun getExchangeList(): Observable<BaseResult<List<ExchangeRecord>>>

    /**
     * 实名认证
     */
    @Multipart
    @POST("users/upload_identify")
    fun commitCertification(@QueryMap map:HashMap<String,String>,@Part files:List<MultipartBody.Part>): Observable<BaseResult<Any>>
    /**
     * 获取实名认证
     */
    @POST("users/get_identity")
    fun getCertification(): Observable<BaseResult<Certification>>

    /**
     * 转账给其他用户
     */
    @FormUrlEncoded
    @POST("accounts/v0/trans_accounts/")
    fun transAccount(@FieldMap map: HashMap<String, String>): Observable<BaseResult<Any>>

    /**
     * 转账给其他用户列表
     */
    @GET("accounts/v0/trans_accounts_list")
    fun getTransAccountList(@Query("type") type: Int): Observable<BaseResult<List<TransferCoin>>>
}