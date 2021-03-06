package com.company.client.http;

import com.company.util.JsonUtil;
import com.google.common.collect.Lists;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * 实体
 *
 * @author wangzhj
 */
abstract class Entitys {

    /**
     * 生成表单实体
     *
     * @param params
     * @param charset
     * @return HttpEntity
     */
    public static HttpEntity createUrlEncodedFormEntity(Map<String, String> params, Charset charset) {
        List<NameValuePair> pairLt = pairs(params);
        UrlEncodedFormEntity formEntity = null;
        try {
            formEntity = new UrlEncodedFormEntity(pairLt, charset);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return formEntity;
    }


    /**
     * 生成Json实体
     *
     * @param params
     * @param charset
     * @return HttpEntity
     */
    public static HttpEntity createJsonEntity(Map<String, String> params, Charset charset) {
        String json = JsonUtil.toJson(params);
        StringEntity stringEntity = new StringEntity(json, charset.toString());
        stringEntity.setContentEncoding(charset.toString());
        stringEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());

        return stringEntity;
    }

    /**
     * 生成Multipart实体
     *
     * @param params
     * @param files
     * @return HttpEntity
     */
    public static HttpEntity createMultipartEntity(Map<String, String> params, Map<String, byte[]> files) {

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        //文件
        if(files != null){
            for (Map.Entry<String, byte[]> entry : files.entrySet()) {
                builder.addBinaryBody(entry.getKey(), entry.getValue(), ContentType.DEFAULT_BINARY, entry.getKey());
            }
        }
        //参数
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addTextBody(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity httpEntity = builder.build();
        return httpEntity;
    }

    public static List<NameValuePair> pairs(Map<String, String> params) {
        List<NameValuePair> pairLt = Lists.newArrayList();
        if (params == null) {
            return pairLt;
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            pairLt.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return pairLt;
    }
}
