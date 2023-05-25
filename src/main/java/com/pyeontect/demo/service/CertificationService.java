package com.pyeontect.demo.service;

import com.nimbusds.jose.shaded.json.JSONObject;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
@Service
public class CertificationService {
    public void certifiedPhoneNumber() throws CoolsmsException {
        String api_key = "NCSMTKTOCEVTZJ3J";
        String api_secret = "PPQR9XW7NF4MKQDQQYHN2Q731NG3Q6UN";
        Message coolsms = new Message(api_key, api_secret);

        SimpleDateFormat sdf = new SimpleDateFormat("HH시 mm분");

        Date now = new Date();

        String nowTime = sdf.format(now);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", "01052261368");    // 수신전화번호
        params.put("from", "01052261368");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text","[긴급출동요청] " + nowTime + " 서울특별시 서대문구 신촌역로 22-13 1층 삼총사편의점");
        params.put("app_version", "test app 1.2"); // application name and version

        coolsms.send(params);

    }
}
