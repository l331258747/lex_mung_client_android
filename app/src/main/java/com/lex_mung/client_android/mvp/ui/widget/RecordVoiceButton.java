package com.lex_mung.client_android.mvp.ui.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.widget.AppCompatButton;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.ui.activity.MessageChatActivity;
import com.lex_mung.client_android.mvp.ui.adapter.MessageChatAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import me.zl.mvp.utils.AppUtils;

public class RecordVoiceButton extends AppCompatButton {

    private File myRecAudioFile;

    private MessageChatAdapter mMsgListAdapter;
    private static final int MIN_INTERVAL_TIME = 1000;// 1s
    private final static int CANCEL_RECORD = 5;
    private final static int START_RECORD = 7;
    private final static int RECORD_DENIED_STATUS = 1000;

    private float mTouchY1, mTouchY2, mTouchY;//依次为按下录音键坐标、手指离开屏幕坐标、手指移动坐标
    private long startTime, time1, time2;//依次为开始录音时刻，按下录音时刻，松开录音按钮时刻

    private Dialog recordIndicator;
    private Dialog mTimeShort;
    private ImageView mVolumeIv;
    private TextView mRecordHintTv;

    private MediaRecorder recorder;

    private ObtainDecibelThread mThread;

    private Handler mVolumeHandler;
    public static boolean mIsPressed = false;
    private MessageChatActivity mChatView;
    private Context mContext;
    private Conversation mConv;
    private Timer timer = new Timer();
    private Timer mCountTimer;
    private boolean isTimerCanceled = false;
    private boolean mTimeUp = false;
    private final MyHandler myHandler = new MyHandler(this);
    private static int[] res;
    private Chronometer mVoiceTime;
    private TextView mTimeDown;
    private LinearLayout mMicShow;

    public RecordVoiceButton(Context context) {
        super(context);
        init();
    }

