package cn.lex_mung.client_android.mvp.ui.widget;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.zl.mvp.utils.AppUtils;

/**
 * 日期选择器工具类
 * Created by zl on 2017/11/02.
 */
public class CustomDatePicker {

    /**
     * 定义结果回调接口
     */
    public interface ResultHandler {
        void handle(Date date, String time, EasyDialog easyDialog);
    }

    public enum SCROLL_TYPE {
        HOUR(1),
        MINUTE(2);

        SCROLL_TYPE(int value) {
            this.value = value;
        }

        public int value;
    }

    private int scrollUnits = SCROLL_TYPE.HOUR.value + SCROLL_TYPE.MINUTE.value;
    private ResultHandler handler;
    private Activity context;
    private boolean canAccess = false;
    private boolean isShowDay = true;
    private boolean isShowHitherto = false;//是否第一个显示至今
    private String hitherto = "";

    private EasyDialog easyDialog;
    private View layout;
    private View view;

    private DatePickerView year_pv, month_pv, day_pv, hour_pv, minute_pv;

    private static final int MAX_MINUTE = 59;
    private static final int MAX_HOUR = 23;
    private static final int MIN_MINUTE = 0;
    private static final int MIN_HOUR = 0;
    private static final int MAX_MONTH = 12;

    private ArrayList<String> year, month, day, hour, minute;
    private int startYear, startMonth, startDay, startHour, startMinute, endYear, endMonth, endDay, endHour, endMinute;
    private boolean spanYear, spanMon, spanDay, spanHour, spanMin;
    private Calendar selectedCalender, startCalendar, endCalendar;

    public CustomDatePicker(Activity context, String startDate, String endDate, View view) {
        this(context, startDate, endDate, view, false);
    }

    public CustomDatePicker(Activity context, String startDate, String endDate, View view, boolean isShowDay) {
        this(context, startDate, endDate, view, isShowDay, false);
    }

    public CustomDatePicker(Activity context, String startDate, String endDate, View view, boolean isShowDay, boolean isShowHitherto) {
        if (isValidDate(startDate, "yyyy-MM-dd HH:mm") && isValidDate(endDate, "yyyy-MM-dd HH:mm")) {
            canAccess = true;
            this.context = context;
            this.view = view;
            this.isShowDay = isShowDay;
            this.isShowHitherto = isShowHitherto;
            selectedCalender = Calendar.getInstance();
            startCalendar = Calendar.getInstance();
            endCalendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            try {
                startCalendar.setTime(sdf.parse(startDate));
                endCalendar.setTime(sdf.parse(endDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            initDialog();
            initView();
        }
    }

    public void setHandler(ResultHandler handler) {
        this.handler = handler;
    }

    @SuppressLint("InflateParams")
    private void initDialog() {
        layout = context.getLayoutInflater().inflate(R.layout.layout_date_picker_dialog, null);
        easyDialog = new EasyDialog(context)
                .setLayout(layout)
                .setVisibility(View.GONE)
                .setGravity(EasyDialog.GRAVITY_TOP)
                .setBackgroundColor(AppUtils.getColor(context, R.color.c_00))
                .setLocationByAttachedView(view)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 200, 1000, 0)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_Y, 200, 0, 1000)
                .setTouchOutsideDismiss(false)
                .setMatchParent(true)
                .setMarginLeftAndRight(0, 0)
                .setOutsideColor(AppUtils.getColor(context, R.color.c_50));
    }

    private void initView() {
        year_pv = layout.findViewById(R.id.dp_year);
        month_pv = layout.findViewById(R.id.dp_month);
        day_pv = layout.findViewById(R.id.dp_day);
        hour_pv = layout.findViewById(R.id.dp_hour);
        minute_pv = layout.findViewById(R.id.dp_minute);
        if (!isShowDay) {
            day_pv.setVisibility(View.GONE);
        }
        layout.findViewById(R.id.tv_cancel).setOnClickListener(view -> easyDialog.dismiss());
        layout.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if ("至今".equals(hitherto)) {
                if (handler != null) {
                    handler.handle(null, "至今", easyDialog);
                }
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            if (handler != null) {
                handler.handle(selectedCalender.getTime(), sdf.format(selectedCalender.getTime()), easyDialog);
            }
        });
    }

