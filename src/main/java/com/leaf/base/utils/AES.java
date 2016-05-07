package com.leaf.base.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加密算法工具类
 * @author sunhao
 * @version 1.0
 */
public class AES {

	/**
     * 加密
     * @param content 需要加密的内容
     * @param password  加密密码
     * @return
     */
    public static byte[] encrypt(String content, String password) {
        try {
            // 密钥生成器
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // 生成强加密随机源
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            // 重新设置此随机源的种子
            secureRandom.setSeed(password.getBytes());
            // 使用提供的随机源初始化此密钥生成器，使其具有128b密钥大小
            kgen.init(128, secureRandom);
            // 生成一个密钥
            SecretKey secretKey = kgen.generateKey();
            // 返回基本编码格式的密钥，如果此密钥不支持编码，则返回null
            byte[] enCodeFormat = secretKey.getEncoded();
            // 根据给定的字节数组构造一个密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器“算法/模式/填充”或“算法”
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            // 以加密模式初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
        } catch (NoSuchPaddingException e) {
                e.printStackTrace();
        } catch (InvalidKeyException e) {
                e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
        } catch (BadPaddingException e) {
                e.printStackTrace();
        }
        return null;
    }

    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
      */
     public static byte[] decrypt(byte[] content, String password) {
         try {
              KeyGenerator kgen = KeyGenerator.getInstance("AES");
              SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
              secureRandom.setSeed(password.getBytes());
              kgen.init(128, secureRandom);
              SecretKey secretKey = kgen.generateKey();
              byte[] enCodeFormat = secretKey.getEncoded();
              SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
              Cipher cipher = Cipher.getInstance("AES");
              cipher.init(Cipher.DECRYPT_MODE, key);
              byte[] result = cipher.doFinal(content);
              return result;
         } catch (NoSuchAlgorithmException e) {
                 e.printStackTrace();
         } catch (NoSuchPaddingException e) {
                 e.printStackTrace();
         } catch (InvalidKeyException e) {
                 e.printStackTrace();
         } catch (IllegalBlockSizeException e) {
                 e.printStackTrace();
         } catch (BadPaddingException e) {
                 e.printStackTrace();
         }
         return null;
     }

     /**
        * 将二进制转换成16进制
        * @param buf
        * @return
        */
      public static String parseByte2HexStr(byte buf[]) {
          StringBuffer sb = new StringBuffer();
          for (int i = 0; i < buf.length; i++) {
                  String hex = Integer.toHexString(buf[i] & 0xFF);
                  if (hex.length() == 1) {
                          hex = '0' + hex;
                  }
                  sb.append(hex.toUpperCase());
          }
          return sb.toString();
     }
        
      /**
        * 将16进制转换为二进制
        * @param hexStr
        * @return 
        */   
       public static byte[] parseHexStr2Byte(String hexStr) {
               if (hexStr.length() < 1)
                       return null;
               byte[] result = new byte[hexStr.length()/2];
               for (int i = 0;i< hexStr.length()/2; i++) {
                       int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
                       int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
                       result[i] = (byte) (high * 16 + low);
               }
               return result;
       }
         
       /** 
          * 加密 
          * @param content 需要加密的内容
          * @param password  加密密码
          * @return
          */   
         public static byte[] encrypt2(String content, String password) {
             try {
                     SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
                     Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
                     byte[] byteContent = content.getBytes("utf-8");
                     cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
                     byte[] result = cipher.doFinal(byteContent);
                     return result; // 加密
             } catch (NoSuchAlgorithmException e) {
                     e.printStackTrace();
             } catch (NoSuchPaddingException e) {
                     e.printStackTrace();
             } catch (InvalidKeyException e) {
                     e.printStackTrace();
             } catch (UnsupportedEncodingException e) {
                     e.printStackTrace();
             } catch (IllegalBlockSizeException e) {
                     e.printStackTrace();
             } catch (BadPaddingException e) {
                     e.printStackTrace();
             }
             return null;
         }
       
     public static void main(String[] args) {

        String content = "666666";  
        String password = "aisidi";
        //加密
        System.out.println("加密前：" + content);
        byte[] encryptResult = encrypt(content, password);
        String encryptResultStr = parseByte2HexStr(encryptResult);
        System.out.println("加密后：" + encryptResultStr);
        //解密
         encryptResultStr = "48417A79C5E02319E17C47DA8615D9A3";
        byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
        byte[] decryptResult = decrypt(decryptFrom,password);
        System.out.println("解密后：" + new String(decryptResult));

        System.out.println(RandomStringUtil.randomNumeric(6));
   }
}
