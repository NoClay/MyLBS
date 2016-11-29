package andfans.com.mylbs.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import andfans.com.mylbs.R;

/**
 * 自定义控件:左边图片右边文字
 * Created by 兆鹏 on 2016/5/13.
 */
public class MyImageButton extends LinearLayout {
    private ImageView myim = null;
    private TextView mytv = null;
    private LinearLayout linearLayout = null;

    public MyImageButton(Context context) {
        this(context,null);
    }

    public MyImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.myimbt,this,true);
        myim = (ImageView) findViewById(R.id.imbt_im);
        mytv = (TextView) findViewById(R.id.imbt_tv);
        linearLayout = (LinearLayout) findViewById(R.id.imbtLiner);
    }

    public void setPicture(int resid){
        myim.setImageResource(resid);
    }

    public void setText(String text){
        mytv.setText(text);
    }

    /**
     * 重新设置imageView的大小
     * @param width
     * @param hight
     */
    public void setImageChicun(int width,int hight){
        myim.setLayoutParams(new LayoutParams(width,hight));
    }

    public void setTextSize(float size){
        mytv.setTextSize(size);
    }

    public void setTextColors(int color)
    {
        mytv.setTextColor(color);
    }

    public void setBackground(int color)
    {
        linearLayout.setBackgroundColor(color);
    }
}
