package com.zhongtie.work.network;

import android.support.annotation.Nullable;


import com.orhanobut.logger.Logger;
import com.zhongtie.work.BuildConfig;
import com.zhongtie.work.util.CharacterHandler;
import com.zhongtie.work.util.ZipHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created with Android Studio.
 * Authour：Eric_chan
 * Date：2017/8/18 17:29
 * Des：
 */
public class RequestInterceptor implements Interceptor {
    private final  RequestInterceptor.Level printLevel;

    public enum Level {
        NONE,       //不打印log
        REQUEST,    //只打印请求信息
        RESPONSE,   //只打印响应信息
        ALL         //所有数据全部打印
    }

    public RequestInterceptor() {
        if (!BuildConfig.DEBUG) {
            printLevel = Level.NONE;
        } else {
            printLevel = Level.ALL;
        }
    }

    public RequestInterceptor(@Nullable Level level) {
        if (level == null) {
            printLevel = Level.ALL;
        } else {
            printLevel = level;

        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        boolean logRequest = printLevel == Level.ALL || (printLevel != Level.NONE && printLevel == Level.REQUEST);

        if (logRequest) {
            boolean hasRequestBody = request.body() != null;
            //打印请求信息
            Logger.t("请求响应拦截").e(getTag(request, "请求信息") + " Params : 「 %s 」%nConnection : 「 %s 」%nHeaders : %n「 %s 」"
                    , hasRequestBody ? parseParams(request.newBuilder().build().body()) : "Null"
                    , chain.connection()
                    , request.headers());
        }

        boolean logResponse = printLevel == Level.ALL || (printLevel != Level.NONE && printLevel == Level.RESPONSE);

        long t1 = logResponse ? System.nanoTime() : 0;
        Response originalResponse;
        try {
            originalResponse = chain.proceed(request);
        } catch (Exception e) {
            Logger.t("请求响应拦截").e("Http Error: %s", e);
            throw e;
        }
        long t2 = logResponse ? System.nanoTime() : 0;

        if (logResponse) {
            String bodySize = originalResponse.body().contentLength() != -1 ? originalResponse.body().contentLength() + "-byte" : "unknown-length";
            //打印响应时间以及响应头
            Logger.t("请求响应拦截").e(getTag(request, "响应信息") + " Received response in [ %d-ms ] , [ %s ]%n [%s]"
                    , TimeUnit.NANOSECONDS.toMillis(t2 - t1)
                    , bodySize
                    , originalResponse.headers());
        }

        //打印响应结果
        String bodyString = printResult(request, originalResponse.newBuilder().build(), logResponse);

        return originalResponse;
    }

    /**
     * 打印响应结果
     *
     * @param request
     * @param response
     * @param logResponse
     * @return
     * @throws IOException
     */
    @Nullable
    private String printResult(Request request, Response response, boolean logResponse) throws IOException {
        //读取服务器返回的结果
        ResponseBody responseBody = response.body();
        String bodyString = null;
        if (isParseable(responseBody.contentType())) {
            try {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                //获取content的压缩类型
                String encoding = response
                        .headers()
                        .get("Content-Encoding");

                Buffer clone = buffer.clone();


                //解析response content
                bodyString = parseContent(responseBody, encoding, clone);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (logResponse) {
                Logger.t("请求响应拦截").e("响应结果 %n %s", isJson(responseBody.contentType()) ?
                        CharacterHandler.jsonFormat(bodyString) : isXml(responseBody.contentType()) ?
                        CharacterHandler.xmlFormat(bodyString) : bodyString);
            }

        } else {
            if (logResponse) {
                Logger.t("请求响应拦截").e("响应结果 %s ", "This result isn't parsed");

            }
        }
        return bodyString;
    }


    private String getTag(Request request, String tag) {
        return String.format("%s -----> [请求方式 ：%s] 「请求地址： %s 」%n", tag, request.method(), request.url().toString());
    }


    /**
     * 解析服务器响应的内容
     *
     * @param responseBody
     * @param encoding
     * @param clone
     * @return
     */
    private String parseContent(ResponseBody responseBody, String encoding, Buffer clone) {
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {//content使用gzip压缩
            return ZipHelper.decompressForGzip(clone.readByteArray(), convertCharset(charset));//解压
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {//content使用zlib压缩
            return ZipHelper.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset));//解压
        } else {//content没有被压缩
            return clone.readString(charset);
        }
    }

    public static String parseParams(RequestBody body) throws UnsupportedEncodingException {
        if (isParseable(body.contentType())) {
            try {
                Buffer requestbuffer = new Buffer();
                body.writeTo(requestbuffer);
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
                return URLDecoder.decode(requestbuffer.readString(charset), convertCharset(charset));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "This params isn't parsed";
    }

    public static boolean isParseable(MediaType mediaType) {
        if (mediaType == null) return false;
        return mediaType.toString().toLowerCase().contains("text")
                || isJson(mediaType) || isForm(mediaType)
                || isHtml(mediaType) || isXml(mediaType);
    }

    public static boolean isJson(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("json");
    }

    public static boolean isXml(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("xml");
    }

    public static boolean isHtml(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("html");
    }

    public static boolean isForm(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("x-www-form-urlencoded");
    }

    public static String convertCharset(Charset charset) {
        String s = charset.toString();
        int i = s.indexOf("[");
        if (i == -1) {
            return s;
        }
        return s.substring(i + 1, s.length() - 1);
    }
}