package com.zyf.bings.bingos.login.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.activity.MainActivity;
import com.zyf.bings.bingos.base.BaseFragment;
import com.zyf.bings.bingos.http.CartCountClient;
import com.zyf.bings.bingos.login.LoginPresenter;
import com.zyf.bings.bingos.manager.ManagerFragment;
import com.zyf.bings.bingos.utils.CommonUtils;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.ImageUtils;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.TimeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zyf.bings.bingos_libnet.OkGoUtils.getSign;


/**
 * Created by Administrator on 2017/8/24.
 */

public class SweepCodeFragment extends BaseFragment implements View.OnClickListener, ISweepCodeFragment {


    private SweepCodeFragment mFragment;
    private Button mBtnSeweepCodeLogin;
    private LinearLayout mLlSweepCode;
    private LinearLayout mRlQrCode;
    private ImageView mIvQrCode;
    private LoginPresenter mLoginPresenter;
    private TextView mTvLoginState;
    private Button mBtnReLogin;
    private static String comeFrom;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sweep_code, container, false);
        setPresenter();
        initView(view);
        initListener();


        return view;
    }

    private void initListener() {
        mBtnSeweepCodeLogin.setOnClickListener(this);
        mBtnReLogin.setOnClickListener(this);
        mTvLoginState.setOnClickListener(this);


    }

    private void initView(View view) {
        mBtnSeweepCodeLogin = (Button) view.findViewById(R.id.btn_sweep_code_login);
        mLlSweepCode = (LinearLayout) view.findViewById(R.id.ll_sweep_code);
        //mRlQrCode = (RelativeLayout) view.findViewById(R.id.rl_qr_code);
        mRlQrCode = (LinearLayout) view.findViewById(R.id.rl_qr_code);
        mIvQrCode = (ImageView) view.findViewById(R.id.iv_qr_code);
        mBtnReLogin = (Button) view.findViewById(R.id.btn_re_login);
        mTvLoginState = (TextView) view.findViewById(R.id.tv_login_state);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sweep_code_login:
                mLlSweepCode.setVisibility(View.GONE);
                mRlQrCode.setVisibility(View.VISIBLE);
                String weChatUrl = getWeChatUrl();
                Log.i("demo", "生成的Url" + weChatUrl);

                Bitmap bitmap = ImageUtils.encodeWechatBitmap(weChatUrl);//根据url和mac生成二维码
                if (bitmap != null) {

                    mIvQrCode.setImageBitmap(bitmap);

                }

                mLoginPresenter.sweepCodeLogin();//开启轮询接口

                break;

            case R.id.tv_login_state://登录超时重新登录

                mRlQrCode.setVisibility(View.VISIBLE);
                mLoginPresenter.sweepCodeLogin();
                mTvLoginState.setVisibility(View.GONE);
                break;

            default:
                break;

        }


    }


    public static void start(int resId, FragmentManager manager, String from) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "SweepCode";
        comeFrom = from;
        SweepCodeFragment fragment = (SweepCodeFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            fragment = new SweepCodeFragment();
            transaction.add(resId, fragment, TAG);

        } else {
            if (!fragment.isHidden()) {
                transaction.commitAllowingStateLoss();
                return;
            }
            Log.i("OneKeyCart", "fragemt不为空");
            transaction.show(fragment);
        }
        transaction.addToBackStack(null);
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof SweepCodeFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void setPresenter() {
        mLoginPresenter = new LoginPresenter(this);
    }

    @Override
    public void loginSuccess() {

        mRlQrCode.setVisibility(View.GONE);
        showToast("登录成功");
        CartCountClient.getCartCount();//获得购物车数量
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        //   fragmentTransaction.remove(this);
        goFromFragment(comeFrom);
        // getActivity().onBackPressed();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void goFromFragment(String comeFrom) {
        getActivity().onBackPressed();

        switch (comeFrom) {
            case Config.FROM_MANAGER://去账户界面
                Log.i("SweepCode", "comeFrom" + comeFrom);
                if (mContext != null) {
                    ManagerFragment.start(R.id.fl_container, ((MainActivity) mContext).getSupportFragmentManager());

                }

                break;

            default:
                break;
        }


    }

    @Override
    public void loginFailed(int code, String error) {
        mTvLoginState.setVisibility(View.VISIBLE);
        if (code == 1) {//登录超时
            showToast(error);
            mTvLoginState.setText("登录超时，点击重新登录");
        } else {
            showToast(error);
            mTvLoginState.setText("登录失败，点击重新登录");

        }
        mRlQrCode.setVisibility(View.GONE);

        // mTvLoginSuccess.setText("登录失败");
        // Log.i("SweepCodeFragment", "执行了");
//      //  RegistFragment.start(R.id.rl_container,getActivity().getSupportFragmentManager());

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoginPresenter.unBind();
    }

    public String getWeChatUrl() {

        String timeStamap = TimeUtil.getTimeBIZService();
        Map<String, String> mapData = new HashMap<>();

        mapData.put(BingNetStates.CUSTOME_CODE, "bsj123456");
        mapData.put(BingNetStates.BIZ_SOURCE, "123456");
        mapData.put(BingNetStates.TIMESTAMP, timeStamap);
        mapData.put(BingNetStates.METHOD, "weixin.qrcode");
        String sign = getSign(mapData);
        //http://store.bingstar.com.cn/bingstar-mobile
        String weChatUrl = "http://wxmall.bingstar.com.cn/weChat/weChat/qrcode?mac=" + CommonUtils.getWifiMac() + "&customeCode=bsj123456&bizSource=123456&timestamp=" + timeStamap + "&method=weixin.qrcode&sign=" + sign;


        return weChatUrl;


    }
}