    public RecordVoiceButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    public RecordVoiceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        mVolumeHandler = new ShowVolumeHandler(this);
        //如果需要跳动的麦克图 将五张相同的图片替换即可
        res = new int[]{R.drawable.ic_mic_1
                , R.drawable.ic_mic_2
                , R.drawable.ic_mic_3
                , R.drawable.ic_mic_4
                , R.drawable.ic_mic_5
                , R.drawable.ic_cancel_record};
    }

    public void initConv(Conversation conv, MessageChatAdapter adapter, MessageChatActivity chatView) {
        this.mConv = conv;
        this.mMsgListAdapter = adapter;
        mChatView = chatView;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.setPressed(true);
        int action = event.getAction();
        mTimeShort = new Dialog(getContext(), R.style.alert_dialog);
        mTimeShort.setContentView(R.layout.layout_send_voice_time_short);
        float MIN_CANCEL_DISTANCE = 300f;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                this.setText(AppUtils.getString(mContext, R.string.text_send_voice_hint)); //文字 松开结束
                mIsPressed = true;
                time1 = System.currentTimeMillis();
                mTouchY1 = event.getY();

                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//检查sd卡是否存在
                    if (isTimerCanceled) {
                        timer = createTimer();
                    }
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            android.os.Message msg = myHandler.obtainMessage();
                            msg.what = START_RECORD;
                            msg.sendToTarget();
                        }
                    }, 300);
                } else {
                    AppUtils.makeText(mContext, "暂无外部存储");
                    setPressed(false);
                    setText(AppUtils.getString(mContext, R.string.text_record_voice_hint));//文字 按住说话
                    mIsPressed = false;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                setText(AppUtils.getString(mContext, R.string.text_record_voice_hint));//文字 按住说话
                mIsPressed = false;
                this.setPressed(false);
                mTouchY2 = event.getY();
                time2 = System.currentTimeMillis();
                if (time2 - time1 < 300) {
                    showCancelDialog();
                    return true;
                } else if (time2 - time1 < 1000) {
                    showCancelDialog();
                    cancelRecord();
                } else if (mTouchY1 - mTouchY2 > MIN_CANCEL_DISTANCE) {
                    cancelRecord();
                } else if (time2 - time1 < 60000) {
                    finishRecord();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mTouchY = event.getY();
                if (mTouchY1 - mTouchY > MIN_CANCEL_DISTANCE) {//手指上滑到超出限定后，显示松开取消发送提示
                    setText(AppUtils.getString(mContext, R.string.text_cancel_record_voice_hint));//文字  松开手指取消发送
                    mVolumeHandler.sendEmptyMessage(CANCEL_RECORD);
                    if (mThread != null) {
                        mThread.exit();
                    }
                    mThread = null;
                } else {
                    setText(AppUtils.getString(mContext, R.string.text_send_voice_hint)); //文字 松开结束
                    if (mThread == null) {
                        mThread = new ObtainDecibelThread();
                        mThread.start();
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:// 当手指移动到view外面，会cancel
                setText(AppUtils.getString(mContext, R.string.text_record_voice_hint));//文字 按住说话
                cancelRecord();
                break;
        }

        return true;
    }

    private void showCancelDialog() {
        mTimeShort.show();
        new Handler().postDelayed(() -> mTimeShort.dismiss(), 1000);
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            isTimerCanceled = true;
        }
        if (mCountTimer != null) {
            mCountTimer.cancel();
            mCountTimer.purge();
        }
    }

    private Timer createTimer() {
        timer = new Timer();
        isTimerCanceled = false;
        return timer;
    }

    private void initDialogAndStartRecord() {
        //存放录音文件目录
        File rootDir = mContext.getFilesDir();
        String fileDir = rootDir.getAbsolutePath() + "/voice";
        File destDir = new File(fileDir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        //录音文件的命名格式
        myRecAudioFile = new File(fileDir, DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".amr");
        if (myRecAudioFile == null) {
            cancelTimer();
            stopRecording();
            AppUtils.makeText(mContext, AppUtils.getString(mContext, R.string.text_create_file_failed));
        }
        recordIndicator = new Dialog(getContext(), R.style.alert_dialog);
        recordIndicator.setContentView(R.layout.layout_record_voice_dialog);
        mVolumeIv = recordIndicator.findViewById(R.id.iv_volume_hint);
        mRecordHintTv = recordIndicator.findViewById(R.id.tv_record_voice);
        mVoiceTime = recordIndicator.findViewById(R.id.c_voice_time);
        mTimeDown = recordIndicator.findViewById(R.id.tv_time_down);
        mMicShow = recordIndicator.findViewById(R.id.ll_mic_show);
        mRecordHintTv.setText(AppUtils.getString(mContext, R.string.text_move_to_cancel_hint));
        startRecording();
        recordIndicator.show();
    }

    /**
     * 录音完毕加载 ListView item
     */
    private void finishRecord() {
        cancelTimer();
        stopRecording();

        if (recordIndicator != null) {
            recordIndicator.dismiss();
        }

        long intervalTime = System.currentTimeMillis() - startTime;
        if (intervalTime < MIN_INTERVAL_TIME) {
            mMicShow.setVisibility(GONE);
            myRecAudioFile.delete();
        } else {
            mMicShow.setVisibility(VISIBLE);
            if (myRecAudioFile != null && myRecAudioFile.exists()) {
                MediaPlayer mp = new MediaPlayer();
                try {
                    FileInputStream fis = new FileInputStream(myRecAudioFile);
                    mp.setDataSource(fis.getFD());
                    mp.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int duration = mp.getDuration() / 1000;//即为时长 是s
                if (duration < 1) {
                    duration = 1;
                } else if (duration > 60) {
                    duration = 60;
                }
                try {
                    VoiceContent content = new VoiceContent(myRecAudioFile, duration);
                    Message msg = mConv.createSendMessage(content);
                    mMsgListAdapter.addMsgFromReceiptToList(msg);
                    if (mConv.getType() == ConversationType.single) {
                        MessageSendingOptions options = new MessageSendingOptions();
                        options.setNeedReadReceipt(true);
                        JMessageClient.sendMessage(msg, options);
                    } else {
                        MessageSendingOptions options = new MessageSendingOptions();
                        options.setNeedReadReceipt(true);
                        JMessageClient.sendMessage(msg, options);
                    }
                    mChatView.setToBottom();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 取消录音，清除计时
     */
    private void cancelRecord() {
        //可能在消息队列中还存在HandlerMessage，移除剩余消息
        mVolumeHandler.removeMessages(56, null);
        mVolumeHandler.removeMessages(57, null);
        mVolumeHandler.removeMessages(58, null);
        mVolumeHandler.removeMessages(59, null);
        mTimeUp = false;
        cancelTimer();
        stopRecording();
        if (recordIndicator != null) {
            recordIndicator.dismiss();
        }
        if (myRecAudioFile != null) {
            myRecAudioFile.delete();
        }
    }

    /**
     * 开始录音
     */
    private void startRecording() {
        try {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            recorder.setOutputFile(myRecAudioFile.getAbsolutePath());
            myRecAudioFile.createNewFile();
            recorder.prepare();
            recorder.start();
            startTime = System.currentTimeMillis();

            mVoiceTime.setBase(SystemClock.elapsedRealtime());
            mVoiceTime.start();

            mCountTimer = new Timer();
            mCountTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mTimeUp = true;
                    android.os.Message msg = mVolumeHandler.obtainMessage();
                    msg.what = 50;
                    Bundle bundle = new Bundle();
                    bundle.putInt("restTime", 10);
                    msg.setData(bundle);
                    msg.sendToTarget();
                    mCountTimer.cancel();
                }
            }, 51000);

        } catch (IOException e) {
            e.printStackTrace();
            AppUtils.makeText(mContext, "录音状态异常,请稍后重试");
            cancelTimer();
            dismissDialog();
            if (mThread != null) {
                mThread.exit();
                mThread = null;
            }
            if (myRecAudioFile != null) {
                myRecAudioFile.delete();
            }
            recorder.release();
            recorder = null;
        } catch (RuntimeException e) {
            cancelTimer();
            dismissDialog();
            if (mThread != null) {
                mThread.exit();
                mThread = null;
            }
            if (myRecAudioFile != null) {
                myRecAudioFile.delete();
            }
            recorder.release();
            recorder = null;
        }


        mThread = new ObtainDecibelThread();
        mThread.start();

    }

    /**
     * 停止录音，隐藏录音动画
     */
    private void stopRecording() {
        if (mThread != null) {
            mThread.exit();
            mThread = null;
        }
        releaseRecorder();
    }

    /**
     * 释放资源
     */
    public void releaseRecorder() {
        if (recorder != null) {
            try {
                recorder.stop();
            } catch (Exception e) {
                Log.d("RecordVoice", "Catch exception: stop recorder failed!");
            } finally {
                recorder.release();
                recorder = null;
            }
        }
    }

    private class ObtainDecibelThread extends Thread {

        private volatile boolean running = true;

        void exit() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (recorder == null || !running) {
                    break;
                }
                try {
                    int x = recorder.getMaxAmplitude();
                    if (x != 0) {
                        int f = (int) (10 * Math.log(x) / Math.log(10));
                        if (f < 20) {
                            mVolumeHandler.sendEmptyMessage(0);
                        } else if (f < 26) {
                            mVolumeHandler.sendEmptyMessage(1);
                        } else if (f < 32) {
                            mVolumeHandler.sendEmptyMessage(2);
                        } else if (f < 38) {
                            mVolumeHandler.sendEmptyMessage(3);
                        } else {
                            mVolumeHandler.sendEmptyMessage(4);
                        }
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    /**
     * 关闭dialog
     */
    public void dismissDialog() {
        if (recordIndicator != null) {
            recordIndicator.dismiss();
        }
        setText(AppUtils.getString(mContext, R.string.text_record_voice_hint));
    }

    /**
     * 录音动画控制
     */
    private static class ShowVolumeHandler extends Handler {

        private final WeakReference<RecordVoiceButton> lButton;

        ShowVolumeHandler(RecordVoiceButton button) {
            lButton = new WeakReference<>(button);
        }

        @Override
        @SuppressLint("SetTextI18n")
        public void handleMessage(android.os.Message msg) {
            RecordVoiceButton controller = lButton.get();
            if (controller != null) {
                int restTime = msg.getData().getInt("restTime", -1);
                if (restTime > 0) { // 若restTime>0, 进入倒计时
                    controller.mTimeUp = true;
                    android.os.Message msg1 = controller.mVolumeHandler.obtainMessage();
                    msg1.what = 60 - restTime + 1;
                    Bundle bundle = new Bundle();
                    bundle.putInt("restTime", restTime - 1);
                    msg1.setData(bundle);
                    controller.mVolumeHandler.sendMessageDelayed(msg1, 1000);//创建一个延迟一秒执行的HandlerMessage，用于倒计时
                    controller.mMicShow.setVisibility(GONE);
                    controller.mTimeDown.setVisibility(VISIBLE);
                    controller.mTimeDown.setText(restTime + "");
                } else if (restTime == 0) {// 倒计时结束，发送语音, 重置状态
                    controller.finishRecord();
                    controller.setPressed(false);
                    controller.mTimeUp = false;
                } else {// restTime = -1, 一般情况
                    if (!controller.mTimeUp) { // 没有进入倒计时状态
                        if (msg.what < CANCEL_RECORD) {
                            controller.mRecordHintTv.setText(R.string.text_move_to_cancel_hint);
                            controller.mRecordHintTv.setBackgroundColor(controller.mContext.getResources().getColor(R.color.c_00));
                        } else {
                            controller.mRecordHintTv.setText(R.string.text_cancel_record_voice_hint);
                            controller.mRecordHintTv.setBackgroundColor(controller.mContext.getResources().getColor(R.color.c_00));
                        }
                    } else {// 进入倒计时
                        if (msg.what == CANCEL_RECORD) {
                            controller.mRecordHintTv.setText(R.string.text_cancel_record_voice_hint);
                            controller.mRecordHintTv.setBackgroundColor(controller.mContext.getResources().getColor(R.color.c_00));
                            if (!mIsPressed) {
                                controller.cancelRecord();
                            }
                        }
                    }
                    controller.mVolumeIv.setImageResource(res[msg.what]);
                }
            }
        }
    }

    private static class MyHandler extends Handler {
        private final WeakReference<RecordVoiceButton> lButton;

        MyHandler(RecordVoiceButton button) {
            lButton = new WeakReference<>(button);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            RecordVoiceButton controller = lButton.get();
            if (controller != null) {
                switch (msg.what) {
                    case START_RECORD:
                        if (mIsPressed) {
                            controller.initDialogAndStartRecord();
                        }
                        break;
                }
            }
        }
    }
}
