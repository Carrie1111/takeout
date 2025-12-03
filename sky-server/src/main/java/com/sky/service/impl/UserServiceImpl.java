package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    // 微信服务接口地址，设为常量
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    public User wxLogin(UserLoginDTO userLoginDTO) {
        // 调用微信接口服务，获得当前用户openid
        String openid = getOpenid(userLoginDTO.getCode());

        // 判断openid用户是否为空，空则登录失败
        if(openid == null){
            log.info("传入用户为空");
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        // 判断当前用户是否为外卖程序新用户，是则自动注册
        User user = userMapper.getUserByOpenId(openid);
        if(user == null){
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insertUser(user);
        }
        // 返回用户对象
        return user;
    }

    /**
     * 调用微信接口服务，获得当前用户openid
     * @param code
     * @return
     */
//    private String getOpenid(String code) {
//        // 调用微信接口服务，获得当前用户openid
//        Map<String, String> map = new HashMap();
//        map.put("appid", weChatProperties.getAppid());
//        map.put("secret", weChatProperties.getSecret());
//        map.put("js_code", code);
//        map.put("grant_type", "authorization_code");
//
//        String json = HttpClientUtil.doGet(WX_LOGIN, map);
//
//        JSONObject jsonObject = JSON.parseObject(json);
//        String openid = jsonObject.getString("openid");
//        return openid;
//    }
    @Value("${sky.wechat.appid}")
    private String appid;

    @Value("${sky.wechat.secret}")
    private String secret;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    public String getOpenid(String code) {
        if (code == null || code.isEmpty()) {
            log.warn("前端传入 code 为空");
            return null;
        }

        String url = String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                appid, secret, code
        );

        log.info("调用微信接口 URL: {}", url);

        try {
            String response = restTemplate.getForObject(url, String.class);
            log.info("微信接口返回: {}", response);

            JsonNode jsonNode = objectMapper.readTree(response);

            if (jsonNode.has("errcode")) {
                log.error("微信接口返回错误: errcode={}, errmsg={}",
                        jsonNode.get("errcode").asText(),
                        jsonNode.get("errmsg").asText());
                return null;
            }

            if (jsonNode.has("openid")) {
                String openid = jsonNode.get("openid").asText();
                log.info("获取到 openid: {}", openid);
                return openid;
            } else {
                log.warn("返回 JSON 没有 openid 字段");
                return null;
            }

        } catch (Exception e) {
            log.error("调用微信接口异常", e);
            return null;
        }
    }
}
