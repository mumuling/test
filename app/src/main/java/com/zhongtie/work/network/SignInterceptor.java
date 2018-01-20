package com.zhongtie.work.network;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.zhongtie.work.BuildConfig;
import com.zhongtie.work.util.Base64;
import com.zhongtie.work.util.L;
import com.zhongtie.work.util.RSASignature;
import com.zhongtie.work.util.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

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
    private static final String TAG = "SignInterceptor";
    private static final String ANDROID = "Android";
    private static final int SIGN_LENGTH = 100;


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder()
                .method(request.method(), request.body())
                .url(Http.HOST);
        if (request.body() instanceof FormBody) {

            //加密方法
            StringBuilder signSource = new StringBuilder();
            FormBody body = (FormBody) request.body();
            String actionName = getApiAction(request);

            //添加基本参数
            ArrayMap<String, String> baseData = new ArrayMap<>();
            baseData.put("action", actionName);
            baseData.put("wftime", TimeUtils.getFormatDateAll());
            baseData.put("wfversion", BuildConfig.VERSION_NAME);
            baseData.put("wfos", ANDROID);

            if (body != null) {
                //将传递过来的参数加入
                for (int i = 0, len = body.size(); i < len; i++) {
                    baseData.put(body.encodedName(i), body.value(i));
                }
            }

            //拼接字符串
            for (String key : baseData.keySet()) {
                //提取action 参数
                signSource.append(key);
                signSource.append("=");
                //遍历用base64加密参数
                String value = baseData.get(key);
                //gb2312 编码 不然服务器存入的字符为乱码
                signSource.append(Base64.encode(value.getBytes("GB2312")).trim());
                signSource.append("&");
            }

            if (BuildConfig.DEBUG) {
                L.e(TAG, baseData.toString());
            }


            signSource.delete(signSource.length() - 1, signSource.length());

            //分割数据
            List<String> signDataList = new ArrayList<>();
            for (int i = 0, len = signSource.length() / SIGN_LENGTH; i < len + 1; i++) {
                int min = Math.min(SIGN_LENGTH, signSource.length());
                if (min == 0) {
                    continue;
                }
                String signData = RSASignature.getInstance().encryptByPublicKey(signSource.substring(0, min));
                signDataList.add(signData);
                signSource.delete(0, min);
            }

            signSource = new StringBuilder();
            //每100个字符添加一个||分割
            for (String item : signDataList) {
                signSource.append(item).append("||");
            }
            signSource.delete(signSource.length() - 2, signSource.length());

            if (isUploadImage(actionName)) {
                MultipartBody.Builder newBuilder = getUploadForm(signSource, actionName, baseData);
                requestBuilder.post(newBuilder.build());
            } else {
                requestBuilder.post(new FormBody.Builder().add("ref", signSource.toString()).build());
            }
        }
        Request r = requestBuilder.build();
        return chain.proceed(r);
    }

    @NonNull
    private MultipartBody.Builder getUploadForm(StringBuilder signSource, String actionName, ArrayMap<String, String> baseData) {
        //获取图片地址
        String upload = URLDecoder.decode(baseData.get("picfile"));
        MultipartBody.Builder newBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        newBuilder.addFormDataPart("ref", signSource.toString());
        //判断是jpg 还是png
        String fileName = "UploadPic".equals(actionName) ? "file.jpg" : "file.png";
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), new File(upload));
        newBuilder.addPart(MultipartBody.Part.createFormData("picfile", fileName, requestBody));
        return newBuilder;
    }

    /**
     * 根据接口名称判断是否是上传图片接口
     *
     * @param actionName 接口名称
     * @return true 是上传 false 不是上传
     */
    private boolean isUploadImage(String actionName) {
        return "UploadPic".equals(actionName) || "UploadSignPic".equals(actionName);
    }

    @NonNull
    private String getApiAction(Request request) {
        String url = request.url().toString();
        return url.substring(url.indexOf("=") + 1, url.length());
    }
}
