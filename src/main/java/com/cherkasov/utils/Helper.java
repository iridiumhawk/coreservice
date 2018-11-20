package com.cherkasov.utils;

import com.cherkasov.Configuration;
import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestOperations;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

public class Helper {
    public static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }

    public static HttpHeaders createHeaders(String username, String password) {

        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

    public static String getDeviceName(String controllerDevice) {
        //e8639832111cffa939ed53e765ecb17d::ZWayVDev_zway_5-0-49-1
        String[] split = controllerDevice.split("::");
        if (split.length > 1) {
            return split[1];
        }
        return "";
    }

    public static String getControllerName(String controllerDevice) {
        String[] split = controllerDevice.split("::");
        if (split.length > 1) {
            return split[0];
        }
        return controllerDevice;
    }


}
