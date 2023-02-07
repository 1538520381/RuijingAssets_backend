package com.ruijing.assets.util.using;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 将字符串渲染到客户端
 * @email 3161788646@qq.com
 * @date 2023/2/6 4:19
 */

public class WebUtil {

    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
