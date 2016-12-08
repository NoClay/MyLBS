package andfans.com.mylbs.LoginAndSign;

import android.content.Context;
import android.content.Intent;
import android.net.http.EventHandler;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import andfans.com.mylbs.Activity.ActivityCollector;
import andfans.com.mylbs.Data.SignUserData;
import andfans.com.mylbs.R;
import andfans.com.mylbs.util.UtilClass;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static andfans.com.mylbs.util.UtilClass.isMobileNum;


public class ChangePassWord extends AppCompatActivity implements View.OnClickListener {

    private EditText signedPhoneNumber;
    private Button sendMessage;
    private EditText checkNumber;
    private EditText newPassWord;
    private ImageView cancelButton;
    private Button completeChangePassWord;
    private boolean isFromLogin;
    private int i = 30;
    private static final int MSG_WHAT_FOR_THREAD = 0;
    private static final int MSG_WHAT_FOR_THREAD_DEATH = 2;
    private static final int MSG_WHAT_FOT_SHORT_MESSAGE = 1;
    private static final int MSG_VERIFY_CODE = 3;
    private String objectId;
    private static final String TAG = "ChangePassWord";
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_word);
        ActivityCollector.addActivity(this);
        findView();

        signedPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isMobileNum(signedPhoneNumber.getText().toString())) {
                    //发送短信
                    sendMessage.setClickable(true);
                    sendMessage.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_button_def));
                } else {
                    sendMessage.setClickable(false);
                    sendMessage.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_button_2));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    private void findView() {
        signedPhoneNumber = (EditText) findViewById(R.id.signed_phoneNumber);
        sendMessage = (Button) findViewById(R.id.send_message_button);
        newPassWord = (EditText) findViewById(R.id.new_password);
        cancelButton = (ImageView) findViewById(R.id.back);
        completeChangePassWord = (Button) findViewById(R.id.complete_change);
        checkNumber = (EditText) findViewById(R.id.input_checkNumber);
        TextView title = (TextView) findViewById(R.id.info_title);
        title.setText("修改密码");
        sendMessage.setOnClickListener(this);
        completeChangePassWord.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        Intent intent = getIntent();
        isFromLogin = intent.getBooleanExtra("isLogin", true);
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_VERIFY_CODE:{
                    if (msg.arg1 == 0){
                        //验证成功
                        //验证成功，在这里进行修改密码
                        SignUserData newUser = new SignUserData();
                        newUser.setPassWord(newPassWord.getText().toString());
                        newUser.update(objectId, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(ChangePassWord.this,
                                            "修改成功,即将跳转到登录界面",
                                            Toast.LENGTH_SHORT).show();
                                    setResultBack(true);
                                } else {
                                    Toast.makeText(ChangePassWord.this,
                                            "修改失败",
                                            Toast.LENGTH_SHORT).show();
                                    setResultBack(false);
                                }
                            }
                        });
                    }else{
                        UtilClass.toToast(context, "验证码错误");
                    }
                    break;
                }
                case MSG_WHAT_FOR_THREAD: {
                    sendMessage.setClickable(false);
                    sendMessage.setText(msg.arg1 + "秒后可获取验证码");
                    sendMessage.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_button_2));
                    break;
                }
                case MSG_WHAT_FOR_THREAD_DEATH: {
//                    Log.d(TAG, "handleMessage() called with: " + "线程死亡");
                    sendMessage.setClickable(true);
                    sendMessage.setText("获取验证码");
                    sendMessage.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_button_def));
                    break;
                }
                case MSG_WHAT_FOT_SHORT_MESSAGE: {
                    if (msg.arg1 == 0) {
                        //验证码发送成功
                        Toast.makeText(context, "验证码发送成功，请等待",
                                Toast.LENGTH_SHORT).show();
                        i = 30;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (i > 0) {
                                    i--;
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Message message = new Message();
                                    if (i == 0) {
                                        message.what = MSG_WHAT_FOR_THREAD_DEATH;
                                    } else {
                                        message.what = MSG_WHAT_FOR_THREAD;
                                    }
                                    message.arg1 = i;
                                    handler.sendMessage(message);
//                                            Log.d(TAG, "run() called with: " + i);
                                }
                            }
                        }).start();
//                                Log.d(TAG, "afterEvent() called with: 验证码发送成功");
                        //验证码已发送
                    } else {
                        Toast.makeText(context, "验证码发送失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }

        }
    };

    private void setResultBack(boolean isSuccess) {
        if (isFromLogin) {
            Intent intent = new Intent();
            intent.putExtra("userName", signedPhoneNumber.getText().toString());
            if (isSuccess) {
                setResult(RESULT_OK, intent);
            } else {
                setResult(RESULT_CANCELED, intent);
            }
        } else {
            if (isSuccess) {
                Intent intent = new Intent(ChangePassWord.this, LoginActivity.class);
                intent.putExtra("userName", signedPhoneNumber.getText().toString());
                startActivity(intent);
            }
        }
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_message_button: {
                BmobQuery<SignUserData> query = new BmobQuery<>();
                query.addWhereEqualTo("phoneNumber", signedPhoneNumber.getText().toString());
                query.findObjects(new FindListener<SignUserData>() {
                    @Override
                    public void done(List<SignUserData> list, BmobException e) {
                        if (!list.isEmpty()) {
                            //获取用户的Id
                            SignUserData signUserBaseClass = list.get(0);
                            objectId = signUserBaseClass.getObjectId();
                            BmobSMS.requestSMSCode(context, signedPhoneNumber.getText().toString(),
                                    "MyLBS", new RequestSMSCodeListener() {
                                        @Override
                                        public void done(Integer integer, cn.bmob.sms.exception.BmobException e) {
                                            Message msg = new Message();
                                            msg.arg1 = (e == null ? 0 : 1);
                                            msg.what = MSG_WHAT_FOT_SHORT_MESSAGE;
                                            handler.sendMessage(msg);
                                        }
                                    });
                        } else {
                            Toast.makeText(ChangePassWord.this, "未查询到此用户", Toast.LENGTH_SHORT).show();
                        }
                        if (e != null) {
                            Log.e(TAG, "done: ", e);
                            Toast.makeText(ChangePassWord.this, "数据库异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            }
            case R.id.back: {
                setResultBack(false);
                break;
            }
            case R.id.complete_change: {
                if (checkNumber.getText().toString().isEmpty()) {
                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                } else if (newPassWord.getText().toString().length() > 16 || newPassWord.getText().
                        toString().length() < 6) {
                    Toast.makeText(this, "密码过长或过短", Toast.LENGTH_SHORT).show();
                } else {
                    BmobSMS.verifySmsCode(context, signedPhoneNumber.getText().toString(),
                            checkNumber.getText().toString(),
                            new VerifySMSCodeListener() {
                                @Override
                                public void done(cn.bmob.sms.exception.BmobException e) {
                                    Message message = Message.obtain();
                                    message.what = MSG_VERIFY_CODE;
                                    message.arg1 = ( e == null ? 0 : 1);
                                    handler.sendMessage(message);
                                }
                            });
                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        setResultBack(false);
        super.onBackPressed();
    }
}
