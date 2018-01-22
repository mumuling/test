package com.zhongtie.work.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;

import okhttp3.OkHttpClient;

import static com.zhongtie.work.app.Constant.IMAGE_CACHE_PATH;

/**
 * 图片加载初始化
 */
public class ImageConfigFactory {
    private static final String IMAGE_PIPELINE_CACHE_DIR = "imagepipeline_cache";
    private static ImagePipelineConfig sOkHttpImagePipelineConfig;
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
    private static final int MAX_DISK_CACHE_SIZE = 300 * ByteConstants.MB;
    private static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 5;

    public static ImagePipelineConfig getImagePipelineConfig(Context context) {
        return getOkHttpImagePipelineConfig(context);
    }

    public static ImagePipelineConfig getOkHttpImagePipelineConfig(Context context) {
        if (sOkHttpImagePipelineConfig == null) {
            ImagePipelineConfig.Builder configBuilder = OkHttpImagePipelineConfigFactory.newBuilder(context, new OkHttpClient());
            configureCaches(configBuilder, context);
            //设置压缩图片
            configBuilder.setDownsampleEnabled(true);
            configBuilder.setBitmapsConfig(Bitmap.Config.ARGB_8888);
            sOkHttpImagePipelineConfig = configBuilder.build();

        }
        return sOkHttpImagePipelineConfig;
    }


    private static void configureCaches(ImagePipelineConfig.Builder configBuilder, Context context) {
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
                Integer.MAX_VALUE,                     // Max entries in the cache
                MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
                Integer.MAX_VALUE,                     // Max length of eviction queue
                Integer.MAX_VALUE);                    // Max cache entry size
        configBuilder.setBitmapMemoryCacheParamsSupplier(
                () -> bitmapCacheParams)
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(context)
                        .setBaseDirectoryPath(getExternalCacheDir(context))
                        .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
                        .setMaxCacheSize(MAX_DISK_CACHE_SIZE)
                        .build());
    }

    private static File getExternalCacheDir(final Context context) {
        return new File(IMAGE_CACHE_PATH);
    }

}