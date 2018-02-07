package com.zhongtie.work.ui.rewardpunish.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseDialog;
import com.zhongtie.work.ui.safe.dialog.wheel.NumericWheelAdapter;
import com.zhongtie.work.ui.safe.dialog.wheel.OnWheelChangedListener;
import com.zhongtie.work.ui.safe.dialog.wheel.WheelAdapter;
import com.zhongtie.work.ui.safe.dialog.wheel.WheelView;
import com.zhongtie.work.util.TimeUtils;
import com.zhongtie.work.util.ResourcesUtils;

import java.util.Calendar;
import java.util.List;

/**
 * @Title:时间选择
 */

public class SelectDateTimeDialog extends BaseDialog implements OnTouchListener,
        View.OnClickListener, OnWheelChangedListener {

    public static final int BIRTH_DATE = 0x00010 << 1;
    public static final int SEX_SELECT = 0x00010 << 2;
    public static final int TAG_SELECT = 0x00010 << 3;
    public static final int DEADLINE_SELECT = 0x00010 << 7;
    public static final int BWH_SELECT = 0x00010 << 4;


    public static final int BIRTHDAY = 1;
    public static final int BLOOD = 2;
    public static final int SEX_TYPE = 3;
    public static final int WEIGHT = 4;
    public static final int START_YEAR = 2017;


    public WheelView yearWheel;
    public WheelView monthWheel;
    private TextView mSelectDateTimeSure;
    private TextView mSelectDateTimeTitle;

    private String time;
    private Context mContext;
    private Build build;
    public static final String[] SEX = {"男", "女"};


    public SelectDateTimeDialog(Context context, Build build) {
        super(context);
        mContext = context;
        this.build = build;
        this.selectDateTime = build.selectDateTime;
    }

    private OnSelectDateTimeListener selectDateTime;

    private int selectType[] = new int[3];

    @Override
    public void show() {
        super.show();
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        super.dismiss();
        return super.onTouchEvent(event);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_time);
        yearWheel = (WheelView) findViewById(R.id.days);
        monthWheel = (WheelView) findViewById(R.id.hour);
        WheelView dayWheel = (WheelView) findViewById(R.id.mins);
        dayWheel.setVisibility(View.GONE);
        mSelectDateTimeTitle = (TextView) findViewById(R.id.select_date_time_title);
        mSelectDateTimeSure = (TextView) findViewById(R.id.btn_ok);
        findViewById(R.id.btn_canel).setOnClickListener(this);
        mSelectDateTimeSure.setOnClickListener(this);
        mSelectDateTimeTitle.setText(build.title);
        selectType = build.selectType;

        switch (build.getType()) {
            case BIRTH_DATE:
                initBirthDate();
                break;
            default:
        }


    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


    private String age;

    private int mCurYear = 80, mCurMonth = 0, mCurDay = 0;


    private void updateAllWheel(WheelView year, WheelView month, boolean isFirst) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TimeUtils.getDateAfterDay(calendar.getTime(), build.deadLine));
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year.getCurrentItem());
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        //当前月份
        int minDay = 1;
        int maxDays = calendar.getActualMaximum(Calendar.DATE);
        if (build.dataModel == Build.BIRTH) {
            if ((getYearCurrentItem() == (yearWheel.getAdapter().getItemsCount() - 1))) {
                month.setAdapter(new NumericWheelAdapter(1, currentMonth, "%02d"));
                if (getMonthCurrentItem() == monthWheel.getAdapter().getItemsCount() - 1) {
                    maxDays = calendar.get(Calendar.DAY_OF_MONTH);
                }
            } else {
                month.setAdapter(new NumericWheelAdapter(1, 12, "%02d"));
            }
        }


        if (isFirst) {
//            if (currentMonth > 1)
            month.setCurrentItem(currentMonth - 1);
            month.requestLayout();
        } else {
            if (month.getCurrentItem() >= month.getAdapter().getItemsCount()) {
                month.setCurrentItem(month.getAdapter().getItemsCount() - 1);
            }
        }
        int years = calendar.get(Calendar.YEAR) + build.dataModel == Build.BIRTH ? -100 : 20;
        age = years + "-" + (month.getCurrentItem() + 1);
    }


    /**
     * 日期选择
     */
    private void initBirthDate() {
        int textSize = ResourcesUtils.dip2px(18);
        yearWheel.setTextSize(textSize);
        yearWheel.setLabel("年");
        monthWheel.setTextSize(textSize);
        monthWheel.setLabel("月");


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TimeUtils.getDateAfterDay(calendar.getTime(), build.deadLine));

        int curYear = calendar.get(Calendar.YEAR);
        age = build.age;
        if (age != null && age.contains("-") && build.dataModel == Build.BIRTH) {
            String str[] = age.split("-");
            mCurYear = 100 - (curYear - Integer.parseInt(str[0]));
            mCurMonth = Integer.parseInt(str[1]) - 1;
            mCurDay = Integer.parseInt(str[2]) - 1;
        }
        int lastYear = build.dataModel == Build.BIRTH ? curYear : curYear + 10;
        if (lastYear < START_YEAR) {
            lastYear = START_YEAR;
        }
        NumericWheelAdapter yearNumber = new NumericWheelAdapter(START_YEAR, lastYear);
        int current = lastYear - START_YEAR;
        yearWheel.setAdapter(yearNumber);
        yearWheel.setCurrentItem(current);
        yearWheel.addChangingListener(this);

        monthWheel.setAdapter(new NumericWheelAdapter(1, 12, "%02d"));
        monthWheel.setCurrentItem(mCurMonth);
        monthWheel.addChangingListener(this);

        monthWheel.post(() -> updateAllWheel(yearWheel, monthWheel, true));


    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        dismiss();
        return false;
    }

    private int getYearCurrentItem() {
        return yearWheel.getCurrentItem();
    }

    /**
     * 获取天数选择参数
     *
     * @return
     */
    private String getDayCurrentItemStr() {
        return yearWheel.getAdapter().getItem(getYearCurrentItem());
    }

    private int getMonthCurrentItem() {
        return monthWheel.getCurrentItem();
    }

    /**
     * 获取小时
     *
     * @return
     */
    private String getHourCurrentItemStr() {
        return monthWheel.getAdapter().getItem(getMonthCurrentItem());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                switch (build.getType()) {
                    case BIRTH_DATE:
                        selectType[0] = getYearCurrentItem();
                        selectType[1] = getMonthCurrentItem();
                        String t = "%s-%s";
                        time = String.format(t, (getDayCurrentItemStr()), (getHourCurrentItemStr()));
                        break;
                    case DEADLINE_SELECT:
                    case TAG_SELECT:
                        time = getHourCurrentItemStr();
                        selectType[1] = getMonthCurrentItem();
                        break;
                    case BWH_SELECT:
                        selectType[0] = getYearCurrentItem();
                        selectType[1] = getMonthCurrentItem();
                        String bwh = "%s-%s";
                        time = String.format(bwh, (getDayCurrentItemStr()), (getHourCurrentItemStr()));
                        break;
                }
                if (selectDateTime != null) {
                    selectDateTime.setTimeDate(time, build.dialogType);
                    selectDateTime.setSelectType(selectType, build.dialogType);
                }
                cancel();
                break;
            case R.id.btn_canel:
                cancel();
                break;

            default:
                break;
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        updateAllWheel(yearWheel, monthWheel, false);
    }

    /**
     * 日期时间选择构造器
     */
    public static class Build {
        public static final int BIRTH = 0x0001 << 4;
        public static final int CREATE_TIME = 0x0001 << 5;
        private Context context;
        private int type;
        private String title = "选择日期";
        private int[] selectType = {0, 0, 0};
        private String age;
        private int dataModel = CREATE_TIME;
        private List<String> tagList;
        private int dialogType;
        /**
         * 期限
         */
        private int deadLine = 0;
        private OnSelectDateTimeListener selectDateTime;
        public WheelAdapter adapter;
        public String Label = "";

        public Build setLabel(String label) {
            Label = label;
            return this;
        }

        public Build setAdapter(WheelAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public Build setDialogType(int dialogType) {
            this.dialogType = dialogType;
            return this;
        }

        public Build setAge(String age) {
            this.age = age;
            return this;
        }


        public Build setDeadLine(int deadLine) {
            this.deadLine = deadLine;
            return this;
        }

        public Build setTagList(List<String> tagList) {
            this.tagList = tagList;
            return this;
        }

        public Build setDataModel(int dataModel) {
            this.dataModel = dataModel;
            return this;
        }

        public Build setSelectType(int[] selectType) {
            this.selectType = selectType;
            return this;
        }

        public Build setTitle(String title) {
            this.title = title;
            return this;
        }

        public Build setSelectDateTime(OnSelectDateTimeListener selectDateTime) {
            this.selectDateTime = selectDateTime;
            return this;
        }

        public int getType() {
            return type;
        }

        public Build(Context context) {
            this.context = context;
        }


        public Build setType(int type) {
            this.type = type;
            return this;
        }

        public SelectDateTimeDialog create() {
            return new SelectDateTimeDialog(context, this);
        }
    }

    /**
     * 选择回调
     */
    public interface OnSelectDateTimeListener {
        /**
         * 真正的时间格式
         *
         * @param datetime
         * @param type
         */
        void setTimeDate(String datetime, int type);


        /**
         * 滑动选择的几个参数 缓存再次打开使用
         *
         * @param type
         * @param buildType
         */
        void setSelectType(int[] type, int buildType);
    }
}
