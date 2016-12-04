package andfans.com.mylbs.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import andfans.com.mylbs.MainActivity;
import andfans.com.mylbs.R;
import andfans.com.mylbs.util.ChooseImageDialog;
import andfans.com.mylbs.util.Utils;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;
import rx.Subscriber;

/*
 * Created by 兆鹏 on 2016/11/30.
 */
public class RegisterActivity extends Activity {
    private ImageView imBack,imUserPic;
    private EditText edUser,edTelephone,edPassword,edConfirmCode;
    private Button btRegister,btGetConfirm;
    private Context context;
    private static final int REQUEST_CODE_PICK_IMAGE = 0;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String TAG = "RegisterActivity";
    private Uri userImageUri = null;
    private static Bitmap photo;
    private ChooseImageDialog chooseUserImageDialog;
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
        edPassword = (EditText) findViewById(R.id.id_register_password);
        btRegister = (Button) findViewById(R.id.id_register_register);
        btGetConfirm = (Button) findViewById(R.id.id_register_getConfirmCode);
        imBack.setOnClickListener(view -> finish());
        imUserPic.setOnClickListener(view -> showChoosePicDiaglog());
        btRegister.setOnClickListener(view -> {
            Utils.showToast(context,"点击了注册按钮");
            if(userImageUri == null){
                Utils.showToast(context,"nulllllll");
            }
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

    private void showChoosePicDiaglog(){
        chooseUserImageDialog = new ChooseImageDialog(context, v -> {
            chooseUserImageDialog.dismiss();
            switch (v.getId()) {
                case R.id.takePhotoBtn: {
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        Intent getImageByCamera = new
                                Intent("android.media.action.IMAGE_CAPTURE");
                        startActivityForResult(getImageByCamera,
                                REQUEST_CODE_CAPTURE_CAMEIA);
                    } else {
                        Utils.showToast(getApplicationContext(),"请确认已经插入SD卡");
                    }
                    break;
                }
                case R.id.pickPhotoBtn:
//                                Intent intent = new Intent(Intent.ACTION_PICK);//从相册中选取图片
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    //从相册/文件管理中选取图片
                    intent.setType("image/*");//相片类型
                    startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
                    break;
                case R.id.cancelBtn: {
                    break;
                }
            }
        });
        chooseUserImageDialog.showAtLocation(findViewById(R.id.mainLayout),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri imageUri = null;
        if(resultCode == RESULT_CANCELED){
            Utils.showToast(context,"获取失败");
            imUserPic.setImageResource(R.drawable.headpic);//设定为默认头像
        }else if(resultCode == RESULT_OK) {//选取成功后进行裁剪
            if (requestCode == REQUEST_CODE_PICK_IMAGE) {
                //从图库中选择图片作为头像
                imageUri = data.getData();
                Log.d(TAG, "onActivityResult: " + imageUri);
                reSizeImage(imageUri);
            } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
                //使用相机获取头像
                Log.d(TAG, "onActivityResult: from photo");
                imageUri = data.getData();
                Log.d(TAG, "onActivityResult: " + imageUri);
                if (imageUri == null) {
                    //use bundle to get data
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap bitMap = (Bitmap) bundle.get("data"); //get bitmap
                        imageUri = Uri.parse(MediaStore.Images.Media.
                                insertImage(getContentResolver(), bitMap, null,null));
                        Log.d(TAG, "onActivityResult: bndle != null" + imageUri);
                        reSizeImage(imageUri);
                    } else {
                        Utils.showToast(context,"Error");
                    }
                }
            }else if(requestCode == RESIZE_REQUEST_CODE){
                Log.d(TAG, "onActivityResult: " + userImageUri);
                showImage(userImageUri);
            }
        }
    }

    private void reSizeImage(Uri uri) {//重新剪裁图片的大小
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//可以裁剪
        intent.putExtra("aspectX", 1);//宽高比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        /**
         * 此方法返回的图片只能是小图片（测试为高宽160px的图片）
         * 故将图片保存在Uri中，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         */
        //intent.putExtra("return-data", true);
//        intent.putExtra("output", Uri.fromFile(new File("/mnt/sdcard/temp")));//保存路径
        userImageUri = Uri.parse("file://"+ Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, userImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    private void showImage(Uri uri) {
        Log.d(TAG, "showImage: ");
        try {
            Log.d(TAG, "showImage: " + uri.toString());
            Bitmap photo = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            imUserPic.setImageDrawable(new BitmapDrawable(photo));
            Utils.showToast(context,"头像设置成功");
        } catch (IOException e) {
            Utils.showToast(context,"头像设置失败");
            e.printStackTrace();
        }
    }
}