    private void initParameter() {
        startYear = startCalendar.get(Calendar.YEAR);
        startMonth = startCalendar.get(Calendar.MONTH) + 1;
        startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
        startMinute = startCalendar.get(Calendar.MINUTE);
        endYear = endCalendar.get(Calendar.YEAR);
        endMonth = endCalendar.get(Calendar.MONTH) + 1;
        endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
        endHour = endCalendar.get(Calendar.HOUR_OF_DAY);
        endMinute = endCalendar.get(Calendar.MINUTE);
        spanYear = startYear != endYear;
        spanMon = (!spanYear) && (startMonth != endMonth);
        spanDay = (!spanMon) && (startDay != endDay);
        spanHour = (!spanDay) && (startHour != endHour);
        spanMin = (!spanHour) && (startMinute != endMinute);
        selectedCalender.setTime(startCalendar.getTime());
    }

    private void initTimer() {
        initArrayList();
        if (spanYear) {
            for (int i = startYear; i <= endYear; i++) {
                year.add(String.valueOf(i));
            }
            for (int i = startMonth; i <= MAX_MONTH; i++) {
                month.add(formatTimeUnit(i));
            }
            for (int i = startDay; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(formatTimeUnit(i));
            }

            if ((scrollUnits & SCROLL_TYPE.HOUR.value) != SCROLL_TYPE.HOUR.value) {
                hour.add(formatTimeUnit(startHour));
            } else {
                for (int i = startHour; i <= MAX_HOUR; i++) {
                    hour.add(formatTimeUnit(i));
                }
            }

            if ((scrollUnits & SCROLL_TYPE.MINUTE.value) != SCROLL_TYPE.MINUTE.value) {
                minute.add(formatTimeUnit(startMinute));
            } else {
                for (int i = startMinute; i <= MAX_MINUTE; i++) {
                    minute.add(formatTimeUnit(i));
                }
            }
        } else if (spanMon) {
            year.add(String.valueOf(startYear));
            for (int i = startMonth; i <= endMonth; i++) {
                month.add(formatTimeUnit(i));
            }
            for (int i = startDay; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(formatTimeUnit(i));
            }

            if ((scrollUnits & SCROLL_TYPE.HOUR.value) != SCROLL_TYPE.HOUR.value) {
                hour.add(formatTimeUnit(startHour));
            } else {
                for (int i = startHour; i <= MAX_HOUR; i++) {
                    hour.add(formatTimeUnit(i));
                }
            }

            if ((scrollUnits & SCROLL_TYPE.MINUTE.value) != SCROLL_TYPE.MINUTE.value) {
                minute.add(formatTimeUnit(startMinute));
            } else {
                for (int i = startMinute; i <= MAX_MINUTE; i++) {
                    minute.add(formatTimeUnit(i));
                }
            }
        } else if (spanDay) {
            year.add(String.valueOf(startYear));
            month.add(formatTimeUnit(startMonth));
            for (int i = startDay; i <= endDay; i++) {
                day.add(formatTimeUnit(i));
            }

            if ((scrollUnits & SCROLL_TYPE.HOUR.value) != SCROLL_TYPE.HOUR.value) {
                hour.add(formatTimeUnit(startHour));
            } else {
                for (int i = startHour; i <= MAX_HOUR; i++) {
                    hour.add(formatTimeUnit(i));
                }
            }

            if ((scrollUnits & SCROLL_TYPE.MINUTE.value) != SCROLL_TYPE.MINUTE.value) {
                minute.add(formatTimeUnit(startMinute));
            } else {
                for (int i = startMinute; i <= MAX_MINUTE; i++) {
                    minute.add(formatTimeUnit(i));
                }
            }
        } else if (spanHour) {
            year.add(String.valueOf(startYear));
            month.add(formatTimeUnit(startMonth));
            day.add(formatTimeUnit(startDay));

            if ((scrollUnits & SCROLL_TYPE.HOUR.value) != SCROLL_TYPE.HOUR.value) {
                hour.add(formatTimeUnit(startHour));
            } else {
                for (int i = startHour; i <= endHour; i++) {
                    hour.add(formatTimeUnit(i));
                }
            }

            if ((scrollUnits & SCROLL_TYPE.MINUTE.value) != SCROLL_TYPE.MINUTE.value) {
                minute.add(formatTimeUnit(startMinute));
            } else {
                for (int i = startMinute; i <= MAX_MINUTE; i++) {
                    minute.add(formatTimeUnit(i));
                }
            }
        } else if (spanMin) {
            year.add(String.valueOf(startYear));
            month.add(formatTimeUnit(startMonth));
            day.add(formatTimeUnit(startDay));
            hour.add(formatTimeUnit(startHour));

            if ((scrollUnits & SCROLL_TYPE.MINUTE.value) != SCROLL_TYPE.MINUTE.value) {
                minute.add(formatTimeUnit(startMinute));
            } else {
                for (int i = startMinute; i <= endMinute; i++) {
                    minute.add(formatTimeUnit(i));
                }
            }
        }
        if (isShowHitherto) {
            year.add("至今");
        }
        loadComponent();
    }

