package com.zhongtie.work.ui.safe.calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.zhongtie.work.ui.base.BaseDialog;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class CalendarDialog extends BaseDialog implements OnSelectDateListener, View.OnClickListener {
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

    public CalendarDialog(@NonNull Context context, OnSelectDateCallback onSelectDateCallback) {
        super(context);
        this.mOnSelectDateCallback = onSelectDateCallback;
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
        mCalendarDate.setText(getContext().getString(R.string.select_date, currentDate.getYear(), currentDate.getMonth()));
        CustomDayView customDayView = new CustomDayView(getContext(), R.layout.custom_day);
        mCalendarViewAdapter = new CalendarViewAdapter(
                getContext(),
                this,
                CalendarAttr.CalendarType.MONTH,
                CalendarAttr.WeekArrayType.Monday,
                customDayView);
        mCalendarViewAdapter.setOnCalendarTypeChangedListener(new CalendarViewAdapter.OnCalendarTypeChanged() {
            @Override
            public void onCalendarTypeChanged(CalendarAttr.CalendarType type) {
            }
        });
        HashMap<String, String> markData = new HashMap<>();
        markData.put("2018-1-10", "0");
        mCalendarViewAdapter.setMarkData(markData);

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
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onSelectDate(CalendarDate calendarDate) {
        dismiss();
        if (mOnSelectDateCallback != null) {
            mOnSelectDateCallback.onSelectDate(calendarDate.toString());
        }
    }

    @Override
    public void onSelectOtherMonth(int offset) {
        //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
        mCalendarView.selectOtherMonth(offset);
    }

    public interface OnSelectDateCallback {

        void onSelectDate(String date);
    }
}
