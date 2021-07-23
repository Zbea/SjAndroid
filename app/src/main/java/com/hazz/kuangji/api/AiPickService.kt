package com.hazz.kuangji.api

import com.hazz.kuangji.mvp.model.*
import com.hazz.kuangji.net.BaseResult
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface AiPickService{

    /**
     * 头像上传
     */
    @Multipart
    @POST("/app/header/modify")
    fun upHeaderImage(@Part file: MultipartBody.Part): Observable<BaseResult<UploadModel>>
    /**
     * 登陆
     */
    @POST("/app/login")
    fun login(@Body request: RequestBody): Observable<BaseResult<UserInfo>>

    /**
     * 获取验证码  "0 注册密码 1 修改登录密码 2 修改交易密码 3 实名认证"
     */
    @GET("/captcha/sms")
    fun sendCode(@Query("mobile" )mobile:String,@Query("type" )type:String): Observable<BaseResult<Any>>

    /**
     *找回登录密码
     */
    @POST("/app/login/password")
    fun findLoginPwd(@Body request: RequestBody): Observable<BaseResult<Any>>

    /**
     *设置资金密码
     */
    @POST("/app/trade/password")
    fun findTradePwd(@Body request: RequestBody): Observable<BaseResult<Any>>


    /**
     * 注册
     */
    @POST("/app/user/register")
    fun regist(@Body request: RequestBody): Observable<BaseResult<Any>>

    /**
     * 获取g公告
     */
    @GET("/app/article/list")
    fun getMsg(): Observable<BaseResult<Msg>>
    /**
     * 获取公告详情
     */
    @GET("/app/article/detail")
    fun getMsgDetails(@Query("id" )id:String): Observable<BaseResult<Msg.MsgBean>>

    /**
     * 邀请好友
     */
    @GET("/app/user/invitation")
    fun getInviteRecord(): Observable<BaseResult<List<Invite>>>


    /**
     * 转出
     */
    @POST("/app/user/withdraw")
    fun extract(@Body request: RequestBody): Observable<BaseResult<Any>>

    /**
     * 转出记录
     */
    @GET("/app/withdraw/record")
    fun extractRecord(@Query("pageNum" )pageNum:String): Observable<BaseResult<ExtractRecord>>

    /**
     * 取消提币
     */
    @POST("/app/cancel/withdraw")
    fun cancelExtract(@Query("withdraw_id" )id:String): Observable<BaseResult<Any>>
    /**
     * 提币规则
     */
    @GET("/app/withdraw/rule")
    fun extractRule(): Observable<BaseResult<ExtractRule>>

    /**
     * 充值哦
     */
    @GET("/app/charge/address")
    fun charge(): Observable<BaseResult<Charge>>

    /**
     * 充值记录
     */
    @GET("/app/charge/record")
    fun chargeRecord(@Query("pageNum")coin: String): Observable<BaseResult<ChargeRecord>>

    /**
     * 我的资产
     */
    @GET("/app/user/assets")
    fun myAsset(): Observable<BaseResult<List<Asset>>>

    /**
     * 我的资产记录
     */
    @GET("/app/balance/detail")
    fun getAssetCoinList(@Query("coin")coin: String): Observable<BaseResult<List<AssetCoin>>>

    /**
     * 首页
     */
    @GET("/app/home/index")
    fun getHome(): Observable<BaseResult<Home>>

    /**
     * FIL服务器信息
     */
    @GET("/app/fil/miner")
    fun getMinerFilInfo(@Query("id")id: String): Observable<BaseResult<MinerFILInfo>>
    /**
     * FIL服务器租用提交
     */
    @POST("/app/fil/invest")
    fun commitMiner(@Body request: RequestBody): Observable<BaseResult<Any>>

    /**
     * 我的服务器
     */
    @GET("/app/user/miner")
    fun getMill(): Observable<BaseResult<Mill>>
    /**
     * 服务器收益明细
     */
    @GET("/app/miner/coinage")
    fun getMillEarnings(@Query("order_id" )id:String): Observable<BaseResult<List<MillEarningsList>>>

    /**
     * 我的账户信息
     */
    @GET("/app/user/info")
    fun getAccount(): Observable<BaseResult<Account>>

    /**
     * 用户协议
     */
    @GET("/app/user/agreement")
    fun getAgreementUser(): Observable<BaseResult<Agreement>>
    /**
     * 用户协议
     */
    @GET("/app/rent/agreement")
    fun getAgreementRent(): Observable<BaseResult<Agreement>>

    /**
     * 实名认证
     */
    @Multipart
    @POST("/app/user/identity")
    fun commitCertification(@QueryMap map:HashMap<String,String>,@Part files:List<MultipartBody.Part>): Observable<BaseResult<Any>>
    /**
     * 获取实名认证
     */
    @GET("/app/identity/status")
    fun getCertification(): Observable<BaseResult<Certification>>

    /**
     * 获取加速服务器信息
     */
    @GET("/app/boost/product")
    fun getAccelerateInfo(@Query("order_id") id: String): Observable<BaseResult<AccelerateInfo>>
    /**
     * 提交加速包
     */
    @POST("/app/boost/investment")
    fun commitAccelerate(@QueryMap map: HashMap<String, String>): Observable<BaseResult<Any>>

    /**
     * 查看增值服务
     */
    @GET("app/deposit/list")
    fun getValueList(): Observable<BaseResult<ValueAdd>>

    /**
     * 提取增值服务
     */
    @POST("app/deposit/withdraw")
    fun extractValue(@Query("order_id") id: String,@Query("trade_password") pwd: String): Observable<BaseResult<Any>>

}