    /**
     * 将0-9转换为00-09
     */
    private String formatTimeUnit(int unit) {
        return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
    }

    private void initArrayList() {
        if (year == null) year = new ArrayList<>();
        if (month == null) month = new ArrayList<>();
        if (day == null) day = new ArrayList<>();
        if (hour == null) hour = new ArrayList<>();
        if (minute == null) minute = new ArrayList<>();
        year.clear();
        month.clear();
        day.clear();
        hour.clear();
        minute.clear();
    }

    private void loadComponent() {
        year_pv.setData(year);
        month_pv.setData(month);
        day_pv.setData(day);
        hour_pv.setData(hour);
        minute_pv.setData(minute);
        year_pv.setSelected(0);
        month_pv.setSelected(0);
        day_pv.setSelected(0);
        hour_pv.setSelected(0);
        minute_pv.setSelected(0);
        executeScroll();
    }

    private void addListener() {
        year_pv.setOnSelectListener(text -> {
            if ("至今".equals(text)) {
                hitherto = "至今";
                List<String> list = new ArrayList<>();
                list.add("至今");
                month_pv.setData(list);
                executeAnimator(month_pv);
                month_pv.postDelayed(this::dayChange, 100);
                return;
            }
            selectedCalender.set(Calendar.YEAR, Integer.parseInt(text));
            monthChange();
        });

        month_pv.setOnSelectListener(text -> {
            selectedCalender.set(Calendar.DAY_OF_MONTH, 1);
            selectedCalender.set(Calendar.MONTH, Integer.parseInt(text) - 1);
            dayChange();
        });

        day_pv.setOnSelectListener(text -> {
            selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(text));
            hourChange();
        });

