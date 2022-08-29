package com.company.project.util;

import com.alibaba.fastjson.JSON;
import com.company.project.constent.Code;
import com.company.project.constent.ProjectConstant;
import com.company.project.exception.BusinessException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.BeanUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author :Libi
 * @version :1.0
 * @date :4/8/22 2:11 PM
 * 生成和解析就问他
 */
@Slf4j
public class JwtUtil {

    /**
     * 生成jwtToken
     *
     * @param tokenInfo     jwt中包含的信息
     * @param timeoutMillis 超时时间，单位毫秒
     * @return
     */
    public static String createJwtToken(Object tokenInfo, Long timeoutMillis) {

        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = JSON.parseObject(JSON.toJSONString(tokenInfo), HashMap.class);
        BeanUtils.copyProperties(tokenInfo, claims);

        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
        // 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generalKey();

        // 下面就是在为payload添加各种标准声明和私有声明了
        // 这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                // iat: jwt的签发时间
                .setIssuedAt(now)
//                // issuer：jwt签发人
//                .setIssuer(issuer)
//                // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
//                .setSubject(subject)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);

        // 设置过期时间
        if (timeoutMillis >= 0) {
            long expMillis = nowMillis + timeoutMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解析Jwt
     *
     * @param jwt   jwt字符串
     * @param clazz 目标类型的class对象
     * @return
     * @throws Exception
     */
    public static <T> T parseJwt(String jwt, Class<T> clazz) {
        try {
            //签名秘钥，和生成的签名的秘钥一模一样
            SecretKey key = generalKey();
            //得到DefaultJwtParser
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt).getBody();
            log.info("获取token中信息：{}", JSON.toJSONString(claims));
            return JSON.parseObject(JSON.toJSONString(claims), clazz);
        } catch (ExpiredJwtException e) {
            log.error("token过期。msg：{}", e.getMessage());
            throw new BusinessException(Code.AUTH_TOKEN_EXPIRED);
        } catch (MalformedJwtException e) {
            log.error("token解析失败。msg：{}", e.getMessage());
            throw new BusinessException(Code.AUTH_TOKEN_VERIFY_ERROR);
        }
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    private static SecretKey generalKey() {
        String stringKey = ProjectConstant.JWT_SECRET;
        // 本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
}
