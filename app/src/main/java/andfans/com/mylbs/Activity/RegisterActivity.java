package andfans.com.mylbs.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import andfans.com.mylbs.R;
import andfans.com.mylbs.util.Utils;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

/*
 * Created by 兆鹏 on 2016/11/30.
 */
public class RegisterActivity extends Activity {
    private ImageView imBack,imUserPic;
    private EditText edUser,edTelephone,edBrand,edPassword,edConfirmCode;
    private Button btRegister,btGetConfirm;
    private boolean isRegister = false;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        context = this;
        findView();
    }

    private void findView() {
        imUserPic = (ImageView) findViewById(R.id.id_register_userPic);
        imBack = (ImageView) findViewById(R.id.id_register_back);
        edConfirmCode = (EditText) findViewById(R.id.id_register_confirmCode);
        edUser = (EditText) findViewById(R.id.id_register_user);
        edTelephone = (EditText) findViewById(R.id.id_register_telephone);
        edBrand = (EditText) findViewById(R.id.id_register_brand);
        edPassword = (EditText) findViewById(R.id.id_register_password);
        btRegister = (Button) findViewById(R.id.id_register_register);
        btGetConfirm = (Button) findViewById(R.id.id_register_getConfirmCode);
        imBack.setOnClickListener(view -> {
            if(isRegister){

            }else {
                finish();
            }
        });
        btRegister.setOnClickListener(view -> {
            Utils.showToast(context,"点击了注册按钮");
            BmobSMS.verifySmsCode(context,
                    edTelephone.getText().toString(),
                    edConfirmCode.getText().toString(),
                    new VerifySMSCodeListener() {
                        @Override
                        public void done(BmobException ex) {
                            if(ex==null){//短信验证码已验证成功
                                Log.i("bmob", "验证通过");
                            }else{
                                Log.i("bmob", "验证失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
                            }
                        }
                    });
        });
        btGetConfirm.setOnClickListener(view -> {
            Utils.showToast(context,"点击了获取验证码");
            BmobSMS.requestSMSCode(context,
                    edTelephone.getText().toString(),
                    "MyLBS",
                    new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer smsId, BmobException ex) {
                            if(ex==null){//
                                Log.i("bmob","短信发送成功，短信id："+smsId);//用于查询本次短信发送详情
                            }else{
                                Log.i("bmob","errorCode = "+ex.getErrorCode()+",errorMsg = "+ex.getLocalizedMessage());
                            }
                        }
                    });
        });
    }
}
