package com.zhongtie.work.network;

import android.support.v4.util.ArrayMap;

import com.zhongtie.work.util.Base64;
import com.zhongtie.work.util.RSASignature;
import com.zhongtie.work.util.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Auth:Cheek
 * date:2018.1.8
 */

class SignInterceptor implements Interceptor {

//    private String privateKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCpq87z7ML9yBuIXh46ZWSH2+Fg" +
//        "xoFZCqFF/3w99AdTHz0r/aa8FExDkhY2wJFnGjctCEYZ1CrxMJsuEWQzMjighjKP" +
//        "efr3eqYvzpJ0kno5dh5v/+oj/CKZVObbFGzNLUeMYpRmOr2ghXcoJ5ePg5rGNvDy" +
//        "+blYVDlt0qpovR1uhwIDAQAB";


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder()
                .method(request.method(), request.body())
                .url(Http.HOST);
        if (request.body() instanceof FormBody) {

            //加密方法
            StringBuilder stringBuffer = new StringBuilder();
            FormBody body = (FormBody) request.body();
            String url = request.url().toString();
            String actionName = url.substring(url.indexOf("=") + 1, url.length());


            ArrayMap<String, String> baseData = new ArrayMap<>();
            baseData.put("action", actionName);
            baseData.put("wftime", TimeUtils.getFormatDateAll());
            baseData.put("wfversion", "V1.0");
            baseData.put("wfos", "Android");

            for (int i = 0, len = body.size(); i < len; i++) {
                baseData.put(body.encodedName(i), body.encodedValue(i));
            }


            for (String key : baseData.keySet()) {
                String value = baseData.get(key);
                //提取action 参数
                stringBuffer.append(key);
                stringBuffer.append("=");
                stringBuffer.append(Base64.encode(value.getBytes()).trim());
                //遍历用base64加密参数
                stringBuffer.append("&");
            }

            stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
            //分割数据
            List<String> signDataList = new ArrayList<>();
            for (int i = 0, len = stringBuffer.length() / 100; i < len + 1; i++) {
                int min = Math.min(100, stringBuffer.length());
                String signData = RSASignature.getInstance().sign(stringBuffer.substring(0, min));
                signDataList.add(signData);
                stringBuffer.delete(0, min);
            }

            stringBuffer = new StringBuilder(new StringBuilder());
            //每100个字符添加一个|
            for (String item : signDataList) {
                stringBuffer.append(item).append("||");
            }
            stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length());

            if ("UploadPic".equals(actionName) || "UploadSignPic".equals(actionName)) {
                String upload = URLDecoder.decode(baseData.get("picfile"));
                MultipartBody.Builder newBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                newBuilder.addFormDataPart("ref", stringBuffer.toString());
                String fileName = "UploadPic".equals(actionName) ? "file.jpg" : "file.png";
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), new File(upload));
                MultipartBody.Part part = MultipartBody.Part.createFormData("picfile", fileName, requestBody);
                newBuilder.addPart(part);
                requestBuilder.post(newBuilder.build());
            } else {
                FormBody.Builder postForm = new FormBody.Builder();
                postForm.add("ref", stringBuffer.toString());
                requestBuilder.post(postForm.build());
            }
        }
        Request r = requestBuilder.build();
        return chain.proceed(r);
    }
}
