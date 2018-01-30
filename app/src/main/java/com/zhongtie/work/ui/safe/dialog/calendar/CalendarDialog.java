package com.zhongtie.work.ui.safe.dialog.calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;
import com.zhongtie.work.R;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.enums.CalendarType;
import com.zhongtie.work.model.EventCountData;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.SafeApi;
import com.zhongtie.work.ui.base.BaseDialog;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class CalendarDialog extends BaseDialog implements OnSelectDateListener, View.OnClickListener {

    public static final int SAFE_EVENT_COUNT = 1;
    public static final int SAFE_PUNISH_COUNT = 2;

    private TextView mCancel;
    private TextView mFinish;
    private MonthPager mCalendarView;
    private ArrayList<Calendar> currentCalendars = new ArrayList<>();

    private CalendarViewAdapter mCalendarViewAdapter;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private CalendarDate currentDate;

    private AppCompatImageView mUp;
    private TextView mCalendarDate;
    private AppCompatImageView mNext;

    private OnSelectDateCallback mOnSelectDateCallback;
    private HashMap<String, String> mEventCountList = new HashMap<>();

    private ArrayMap<String, Boolean> monthMar = new ArrayMap<>();

    @CalendarType
    private int mCalendarTyp;

    public CalendarDialog(@NonNull Context context, OnSelectDateCallback onSelectDateCallback, @CalendarType int calendarTyp) {
        super(context);
        mOnSelectDateCallback = onSelectDateCallback;
        mCalendarTyp = calendarTyp;
    }

    public CalendarDialog(@NonNull Context context, OnSelectDateCallback onSelectDateCallback) {
        this(context, onSelectDateCallback, SAFE_EVENT_COUNT);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_calendar);
        mCancel = findViewById(R.id.cancel);
        mFinish = findViewById(R.id.finish);
        mCalendarView = findViewById(R.id.calendar_view);

        mCalendarView.setViewHeight(com.zhongtie.work.util.ViewUtils.dip2px(232));
        mUp = findViewById(R.id.up);
        mCalendarDate = findViewById(R.id.calendar_date);
        mNext = findViewById(R.id.next);
        mUp.setOnClickListener(this);
        mNext.setOnClickListener(this);
        initCalendarPage();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.up:
                mCalendarView.setCurrentItem(mCalendarView.getCurrentPosition() - 1);
                break;
            case R.id.next:
                mCalendarView.setCurrentItem(mCalendarView.getCurrentPosition() + 1);
                break;
            case R.id.cancel:
                dismiss();
                break;
            case R.id.finish:
                dismiss();
                break;
            default:
        }

    }

    private void initCalendarPage() {
        currentDate = new CalendarDate();
        getMonthCount(currentDate.year, currentDate.month);

        mCalendarDate.setText(getContext().getString(R.string.select_date, currentDate.getYear(), currentDate.getMonth()));
        CustomDayView customDayView = new CustomDayView(getContext(), R.layout.custom_day);
        mCalendarViewAdapter = new CalendarViewAdapter(
                getContext(),
                this,
                CalendarAttr.CalendarType.MONTH,
                CalendarAttr.WeekArrayType.Monday,
                customDayView);
        mCalendarViewAdapter.setMarkData(mEventCountList);

        mCalendarView.setAdapter(mCalendarViewAdapter);
        mCalendarView.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        mCalendarView.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        mCalendarView.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = mCalendarViewAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    currentDate = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    mCalendarDate.setText(getContext().getString(R.string.select_date, currentDate.getYear(), currentDate.getMonth()));

                    getMonthCount(currentDate.year, currentDate.month);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private void getMonthCount(int year, int month) {
        int company = Cache.getSelectCompany();
        if (monthMar.get(year + "" + month) == null) {
            Http.netServer(SafeApi.class)
                    .safeEventListMonthCount(Cache.getUserID(), company, year, month)
                    .map(new NetWorkFunc1<>())
                    .compose(Network.networkIO())
                    .map(eventCountData -> {
                        HashMap<String, String> map = new HashMap<>(eventCountData.size());
                        for (int i = 0; i < eventCountData.size(); i++) {
                            EventCountData data = eventCountData.get(i);
                            String[] date = data.getDay().split("-");
                            map.put(date[0] + "-" + Integer.valueOf(date[1]) + "-" + date[2], data.getCount() + "");
                        }
                        return map;
                    })
                    .compose(Network.networkIO())
                    .subscribe(eventCountData -> {
                        monthMar.put(year + "" + month, true);
                        mEventCountList.putAll(eventCountData);
                        mCalendarViewAdapter.setMarkData(mEventCountList);
                        mCalendarViewAdapter.notifyDataChanged();
                    }, Throwable::printStackTrace);
        }
    }


    @Override
    public void onSelectDate(CalendarDate calendarDate) {
        dismiss();
        if (mOnSelectDateCallback != null) {
            if (mCalendarTyp == SAFE_EVENT_COUNT) {
                String date = getContext().getResources().getString(R.string.safe_select_title_date, calendarDate.getYear(), calendarDate.getMonth(), calendarDate.getDay());
                mOnSelectDateCallback.onSelectDate(date);
            } else {
                String date = getContext().getResources().getString(R.string.punish_select_title_date, calendarDate.getYear(), calendarDate.getMonth());
                mOnSelectDateCallback.onSelectDate(date);
            }
        }
    }

    @Override
    public void onSelectOtherMonth(int offset) {
        //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
        mCalendarView.selectOtherMonth(offset);
    }

    public void setEventCountList(HashMap<String, String> eventCountList) {
        mEventCountList = eventCountList;
        if (mCalendarViewAdapter != null) {
            mCalendarViewAdapter.setMarkData(mEventCountList);
            mCalendarViewAdapter.notifyDataChanged();
        }
    }

    public interface OnSelectDateCallback {
        void onSelectDate(String date);
    }
}
