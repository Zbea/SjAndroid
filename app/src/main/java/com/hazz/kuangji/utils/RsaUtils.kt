package com.hazz.kuangji.utils

import android.text.TextUtils
import android.util.Base64
import java.io.*

import java.math.BigInteger
import java.nio.charset.Charset
import java.security.*
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAPublicKeySpec
import java.security.spec.X509EncodedKeySpec

import javax.crypto.Cipher

object RsaUtils {

    val key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQgskAWE7d32z5wOeNBmUsB+5W"+
            "kvzhSbhI8f1r47bmXS8y5nY7tTJoXichQqPpsKu0sVMkJRC58ltGOgOY1WnteNHa"+
            "v7iNmKEUvuXu+phaQSOtgTxEhJrgh4BPnp8ocJ2s1STygTyjrm9isF4TcDg6HRj6"+
            "LbNRJKrix5CbfuMD6wIDAQAB"

    private val RSA = "RSA"

    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength 密钥长度，范围：512～2048<br></br>
     * 一般1024
     * @return
     */
    @JvmOverloads
    fun generateRSAKeyPair(keyLength: Int = 1024): KeyPair? {
        try {
            val kpg = KeyPairGenerator.getInstance(RSA)
            kpg.initialize(keyLength)
            return kpg.genKeyPair()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 用公钥加密 <br></br>
     * 每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data   需加密数据的byte数据
     * @param pubKey 公钥
     * @return 加密后的byte型数据
     */
    fun encryptData(data: ByteArray, pubKey: PublicKey): ByteArray? {
        try {
            val cipher = //Cipher.getInstance(RSA);
                    Cipher.getInstance("RSA/ECB/PKCS1Padding")
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, pubKey)
            // 传入编码数据并返回编码结果
            return cipher.doFinal(data)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 用私钥解密
     *
     * @param encryptedData 经过encryptedData()加密返回的byte数据
     * @param privateKey    私钥
     * @return
     */
    fun decryptData(encryptedData: ByteArray, privateKey: PrivateKey): ByteArray? {
        try {
            val cipher = //Cipher.getInstance(RSA);
                    Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.DECRYPT_MODE, privateKey)
            return cipher.doFinal(encryptedData)
        } catch (e: Exception) {
            return null
        }

    }

    /**
     * 通过公钥byte[](publicKey.getEncoded())将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun getPublicKey(keyBytes: ByteArray): PublicKey {
        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(RSA)
        return keyFactory.generatePublic(keySpec)
    }

    /**
     * 通过私钥byte[]将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun getPrivateKey(keyBytes: ByteArray): PrivateKey {
        val keySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(RSA)
        return keyFactory.generatePrivate(keySpec)
    }

    /**
     * 使用N、e值还原公钥
     *
     * @param modulus
     * @param publicExponent
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun getPublicKey(modulus: String, publicExponent: String): PublicKey {
        val bigIntModulus = BigInteger(modulus)
        val bigIntPrivateExponent = BigInteger(publicExponent)
        val keySpec = RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent)
        val keyFactory = KeyFactory.getInstance(RSA)
        return keyFactory.generatePublic(keySpec)
    }

    /**
     * 使用N、d值还原私钥
     *
     * @param modulus
     * @param privateExponent
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun getPrivateKey(modulus: String, privateExponent: String): PrivateKey {
        val bigIntModulus = BigInteger(modulus)
        val bigIntPrivateExponent = BigInteger(privateExponent)
        val keySpec = RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent)
        val keyFactory = KeyFactory.getInstance(RSA)
        return keyFactory.generatePrivate(keySpec)
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    @Throws(Exception::class)
    fun loadPublicKey(publicKeyStr: String): PublicKey {
        try {
            val buffer = Base64.decode(publicKeyStr, Base64.DEFAULT)
            val keyFactory = KeyFactory.getInstance(RSA)
            val keySpec = X509EncodedKeySpec(buffer)
            return keyFactory.generatePublic(keySpec) as RSAPublicKey
        } catch (e: NoSuchAlgorithmException) {
            throw Exception("无此算法")
        } catch (e: InvalidKeySpecException) {
            throw Exception("公钥非法")
        } catch (e: NullPointerException) {
            throw Exception("公钥数据为空")
        }

    }

    @Throws(Exception::class)
    fun jiami(pwd: String): String {
        val publicKey = loadPublicKey(key)
        val encryptData = encryptData(pwd.toByteArray(), publicKey)
        val encode = Base64.encode(encryptData, Base64.NO_WRAP)
        return String(encode, Charset.forName("utf-8"))
    }



    /**
     * 从字符串中加载私钥<br></br>
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun loadPrivateKey(privateKeyStr: String): PrivateKey {
        try {
            val buffer = Base64.decode(privateKeyStr, Base64.DEFAULT)
            // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            val keySpec = PKCS8EncodedKeySpec(buffer)
            val keyFactory = KeyFactory.getInstance(RSA)
            return keyFactory.generatePrivate(keySpec) as RSAPrivateKey
        } catch (e: NoSuchAlgorithmException) {
            throw Exception("无此算法")
        } catch (e: InvalidKeySpecException) {
            throw Exception("私钥非法")
        } catch (e: NullPointerException) {
            throw Exception("私钥数据为空")
        }

    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    @Throws(Exception::class)
    fun loadPublicKey(`in`: InputStream): PublicKey {
        try {
            return loadPublicKey(readKey(`in`))
        } catch (e: IOException) {
            throw Exception("公钥数据流读取错误")
        } catch (e: NullPointerException) {
            throw Exception("公钥输入流为空")
        }

    }

    /**
     * 从文件中加载私钥
     *
     * @param in 私钥文件流
     * @return 是否成功
     * @throws Exception
     */
    @Throws(Exception::class)
    fun loadPrivateKey(`in`: InputStream): PrivateKey {
        try {
            return loadPrivateKey(readKey(`in`))
        } catch (e: IOException) {
            throw Exception("私钥数据读取错误")
        } catch (e: NullPointerException) {
            throw Exception("私钥输入流为空")
        }

    }

    /**
     * 读取密钥信息
     *
     * @param in
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun readKey(`in`: InputStream): String {
        val br = BufferedReader(InputStreamReader(`in`))
        var readLine: String? = null
        val sb = StringBuilder()
        while ((readLine ==br.readLine()) != null) {
            if (readLine!![0] == '-') {
                continue
            } else {
                sb.append(readLine)
                sb.append('\r')
            }
        }

        return sb.toString()
    }

    /**
     * 打印公钥信息
     *
     * @param publicKey
     */
    fun printPublicKeyInfo(publicKey: PublicKey) {
        val rsaPublicKey = publicKey as RSAPublicKey
        println("----------RSAPublicKey----------")
        println("Modulus.length=" + rsaPublicKey.modulus.bitLength())
        println("Modulus=" + rsaPublicKey.modulus.toString())
        println("PublicExponent.length=" + rsaPublicKey.publicExponent.bitLength())
        println("PublicExponent=" + rsaPublicKey.publicExponent.toString())
    }

    fun printPrivateKeyInfo(privateKey: PrivateKey) {
        val rsaPrivateKey = privateKey as RSAPrivateKey
        println("----------RSAPrivateKey ----------")
        println("Modulus.length=" + rsaPrivateKey.modulus.bitLength())
        println("Modulus=" + rsaPrivateKey.modulus.toString())
        println("PrivateExponent.length=" + rsaPrivateKey.privateExponent.bitLength())
        println("PrivatecExponent=" + rsaPrivateKey.privateExponent.toString())

    }


    //    /**
    //     * 使用公钥加密
    //     *
    //     * @param content
    //     * @return
    //     */
    //    public static String encryptByPublic(Context context, String content) {
    //        try {
    //            String pubKey = key;
    //            PublicKey publicKey = getPublicKeyFromX509(ALGORITHM, pubKey);
    //            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    //            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    //            byte[] plaintext = content.getBytes();
    //            byte[] output = cipher.doFinal(plaintext);
    //
    //            String str = Base64.encodeToString(output, Base64.DEFAULT);
    //            return str;
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //            return null;
    //        }
    //    }

    /**
     * 得到公钥
     *
     * @param algorithm
     * @param bysKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    @Throws(NoSuchAlgorithmException::class, Exception::class)
    private fun getPublicKeyFromX509(algorithm: String,
                                     bysKey: String): PublicKey {
        val decodeKey = Base64.decode(bysKey, Base64.DEFAULT)
        val x509 = X509EncodedKeySpec(decodeKey)
        val keyFactory = KeyFactory.getInstance(algorithm)
        return keyFactory.generatePublic(x509)
    }

    fun imageToBase64(path: String): String? {
        if (TextUtils.isEmpty(path)) {
            return null
        }
        var `is`: InputStream? = null
        var data: ByteArray? = null
        var result: String? = null
        try {
            `is` = FileInputStream(path)
            //创建一个字符流大小的数组。
            data = ByteArray(`is`.available())
            //写入数组
            `is`.read(data)
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (null != `is`) {
                try {
                    `is`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        }
        return result
    }

}
/**
 * 随机生成RSA密钥对(默认密钥长度为1024)
 *
 * @return
 */
