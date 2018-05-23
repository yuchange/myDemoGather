package com.bingstar.bingmall.sdk.pay.haier;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bingstar.bingmall.R;

/**
 * 功能：
 * Created by yaoyafeng on 17/5/12 下午1:59
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/5/12 下午1:59
 * @modify by reason:{方法名}:{原因}
 */

public class PayResultDialog extends Dialog {

    public static final int TYPE_FAIL = 0;
    public static final int TYPE_SUCCESS = 1;
    public static final int TYPE_TIMEOUT = 2;

    public PayResultDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private int type;
        private View.OnClickListener onClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Set the Dialog message from String
         *
         * @param type
         * @return
         */
        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public Builder setOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        /**
         * Create the custom dialog
         */
        public PayResultDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final PayResultDialog dialog = new PayResultDialog(context,
                    R.style.Dialog);
            View layout = inflater.inflate(R.layout.pay_haier_result_dialog, null);
            TextView content =(TextView) layout.findViewById(R.id.pay_dialog_message);
            ImageView titleImg =(ImageView) layout.findViewById(R.id.pay_dialog_img);
            ImageView checkBtn =(ImageView) layout.findViewById(R.id.pay_dialog_btn);
            if (type == TYPE_FAIL) {
                content.setText(R.string.pay_haier_fail);
                titleImg.setImageResource(R.drawable.pay_haier_fail_icon);
                checkBtn.setImageResource(R.drawable.pay_haier_fail_btn);
            }else if (type == TYPE_SUCCESS){
                content.setText(R.string.pay_haier_success);
                titleImg.setImageResource(R.drawable.pay_haier_success_icon);
                checkBtn.setImageResource(R.drawable.pay_haier_success_btn);
            }else {
                content.setText(R.string.pay_haier_timeout);
                titleImg.setImageResource(R.drawable.pay_haier_timeout_icon);
                checkBtn.setImageResource(R.drawable.pay_haier_timeout_btn);
            }
            if (onClickListener!=null){
                checkBtn.setOnClickListener(onClickListener);
            }
            dialog.setContentView(layout);
            return dialog;
        }

    }
}
