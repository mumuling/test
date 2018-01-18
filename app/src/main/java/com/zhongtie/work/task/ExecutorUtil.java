package com.zhongtie.work.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程管理类
 * Date: 2017/12/1
 *
 * @author Chaek
 */
public class ExecutorUtil {

    private static ExecutorService poolExecutor;
    private static ExecutorService fethUserListExecutor;
    /**
     * 单个任务 按顺序执行 线程池
     * <p>
     * 要确保 task执行任务全都是耗时 异步任务会导致不可预见的结果例如重复提交
     */
    public static ExecutorService newSingleThreadExecutor() {
        if (poolExecutor == null) {
            poolExecutor = Executors.newSingleThreadExecutor();
        }
        return poolExecutor;

    }

    /**
     * 单个任务 按顺序执行 线程池
     * <p>
     * 要确保 task执行任务全都是耗时 异步任务会导致不可预见的结果例如重复提交
     */
    public static ExecutorService newFethUserListExecutorExecutor() {
        if (fethUserListExecutor == null) {
            fethUserListExecutor = Executors.newSingleThreadExecutor();
        }
        return fethUserListExecutor;

    }
}