        hour_pv.setOnSelectListener(text -> {
            selectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(text));
            minuteChange();
        });

        minute_pv.setOnSelectListener(text -> selectedCalender.set(Calendar.MINUTE, Integer.parseInt(text)));
    }

    private void monthChange() {
        month.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        if (selectedYear == startYear) {
            for (int i = startMonth; i <= MAX_MONTH; i++) {
                month.add(formatTimeUnit(i));
            }
        } else if (selectedYear == endYear) {
            for (int i = 1; i <= endMonth; i++) {
                month.add(formatTimeUnit(i));
            }
        } else {
            for (int i = 1; i <= MAX_MONTH; i++) {
                month.add(formatTimeUnit(i));
            }
        }
        selectedCalender.set(Calendar.MONTH, Integer.parseInt(month.get(0)) - 1);
        month_pv.setData(month);
        month_pv.setSelected(0);
        executeAnimator(month_pv);

        month_pv.postDelayed(this::dayChange, 100);
    }

    private void dayChange() {
        day.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
        if (selectedYear == startYear && selectedMonth == startMonth) {
            for (int i = startDay; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(formatTimeUnit(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth) {
            for (int i = 1; i <= endDay; i++) {
                day.add(formatTimeUnit(i));
            }
        } else {
            for (int i = 1; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(formatTimeUnit(i));
            }
        }
        selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day.get(0)));
        day_pv.setData(day);
        day_pv.setSelected(0);
        executeAnimator(day_pv);

        day_pv.postDelayed(this::hourChange, 100);
    }

    private void hourChange() {
        if ((scrollUnits & SCROLL_TYPE.HOUR.value) == SCROLL_TYPE.HOUR.value) {
            hour.clear();
            int selectedYear = selectedCalender.get(Calendar.YEAR);
            int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
            int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);
            if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay) {
                for (int i = startHour; i <= MAX_HOUR; i++) {
                    hour.add(formatTimeUnit(i));
                }
            } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay) {
                for (int i = MIN_HOUR; i <= endHour; i++) {
                    hour.add(formatTimeUnit(i));
                }
            } else {
                for (int i = MIN_HOUR; i <= MAX_HOUR; i++) {
                    hour.add(formatTimeUnit(i));
                }
            }
            selectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour.get(0)));
            hour_pv.setData(hour);
            hour_pv.setSelected(0);
            executeAnimator(hour_pv);
        }

        hour_pv.postDelayed(this::minuteChange, 100);
    }

    private void minuteChange() {
        if ((scrollUnits & SCROLL_TYPE.MINUTE.value) == SCROLL_TYPE.MINUTE.value) {
            minute.clear();
            int selectedYear = selectedCalender.get(Calendar.YEAR);
            int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
            int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);
            int selectedHour = selectedCalender.get(Calendar.HOUR_OF_DAY);
            if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay && selectedHour == startHour) {
                for (int i = startMinute; i <= MAX_MINUTE; i++) {
                    minute.add(formatTimeUnit(i));
                }
            } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour) {
                for (int i = MIN_MINUTE; i <= endMinute; i++) {
                    minute.add(formatTimeUnit(i));
                }
            } else {
                for (int i = MIN_MINUTE; i <= MAX_MINUTE; i++) {
                    minute.add(formatTimeUnit(i));
                }
            }
            selectedCalender.set(Calendar.MINUTE, Integer.parseInt(minute.get(0)));
            minute_pv.setData(minute);
            minute_pv.setSelected(0);
            executeAnimator(minute_pv);
        }
        executeScroll();
    }

    private void executeAnimator(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(200).start();
    }

    private void executeScroll() {
        year_pv.setCanScroll(year.size() > 1);
        month_pv.setCanScroll(month.size() > 1);
        day_pv.setCanScroll(day.size() > 1);
        hour_pv.setCanScroll(hour.size() > 1 && (scrollUnits & SCROLL_TYPE.HOUR.value) == SCROLL_TYPE.HOUR.value);
        minute_pv.setCanScroll(minute.size() > 1 && (scrollUnits & SCROLL_TYPE.MINUTE.value) == SCROLL_TYPE.MINUTE.value);
    }

    private void disScrollUnit(SCROLL_TYPE... scroll_types) {
        if (scroll_types == null || scroll_types.length == 0) {
            scrollUnits = SCROLL_TYPE.HOUR.value + SCROLL_TYPE.MINUTE.value;
        } else {
            for (SCROLL_TYPE scroll_type : scroll_types) {
                scrollUnits ^= scroll_type.value;
            }
        }
    }

    public void show(String time) {
        if (canAccess) {
            if (isValidDate(time, "yyyy-MM-dd")) {
                if (startCalendar.getTime().getTime() < endCalendar.getTime().getTime()) {
                    canAccess = true;
                    initParameter();
                    initTimer();
                    addListener();
                    setSelectedTime(time);
                    easyDialog.show();
                }
            } else {
                canAccess = false;
            }
        }
    }

    /**
     * 设置日期控件是否显示时和分
     */
    public void showSpecificTime(boolean show) {
        if (canAccess) {
            if (show) {
                disScrollUnit();
                hour_pv.setVisibility(View.VISIBLE);
                minute_pv.setVisibility(View.VISIBLE);
            } else {
                disScrollUnit(SCROLL_TYPE.HOUR, SCROLL_TYPE.MINUTE);
                hour_pv.setVisibility(View.GONE);
                minute_pv.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置日期控件是否可以循环滚动
     */
    public void setIsLoop(boolean isLoop) {
        if (canAccess) {
            this.year_pv.setIsLoop(isLoop);
            this.month_pv.setIsLoop(isLoop);
            this.day_pv.setIsLoop(isLoop);
            this.hour_pv.setIsLoop(isLoop);
            this.minute_pv.setIsLoop(isLoop);
        }
    }

    /**
     * 设置日期控件默认选中的时间
     */
    private void setSelectedTime(String time) {
        if (canAccess) {
            String[] str = time.split(" ");
            String[] dateStr = str[0].split("-");

            year_pv.setSelected(dateStr[0]);
            selectedCalender.set(Calendar.YEAR, Integer.parseInt(dateStr[0]));

            month.clear();
            int selectedYear = selectedCalender.get(Calendar.YEAR);
            if (selectedYear == startYear) {
                for (int i = startMonth; i <= MAX_MONTH; i++) {
                    month.add(formatTimeUnit(i));
                }
            } else if (selectedYear == endYear) {
                for (int i = 1; i <= endMonth; i++) {
                    month.add(formatTimeUnit(i));
                }
            } else {
                for (int i = 1; i <= MAX_MONTH; i++) {
                    month.add(formatTimeUnit(i));
                }
            }
            month_pv.setData(month);
            month_pv.setSelected(dateStr[1]);
            selectedCalender.set(Calendar.MONTH, Integer.parseInt(dateStr[1]) - 1);
            executeAnimator(month_pv);

            day.clear();
            int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
            if (selectedYear == startYear && selectedMonth == startMonth) {
                for (int i = startDay; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                    day.add(formatTimeUnit(i));
                }
            } else if (selectedYear == endYear && selectedMonth == endMonth) {
                for (int i = 1; i <= endDay; i++) {
                    day.add(formatTimeUnit(i));
                }
            } else {
                for (int i = 1; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                    day.add(formatTimeUnit(i));
                }
            }
            day_pv.setData(day);
            day_pv.setSelected(dateStr[2]);
            selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr[2]));
            executeAnimator(day_pv);

            if (str.length == 2) {
                String[] timeStr = str[1].split(":");

                if ((scrollUnits & SCROLL_TYPE.HOUR.value) == SCROLL_TYPE.HOUR.value) {
                    hour.clear();
                    int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);
                    if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay) {
                        for (int i = startHour; i <= MAX_HOUR; i++) {
                            hour.add(formatTimeUnit(i));
                        }
                    } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay) {
                        for (int i = MIN_HOUR; i <= endHour; i++) {
                            hour.add(formatTimeUnit(i));
                        }
                    } else {
                        for (int i = MIN_HOUR; i <= MAX_HOUR; i++) {
                            hour.add(formatTimeUnit(i));
                        }
                    }
                    hour_pv.setData(hour);
                    hour_pv.setSelected(timeStr[0]);
                    selectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeStr[0]));
                    executeAnimator(hour_pv);
                }

                if ((scrollUnits & SCROLL_TYPE.MINUTE.value) == SCROLL_TYPE.MINUTE.value) {
                    minute.clear();
                    int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);
                    int selectedHour = selectedCalender.get(Calendar.HOUR_OF_DAY);
                    if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay && selectedHour == startHour) {
                        for (int i = startMinute; i <= MAX_MINUTE; i++) {
                            minute.add(formatTimeUnit(i));
                        }
                    } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour) {
                        for (int i = MIN_MINUTE; i <= endMinute; i++) {
                            minute.add(formatTimeUnit(i));
                        }
                    } else {
                        for (int i = MIN_MINUTE; i <= MAX_MINUTE; i++) {
                            minute.add(formatTimeUnit(i));
                        }
                    }
                    minute_pv.setData(minute);
                    minute_pv.setSelected(timeStr[1]);
                    selectedCalender.set(Calendar.MINUTE, Integer.parseInt(timeStr[1]));
                    executeAnimator(minute_pv);
                }
            }
            executeScroll();
        }
    }

    /**
     * 验证字符串是否是一个合法的日期格式
     */
    private boolean isValidDate(String date, String template) {
        boolean convertSuccess = true;
        // 指定日期格式
        SimpleDateFormat format = new SimpleDateFormat(template, Locale.CHINA);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2015/02/29会被接受，并转换成2015/03/01
            format.setLenient(false);
            format.parse(date);
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

}
