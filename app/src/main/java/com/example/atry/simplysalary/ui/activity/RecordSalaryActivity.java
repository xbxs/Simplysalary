package com.example.atry.simplysalary.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atry.simplysalary.R;
import com.example.atry.simplysalary.globe.BaseActivity;
import com.example.atry.simplysalary.model.bean.VoiceBean;
import com.example.atry.simplysalary.utils.CommonRequest;
import com.example.atry.simplysalary.utils.CommonResponse;
import com.example.atry.simplysalary.utils.ConstantValues;
import com.example.atry.simplysalary.utils.HttpUtils;
import com.example.atry.simplysalary.utils.SPUtils;
import com.example.atry.simplysalary.utils.Uiutils;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class RecordSalaryActivity extends BaseActivity {


    @BindView(R.id.iv_back_recordsalary)
    ImageView ivBackRecordsalary;
    @BindView(R.id.tv_name_record)
    TextView tvNameRecord;
    @BindView(R.id.et_wage_record)
    EditText etWageRecord;
    @BindView(R.id.et_term_record)
    EditText etTermRecord;
    @BindView(R.id.btn_term_record)
    Button btnTermRecord;
    @BindView(R.id.tv_shift_record)
    TextView tvShiftRecord;
    @BindView(R.id.btn_shift_record)
    Button btnShiftRecord;
    @BindView(R.id.btn_voice_record)
    Button btnVoiceRecord;
    @BindView(R.id.btn_record)
    Button btnRecord;

    private RecognizerDialog  mIatDialog;
    private String maddname;
    private String phonenumber;
    private String u_wage;
    private String morning;
    private String afternoon;
    private String evening;
    private String s_section;
    private AlertDialog dialog;
    private String time;
    private String shift;

    @Override
    protected int setContentView() {
        return R.layout.activity_record_salary;
    }

    @Override
    protected void initView() {
        s_section = getIntent().getStringExtra("s_section");
        maddname = getIntent().getStringExtra("addname");
        phonenumber = getIntent().getStringExtra("phonenumber");
        u_wage = getIntent().getStringExtra("u_wage");

        tvNameRecord.setText(maddname);
        etWageRecord.setText(u_wage);
        tvShiftRecord.setText("早班("+SPUtils.getInstance().getString(ConstantValues.ARRAGEWORK_MORING,"9:00-12:00")+")");
        ivBackRecordsalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //1 和2是标识，1为工作时间，2为工作班次
        btnTermRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String data[] = {"1","1.5","2","2.5","3","3.5","4","4.5","5","5.5","6","6.5","7","7.5","8","8.5","9","9.5","10"};
                singleDialog(1,etTermRecord,"请选择时间",data);
            }
        });

        btnShiftRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morning = "早班("+SPUtils.getInstance().getString(ConstantValues.ARRAGEWORK_MORING,"9:00-12:00")+")";
                afternoon = "中班("+SPUtils.getInstance().getString(ConstantValues.ARRAGEWORK_AFTERNOON,"12:00-17:00")+")";
                evening = "晚班("+SPUtils.getInstance().getString(ConstantValues.ARRAGEWORK_EVENING,"17:00-22:00")+")";

                final String data[] = {morning, afternoon, evening};
                singleDialog(2,tvShiftRecord,"请选择班次",data);
            }
        });

        btnVoiceRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startListen();
            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etTermRecord.getText().toString()) ||TextUtils.isEmpty(etTermRecord.getText().toString()) || TextUtils.isEmpty(tvShiftRecord.getText().toString())){
                    Uiutils.toast("请填写信息完整");
                }else{
                    showSelfDialog(RecordSalaryActivity.this);
                }
            }
        });
    }
    //语音UI
    private final RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {
        StringBuffer mVoiceBuffer = new StringBuffer();
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {

            String date = parseData(recognizerResult.getResultString());
            mVoiceBuffer.append(date);
            if(b){
                String ask = mVoiceBuffer.toString();
                if(ask.contains("时间为")){
                    etTermRecord.setText(ask.substring(3,ask.length() -1));
                    startSpeak("请核对");
                }else{
                    startSpeak("我好像没有听清呢");
                }
                mVoiceBuffer.setLength(0);
            }
        }

        @Override
        public void onError(SpeechError speechError) {

        }
    };
    private RecognizerDialogListener mRecognizerDialogListener = recognizerDialogListener;
    //解析JSON数据
    private String parseData(String resultString) {
        Gson gson = new Gson();
        VoiceBean voiceBean = gson.fromJson(resultString,VoiceBean.class);
        StringBuffer sb  = new StringBuffer();
        ArrayList<VoiceBean.WsBean> ws = voiceBean.ws;
        for(VoiceBean.WsBean wsBean : ws){
            String w = wsBean.cw.get(0).w;
            sb.append(w);
        }

        return sb.toString();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    //语音合成
    public void startSpeak(String content){
        //1.创建SpeechSynthesize对象，第二参数：本地合成是传InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(RecordSalaryActivity.this,null);
        //2.合成参数设置
        mTts.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");
        mTts.setParameter(SpeechConstant.SPEED,"50");
        mTts.setParameter(SpeechConstant.VOLUME,"80");
        mTts.setParameter(SpeechConstant.ENGINE_TYPE,SpeechConstant.TYPE_CLOUD);
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH,"./sdcard/iflytek.pcm");
        //开始合成
        mTts.startSpeaking(content,null);
    }

    //收写
    public void startListen(){
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(RecordSalaryActivity.this, null);
        //设置语法ID和 SUBJECT 为空，以免因之前有语法调用而设置了此参数；或直接清空所有参数，具体可参考 DEMO 的示例。
        mIatDialog.setParameter( SpeechConstant.CLOUD_GRAMMAR, null );
        mIatDialog.setParameter( SpeechConstant.SUBJECT, null );
        //设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
        mIatDialog.setParameter(SpeechConstant.RESULT_TYPE, "json");
        //此处engineType为“cloud”
        mIatDialog.setParameter( SpeechConstant.ENGINE_TYPE, "cloud" );
        //设置语音输入语言，zh_cn为简体中文
        mIatDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //设置结果返回语言
        mIatDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
        //取值范围{1000～10000}
        mIatDialog.setParameter(SpeechConstant.VAD_BOS, "4000");
        //设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
        //自动停止录音，范围{0~10000}
        mIatDialog.setParameter(SpeechConstant.VAD_EOS, "1000");
        //设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIatDialog.setParameter(SpeechConstant.ASR_PTT,"1");

        //开始识别并设置监听器
        mIatDialog.setListener(mRecognizerDialogListener);
        //显示听写对话框
        mIatDialog.show();
    }
    //单选框
    public void singleDialog(int flag,TextView tv,String title,final String data[]) {

        AlertDialog.Builder builder =new AlertDialog.Builder(this)
                .setTitle(title)

                .setSingleChoiceItems(data, -1,new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialogInterface, int i) {

                        String choices =data[i];
                        tv.setText(choices);
                        dialogInterface.dismiss();
                    }
                });

        builder.show();
    }
    //确认工资的dialog
    public void showSelfDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = View.inflate(activity,R.layout.item_dialog_confirmsalary,null);
        ImageView iv_cancel = view.findViewById(R.id.iv_cancel);
        //为dialog赋值
        Button btn_cofirm_salary = view.findViewById(R.id.btn_cofirm_salary);
        TextView tv_name_confirm = view.findViewById(R.id.tv_name_confirm);
        TextView tv_shift_confirm =   view.findViewById(R.id.tv_shift_confirm);
        TextView tv_term_confirm = view.findViewById(R.id.tv_term_confirm);
        TextView tv_wage_confirm = view.findViewById(R.id.tv_wage_confirm);
        tv_name_confirm.setText(maddname);
        tv_shift_confirm.setText(tvShiftRecord.getText().toString());
        tv_term_confirm.setText(etTermRecord.getText().toString());
        tv_wage_confirm.setText(etWageRecord.getText().toString());

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_cofirm_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestServerAddSalary();
            }
        });

    }
    //去服务器存数据
    public void requestServerAddSalary(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        time = simpleDateFormat.format(date);
        shift = "1";
        if(tvShiftRecord.getText().toString().equals(morning)){
            shift = "1";
        }else if(tvShiftRecord.getText().toString().equals(afternoon)){
            shift = "2";
        }else if(tvShiftRecord.getText().toString().equals(evening)){
            shift = "3";
        }

        Log.i("TAG","time:"+ time);
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setRequestCode("addsalary");
        commonRequest.addRequestParam("u_phone",phonenumber);
        commonRequest.addRequestParam("s_rtime", time);
        commonRequest.addRequestParam("s_term",etTermRecord.getText().toString());
        commonRequest.addRequestParam("s_shift", shift);
        commonRequest.addRequestParam("s_section",s_section);
        commonRequest.addRequestParam("s_wage",etWageRecord.getText().toString());

        HttpUtils.sendPost(ConstantValues.URL_SALARY+"addSalary",commonRequest.getJsonStr(), new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Uiutils.toast("NetWork ERROR"+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                CommonResponse res = new CommonResponse(response.body().string());
                String resCode = res.getResCode();
                String resMsg = res.getResMsg();
                if(resCode.equals("0")){

                    Uiutils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Intent intent = new Intent();
                           // time = (time.substring(4,6)+"月"+time.substring(6,8)+"日");
                            intent.putExtra("s_rtime",time);
                            intent.putExtra("s_term",etTermRecord.getText().toString());
                            intent.putExtra("s_shift",shift);
                            intent.putExtra("s_wage",etWageRecord.getText().toString());
                            setResult(2,intent);
                            finish();
                        }
                    });
                }
                Uiutils.toast(resMsg);
            }
        });
    }

}
