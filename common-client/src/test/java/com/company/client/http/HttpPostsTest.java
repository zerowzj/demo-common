package com.company.client.http;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangzhj on 2016/12/29.
 */
public class HttpPostsTest {

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        Stopwatch stopwatch = Stopwatch.createStarted();
        int count = 5000;
        final Map<String, String> params = Maps.newHashMap();
        params.put("userName", "admin");
        params.put("token", "123");
        List<Thread> tLt = Lists.newArrayList();
        for (int i = 0; i < count; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpPosts.create("http://localhost:8080/demo/list", params)
                            .bodyFormat(HttpPosts.BodyFormat.JSON)
                            .submit();
                }
            });
            t.start();

            tLt.add(t);
        }
        for (Thread t : tLt) {
            try {
                t.join();
            } catch (Exception ex) {

            }
        }
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS));
    }


    @Test
    public void test_teacher_web() {
        String url = "http://xin.jiaoshi.xk12.cn/login";
        Map<String, String> params = Maps.newHashMap();
        params.put("username", "61151099");
        params.put("password", "123456");
        Map<String, String> headers = Maps.newHashMap();
        headers.put("requestid", "111111111111");
        ResultBody result = HttpPosts.create(url, params)
                .headers(headers)
                .submit()
                .result();
//        System.out.println(JsonUtil.toJson(result));
        System.out.println(result.isOK());
        System.out.println(new String(result.getData()));
    }


    @Test
    public void test_test(){
        long start = System.currentTimeMillis();
        long useTime = 324243;
        String cost = format((useTime) / 1000.0);

        System.out.println(cost);
    }

    private String format(double value) {
        return new Formatter().format("%.3f", value).toString() + "s";
    }
}
