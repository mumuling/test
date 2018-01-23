
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

#-keep class com.iflytek.**{*;}
#-keepattributes Signature
#-dontwarn java.lang.invoke.*
#-dontwarn **$$Lambda$*
#
#-dontwarn com.tencent.bugly.**
#-keep public class com.tencent.bugly.**{*;}
#
#-dontwarn com.tencent.bugly.**
#-keep public class com.tencent.bugly.**{*;}
#-keep class android.support.**{*;}

-ignorewarnings
-keepattributes Signature
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*

-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
#-keep class android.support.**{*;}


# OkHttp
#-keep class okhttp3.** { *; }
#-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn com.squareup.**
-dontwarn retrofit2.**
#-keep class retrofit2.** { *; }

#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
#  **[] $VALUES;
#  public *;
#}
# RxJava2
-dontwarn io.reactivex.**
#-keep class io.reactivex.** { *; }

-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
#RxJava
-dontwarn rx.**
-keep class rx.** {*;}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# EventBus
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }


-dontwarn okio.**
-keep class okio.**  {*;}

-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keep class org.greenrobot.** { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-dontwarn sun.misc.**
-dontwarn kale.adapter.**
#-keep class kale.adapter.**{ *; }

#-keep class  com.kingyun.hawkeye.**{ *; }
-keep class  com.zhongtie.work.db.**{ *; }
-keep class  com.zhongtie.work.data.**{ *; }
-keep class  com.zhongtie.work.model.**{ *; }
#-keep class  com.zhongtie.work.network.**{ *; }
-keep class  com.zhongtie.work.widget.**{ *; }


# DBFlow
-keep class * extends com.raizlabs.android.dbflow.config.DatabaseHolder { *; }
#-keep class  com.raizlabs.android.** { *; }
-dontwarn com.raizlabs.android.**
-keep class * extends com.raizlabs.android.dbflow.config.DatabaseHolder { *; }
-keep class com.raizlabs.android.dbflow.config.GeneratedDatabaseHolder
-keep class * extends com.raizlabs.android.dbflow.config.BaseDatabaseDefinition { *; }


#激光推送
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#fresco
#-keep class com.facebook.**  {*;}
-dontwarn com.facebook.**
# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn javax.annotation.**
-dontwarn com.facebook.infer.**


#databind
-dontwarn android.databinding.**

