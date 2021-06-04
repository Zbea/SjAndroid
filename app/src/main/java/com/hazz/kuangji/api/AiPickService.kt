package com.hazz.kuangji.api

import com.hazz.kuangji.mvp.model.*
import com.hazz.kuangji.net.BaseResult
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*
import java.io.InputStream


interface AiPickService{

    @GET("/api/app/config")
    fun getConfig(): Observable<BaseResult<Config>>
    @GET("/json/download.json")
    fun getDownloadCode(): Observable<Config>
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
    fun tibiRecord(): Observable<BaseResult<ExtractRecord>>
    /**
     * 充值
     */

    /**
     * 充值哦
     */
    @GET("trade/recharge/alladdress")
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
     * 获取资产可用详情
     */
    @POST("/api/trade/predaydetail")
    fun getAssetFilDetails(): Observable<BaseResult<List<AssetDetails>>>
    /**
     * 获取资产可用详情
     */
    @POST("/api/trade/predaydetail")
    fun getAssetFilDetails(@Query("start" )start:String, @Query("end" )end:String): Observable<BaseResult<List<AssetDetails>>>

    /**
     * 昨日收益来源
     */
    @GET("trade/filldetail?pageSize=30&pageNum=1")
    fun filEarningsList(): Observable<BaseResult<EarningsSource>>
    /**
     * 昨日收益来源
     */
    @GET("trade/usdtdetail?pageSize=30")
    fun usdtEarningsList(): Observable<BaseResult<EarningsSource>>
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
    @POST("boost/investment")
    fun zuyong(@Body request: RequestBody): Observable<BaseResult<Contract>>

    /**
     * 服务器
     */
    @GET("trade/investment")
    fun getMill(): Observable<BaseResult<Mill>>

    /**
     * 服务器明细
     */
    @GET("trade/statement")
    fun getMillEarnings(@Query("start" )start:String, @Query("end" )end:String): Observable<BaseResult<MillEarningsList>>
    /**
     * 获取服务器收益详情
     */
    @POST("/api/trade/miner_coinage")
    fun getEarningsDetails(@Query("order_id" ) type:String): Observable<BaseResult<List<MillEarningsDetails>>>
    /**
     * 获取服务器收益详情
     */
    @POST("/api/trade/miner_coinage?pageSize=20")
    fun getEarningsDetails(@Query("order_id" ) type:String,@Query("pageNum" ) page:String,@Query("start" )start:String, @Query("end" )end:String): Observable<BaseResult<List<MillEarningsDetails>>>
    /**
     * 服务器
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

    @GET("/api/boost/contact_list")
    fun getContracts(): Observable<BaseResult<List<Contract>>>
    @GET("/api/boost/contact_list")
    fun getContracts(@Query("order_id")code: String): Observable<BaseResult<List<Contract>>>
    /**
     * q签名上传
     */
    @Multipart
    @POST("trade/sign_contract")
    fun upSign(@Query("order_id") code: String,@Query("miner_type") type: String, @Part file: MultipartBody.Part): Observable<BaseResult<Contract>>
    /**
     * 下载
     */
    @GET("contractor")
    fun downPdf(@Query("miner_type") type: String,@Query("invest_id") id: String): Observable<ResponseBody>

    /**
     * 获取投资列表
     */
    @POST("/api/trade/get_user_deposit?pageSize=20")
    fun getInvestments(@Query("pageNum" )pageNum:String,@Query("type" )type:String): Observable<BaseResult<Investment>>
    /**
     * 获取投资产品列表
     */
    @POST("/api/deposit/list")
    fun getInvestmentProducts(): Observable<BaseResult<List<InvestmentProduct>>>

    /**
     * 购买投资产品
     */
    @POST("/api/trade/deposit_order")
    fun onInvestmentBuy(@Query("product" )product :String,@Query("amount" )amount:String,@Query("trade_password" )trade_password:String): Observable<BaseResult<Any>>

    /**
     * 购买投资产品
     */
    @POST("/api/trade/deposit_withdraw")
    fun outInvestment(@Query("order_id" )id :String,@Query("trade_password" )trade_password:String): Observable<BaseResult<Any>>

    /**
     * 获取集群方案
     */
    @POST("/api/cluster/products")
    fun getClusters(): Observable<BaseResult<Cluster>>


    /**
     * 提交集群订单
     */
    @POST("/api/cluster/into")
    fun commitCluster(@QueryMap map: HashMap<String, String>): Observable<BaseResult<Any>>


    /**
     * 集群资产可用详情
     */
    @POST("/api/cluster/all_coinage")
    fun getAssetClusterDetails(): Observable<BaseResult<AssetClusterEarningsDetails>>
    /**
     * 集群资产可用详情
     */
    @POST("/api/cluster/all_coinage")
    fun getAssetClusterDetails(@Query("start" )start:String, @Query("end" )end:String): Observable<BaseResult<AssetClusterEarningsDetails>>

    /**
     * 集群资产可用详情
     */
    @GET("/api/cluster/cluster_asset")
    fun getAssetCluster(): Observable<BaseResult<AssetCluster>>
    /**
     * 集群资产可用详情
     */
    @POST("/api/cluster/cluster_coinage?pageSize=20")
    fun getAssetEarningsList(@Query("pageNum" )start:String): Observable<BaseResult<AssetClusterEarningsDetails>>
    /**
     * 提交集群fil提取
     */
    @POST("/api/cluster/cluster_withdraw")
    fun extractCluster(@QueryMap map: HashMap<String, String>): Observable<BaseResult<Any>>
    /**
     * 提交集群fil提取记录
     */
    @GET("/api/cluster/withdraw_list")
    fun extractClusterList(): Observable<BaseResult<ExtractRecord>>

    /**
     * 获取加速服务器信息
     */
    @GET("/api/boost/getProduct")
    fun getAccelerateInfo(@Query("order_id") id: String): Observable<BaseResult<AccelerateInfo>>
    /**
     * 提交加速包
     */
    @POST("/api/boost/orderUp")
    fun commitAccelerate(@QueryMap map: HashMap<String, String>): Observable<BaseResult<Contract>>


    /**
     * 获取加速服务器收益详情
     */
    @POST("/api/boost/boost_coinage")
    fun getEarningsAccelerateDetails(@Query("order_id" ) type:String): Observable<BaseResult<List<MillEarningsDetails>>>
    /**
     * 获取加速服务器收益详情
     */
    @POST("/api/boost/boost_coinage?pageSize=20")
    fun getEarningsAccelerateDetails(@Query("order_id" ) type:String,@Query("pageNum" ) page:String,@Query("start" )start:String, @Query("end" )end:String): Observable<BaseResult<List<MillEarningsDetails>>>

}