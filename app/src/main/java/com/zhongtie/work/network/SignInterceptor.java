package com.zhongtie.work.network;

import android.util.Base64;

import com.zhongtie.work.util.RSASignature;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Auth:Cheek
 * date:2018.1.8
 */

class SignInterceptor implements Interceptor {
    private String privateKey = "MIICXQIBAAKBgQCpq87z7ML9yBuIXh46ZWSH2+FgxoFZCqFF/3w99AdTHz0r/aa8" +
            "FExDkhY2wJFnGjctCEYZ1CrxMJsuEWQzMjighjKPefr3eqYvzpJ0kno5dh5v/+oj" +
            "/CKZVObbFGzNLUeMYpRmOr2ghXcoJ5ePg5rGNvDy+blYVDlt0qpovR1uhwIDAQAB" +
            "AoGBAKJuDA2Ql4uEynbeQ2n8LyWw5i5TWvj3KDVuxG4689gri9BVYA2mEOyPKhz4" +
            "dTqS6kd+KD4J06JaufR8ScSJS5MyBR2FUbUEH3FjhG/XwK9dDZgNvgwX7lLvnJ8g" +
            "QWSKPiDKYiUfuKkntHnnLwpPoh3YW6Th54D/Sq6syUdR3B3BAkEA2SH5HfHiKBpj" +
            "aMeqPwDkDK/zpM8UcnudualFc5pm31TAESpQBN4bIwMe+R6F10tl9Mun9J6aoX0k" +
            "aDCmvW9OaQJBAMgK7Puxn8TPUEc3ANrXRDfopXjVp6kkf5r7Mcl+FJXItZi+MUvV" +
            "mBZbSUKsvNZzMf5fmqU3ECA0NTZYcg93F28CQF9YZbXagz6s574Y/w8PaxSPJuPV" +
            "8tqaoZXnWJH82PN0mapv9BdPGId1VWJ7HrZ9a1XlH+lww+Hpu9TsmCtS9xECQHlc" +
            "p4nLqXJtk/agmBQspWaF7l3wwVcMnZ4Y54umwjZdHk8clCu92hc2KqwYwDgaiyoY" +
            "NmoljdL72Yyu/AQF7GECQQDIVypONLrJMTUeC6wOG0nrmlm88BtaGcLvS94TZ3kw" +
            "GO7TM8FXsyklMILtvmel71uJzS4PZYC0r7+iZfMY3p7m";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder()
                .method(request.method(), request.body())
                .url(Http.HOST)
                .addHeader("content-type", "text/json; charset=utf-8");
        FormBody.Builder builder = new FormBody.Builder();
        if (request.body() instanceof FormBody) {

            //加密方法
            StringBuilder stringBuffer = new StringBuilder();
            FormBody body = (FormBody) request.body();
            String url = request.url().toString();
            String actionName = url.substring(url.indexOf("=") + 1, url.length());

            //提取action 参数
            stringBuffer.append("action")
                    .append("=")
                    .append(Base64.encodeToString(actionName.getBytes(), Base64.DEFAULT));
            //遍历用base64加密参数
            for (int i = 0, len = body.size(); i < len; i++) {
                String value = URLDecoder.decode(body.encodedValue(i), "utf-8");
                builder.add(body.encodedName(i), value);
                stringBuffer.append(body.encodedName(i))
                        .append("=")
                        .append(Base64.encodeToString(value.getBytes(), Base64.DEFAULT));
                if (i <= body.size() - 1) {
                    stringBuffer.append("&");
                }
            }
            //分割数据
            List<String> signDataList = new ArrayList<>();
            for (int i = 1, len = stringBuffer.length() / 100; i < len + 1; ) {
                int min = Math.min(i * 100, len);
                String signData = RSASignature.sign(stringBuffer.substring((i - 1) * 100, min), privateKey);
                signDataList.add(signData);
                i += min;
            }

            stringBuffer.reverse();
            //每100个字符添加一个|
            for (String item : signDataList) {
                stringBuffer.append(item).append("|");
            }
            stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());


            //重载入数据
            FormBody.Builder postForm = new FormBody.Builder();
            postForm.add("ref", stringBuffer.toString());
            postForm.add("wftime", stringBuffer.toString());
            postForm.add("wfversion", stringBuffer.toString());
            postForm.add("wfos", "Android");
            requestBuilder.post(postForm.build());
        }
        Request r = requestBuilder.build();
        return chain.proceed(r);
    }
}
