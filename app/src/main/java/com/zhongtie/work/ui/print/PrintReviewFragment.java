package com.zhongtie.work.ui.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.data.Result;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.SyncApi;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.util.AppUtil;
import com.zhongtie.work.util.TextUtil;

import io.reactivex.functions.Consumer;

import static com.zhongtie.work.ui.print.PrintEventActivity.KEY_EVENT_ID;
import static com.zhongtie.work.ui.print.PrintEventActivity.KEY_PRINT_TYPE;

/**
 * 打印预览界面
 * date:2018.1.22
 *
 * @author Chaek
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class PrintReviewFragment extends BaseFragment {

    public static final String PRINT_SERVICE = "com.hp.android.printservice";
    private WebView mWebView;
    private TextView mPrint;
    private FrameLayout mFullWebView;

    private int mPrintType;
    private int mPrintEventId;
    private PrintEventActivity printEventActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PrintEventActivity) {
            printEventActivity = (PrintEventActivity) context;
        }
    }

    @Override
    public int getLayoutViewId() {
        mPrintEventId = getArguments().getInt(KEY_EVENT_ID);
        mPrintType = getArguments().getInt(KEY_PRINT_TYPE);
        return R.layout.print_event_fragment;
    }


    @Override
    public void initView() {
        mPrint = (TextView) findViewById(R.id.print);
        mFullWebView = (FrameLayout) findViewById(R.id.fullWebView);
        mWebView = new WebView(getAppContext().getApplicationContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(layoutParams);
        mFullWebView.addView(mWebView);

        addWebViewSettings(mWebView);
        mWebView.setWebViewClient(new ZTWebViewClient());

        mPrint.setOnClickListener(view -> {
            createWevViewPrint();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createWevViewPrint() {
        if (!AppUtil.isPkgInstalled(PRINT_SERVICE)) {
            printEventActivity.showDownLoadHPPrintServer();
        } else {
            PrintDocumentAdapter printDocumentAdapter = mWebView.createPrintDocumentAdapter();
            PrintManager printManger = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
            if (printManger != null) {
                String jobName = getActivity().getString(R.string.app_name) + " Document";
                printManger.print(jobName, printDocumentAdapter, null);
            } else {
                showToast(getString(R.string.print_fail));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        mWebView.removeAllViews();
        mFullWebView.removeAllViews();
    }

    @Override
    protected void initData() {
        getPrintUrl();
    }

    private void getPrintUrl() {
        initLoading();
        addDispose(Http.netServer(SyncApi.class)
                .getPrintUrl(mPrintType, mPrintEventId)
                .compose(Network.convertIO())
                .subscribe(s -> {
                    if (TextUtil.isEmpty(s)) {
                        mWebView.loadUrl("http://api.023ztjs.com/apiv2/index.php?action=eventPrint&%20timestamp=1511945178&os=j&devicename=PC&sysversion=windows&appversion=v1.0&deviceid=etchris.com&debug=1&userid=572&eventid=1132");
                    } else {
                        mWebView.loadUrl(s);
                    }
                }, throwable -> {
                    initFail();
                }));
    }

    /**
     * 设置WebView属性
     *
     * @param mWebView WebView
     */
    public void addWebViewSettings(WebView mWebView) {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSaveFormData(false);
        settings.setAppCacheEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setLoadsImagesAutomatically(true);
    }

    private class ZTWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            initLoading();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            initFail();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            initSuccess();
        }
    }

}