package andfans.com.mylbs.PopWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import andfans.com.mylbs.R;

/**
 * Created by 82661 on 2016/12/11.
 */

public class EditChePaiHaoPopWindow extends PopupWindow{
    private View mainView;
    public EditChePaiHaoPopWindow(Context context, View.OnClickListener listener) {
        super(context);
        mainView = LayoutInflater.from(context).inflate(R.layout.edit_chepaihao, null);
        setContentView(mainView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x88000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.PopupAnimation);
    }
}
