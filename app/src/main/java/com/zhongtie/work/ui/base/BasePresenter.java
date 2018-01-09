/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhongtie.work.ui.base;

import io.reactivex.disposables.Disposable;

public interface BasePresenter<T extends BaseView> {

    /**
     * Binds mPresenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this mPresenter
     */
    void takeView(T view);


    /**
     * Drops the reference to the view when destroyed
     */
    void dropView();

    /**
     * 销毁
     */
    void destroy();

    void addDispose(Disposable disposable);

}
