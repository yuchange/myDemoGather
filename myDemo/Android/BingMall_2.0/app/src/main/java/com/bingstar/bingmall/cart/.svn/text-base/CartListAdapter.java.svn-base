package com.bingstar.bingmall.cart;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseActivity;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.cart.bean.ProductInfoAdd;
import com.bingstar.bingmall.cart.view.CustomLinearLayoutManager;
import com.bingstar.bingmall.cart.view.SoftKeyboardStateHelper;
import com.bingstar.bingmall.goods.GoodsDetailFragment;
import com.bingstar.bingmall.goods.view.DelayTextWatcher;
import com.bingstar.bingmall.goods.view.NumEditText;
import com.bingstar.bingmall.net.BingstarNet;
import com.yunzhi.lib.view.OnSingleClickListener;


import java.util.ArrayList;

/**
 * Created by qianhechen on 17/2/9.
 */

public class CartListAdapter extends BaseRecycleAdapter<ProductInfoAdd, CartListAdapter.ViewHolder> {

    private OnDeleteItem onDeleteItem;

    private OnNumberChange onNumberChange;

    private Context context;

    private String categoryId;


    private InputMethodManager mImm;


    public CartListAdapter(ArrayList<ProductInfoAdd> list) {
        super(list);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getView(parent, viewType);
        context = parent.getContext();
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cart_goods_num_increase.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                int position = viewHolder.getLayoutPosition();
                final ProductInfoAdd productInfo = getItem(position);

                String stock = productInfo.getStock();
                if (!TextUtils.isEmpty(stock)) {
                    int stockNum = Integer.parseInt(stock);
                    int count = Integer.parseInt(productInfo.getCount());
                    //加减不能超出库存
                    if (count >= stockNum) {
                        adjustCount(stock, productInfo, viewHolder);
                        return;
                    }
                }
                productInfo.increaseNum(productInfo, new ProductInfoAdd.UpdateNumCallback() {
                    @Override
                    public void update() {
                        viewHolder.cart_goods_num.setText(productInfo.getCount());
                        Selection.setSelection(viewHolder.cart_goods_num.getText(), viewHolder.cart_goods_num.getText().length());
                        onNumberChange.numberChange();
                    }

                    @Override
                    public void rollback() {
                        viewHolder.cart_goods_num.setText(productInfo.getCount());
                    }
                });
            }
        });
        viewHolder.cart_goods_num_decrease.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                int position = viewHolder.getLayoutPosition();
                final ProductInfoAdd productInfo = getItem(position);
                if (productInfo.getCount().equals("1")) {
                    notifyDataSetChanged();
                    onDeleteItem.delete(position);
                    return;
                }

                String stock = productInfo.getStock();
                if (!TextUtils.isEmpty(stock)) {
                    int stockNum = Integer.parseInt(stock);
                    int count = Integer.parseInt(productInfo.getCount());
                    //加减不能超出库存
                    if (count > stockNum) {
                        adjustCount(stock, productInfo, viewHolder);
                        return;
                    }
                }
                productInfo.decreaseNum(productInfo, new ProductInfoAdd.UpdateNumCallback() {
                    @Override
                    public void update() {
                        viewHolder.cart_goods_num.setText(productInfo.getCount());
                        Selection.setSelection(viewHolder.cart_goods_num.getText(), viewHolder.cart_goods_num.getText().length());
                        onNumberChange.numberChange();
                    }

                    @Override
                    public void rollback() {
                        viewHolder.cart_goods_num.setText(productInfo.getCount());
                    }
                });

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CartListAdapter.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final ProductInfoAdd productInfoAdd = getItem(position);
        if (productInfoAdd == null) {
            return;
        }
        if (holder.cart_goods_num.getTag() != null
                && holder.cart_goods_num.getTag() instanceof TextWatcher) {
            holder.cart_goods_num.removeTextChangedListener((TextWatcher) holder.cart_goods_num.getTag());
        }

        if (!TextUtils.isEmpty(productInfoAdd.getStock()) && productInfoAdd.getStock().equalsIgnoreCase("0")) {
            holder.sellOut.setVisibility(View.VISIBLE);   //库存为0的商品 显示暂无商品图片 点击删除
            holder.sellOut.setImageResource(R.drawable.cart_goods_sellout);
            holder.sellOut.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        onDeleteItem.delete(position);
                        notifyDataSetChanged();
                    }
                    return true;
                }
            });
        } else if (!TextUtils.isEmpty(productInfoAdd.getIf_show()) && productInfoAdd.getIf_show().equalsIgnoreCase("4")) {
            //0：未发布 11：已发布  1:审核通过 2: 审核未通过   3:上架 4：下架   5：删除
            holder.sellOut.setVisibility(View.VISIBLE);   //下架的商品 显示商品已下架图片 点击删除
            holder.sellOut.setImageResource(R.drawable.cart_goods_offshelves);
            holder.sellOut.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        onDeleteItem.delete(position);
                        notifyDataSetChanged();
                    }
                    return true;
                }
            });
        } else {
            holder.sellOut.setVisibility(View.GONE);
        }

        holder.cart_goods_num.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    EditText numText = (EditText) v;
                    numText.setFocusableInTouchMode(true);
                }
                return false;
            }
        });
        //滑动列表时 隐藏键盘
        holder.cart_goods_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                } else {
                    if (mImm == null) {
                        mImm = (InputMethodManager) context
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                    }
                    mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    int position = holder.getLayoutPosition();
                    final ProductInfoAdd productInfo = getItem(position);
                    EditText num = (EditText) v;
                    num.setText(productInfo.getCount()); //恢复原来的数量
                }
            }
        });
        //监听确认按键 检查数据合法性
        holder.cart_goods_num.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        int position = holder.getLayoutPosition();
                        final ProductInfoAdd productInfo = getItem(position);
                        try {
                            String count = String.valueOf(holder.cart_goods_num.getText().toString().trim());
                            if (Integer.parseInt(count) < 1 /*&& !productInfo.getStock().equals("0")*/) {
                                ((BaseActivity) context).showToast(R.string.goods_detail_num_warnL);
                                return true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ((BaseActivity) context).showToast(R.string.goods_detail_num_warnE);
                            return true;
                        }
                        String trim = holder.cart_goods_num.getText().toString().trim();
                        String newTrim = trim.replaceFirst("^0*", ""); //去除开头的零
                        productInfo.editNum(newTrim, productInfo.getCount(), productInfo, new ProductInfoAdd.UpdateNumCallback() {
                            @Override
                            public void update() {
                                holder.cart_goods_num.setText(productInfo.getCount());
                                Selection.setSelection(holder.cart_goods_num.getText(), holder.cart_goods_num.getText().length());
                                onNumberChange.numberChange();
                            }

                            @Override
                            public void rollback() {
                                holder.cart_goods_num.setText(productInfo.getCount());
                                Selection.setSelection(holder.cart_goods_num.getText(), holder.cart_goods_num.getText().length());
                            }
                        });
                        holder.cart_goods_num.clearFocus();
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });


        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    try {
                        int position = holder.getLayoutPosition();
                        final ProductInfoAdd productInfo = getItem(position);
                        int num = Integer.parseInt(s.toString());
                        String stock = productInfo.getStock();
                        if (!TextUtils.isEmpty(stock) && stock.equals(s.toString().trim())) {
                            return;
                        }
                        if (s.toString().trim().equals(productInfo.getCount())) {
                            return;
                        }
                        if (!TextUtils.isEmpty(productInfo.getVirtualCount()) && s.toString().trim().equalsIgnoreCase(productInfo.getVirtualCount())) {
                            return;
                        }
                        if (!TextUtils.isEmpty(stock) && num > Integer.parseInt(stock)) {
                            num = Integer.parseInt(stock);
                            ((BaseActivity) context).showToast(context.getResources().getString(R.string.goods_detail_num_warnH1) + num);
                            holder.cart_goods_num.setText(num + "");
                            Selection.setSelection(holder.cart_goods_num.getText(), holder.cart_goods_num.getText().length());
                        }
                        productInfo.setVirtualCount(String.valueOf(num));
                        holder.cart_goods_num.setText(productInfo.getVirtualCount());
                        Selection.setSelection(holder.cart_goods_num.getText(), holder.cart_goods_num.getText().length());
                    } catch (Exception e) {
                        e.printStackTrace();
                        ((BaseActivity) context).showToast(R.string.goods_detail_num_warnE);
                    }
                }
            }
        };

        switch (productInfoAdd.getIsSelected()) {
            case 1:
                holder.cart_checkbox.setImageResource(R.drawable.cart_checkbox_select);
                break;
            case 2:
                holder.cart_checkbox.setImageResource(R.drawable.cart_checkbox_unselect);
                break;
            default:
                holder.cart_checkbox.setImageResource(R.drawable.cart_checkbox_unselect);
                break;
        }


        holder.cart_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                final ProductInfoAdd productInfo = getItem(position);
                if (productInfo.getIsSelected() == 1) {
                    productInfoAdd.setSelected(2);
                    holder.cart_checkbox.setImageResource(R.drawable.cart_checkbox_unselect);
                } else if (productInfoAdd.getIsSelected() == 2) {
                    productInfoAdd.setSelected(1);
                    holder.cart_checkbox.setImageResource(R.drawable.cart_checkbox_select);
                }
                onNumberChange.numberChange();
            }
        });


        holder.cart_goods_num.addTextChangedListener(watcher);
        holder.cart_goods_num.setTag(watcher);

        holder.cart_checkbox.setVisibility(View.VISIBLE);
        Util.imageLoader(holder.cart_goods_img, productInfoAdd.getPicUrl());
//        String postMoney = Util.checkData(productInfoAdd.getPost_money());
//        if (postMoney.equals("0")||postMoney.equals("0.00")||postMoney.equals("0.0")) {
//            postMoney = "包邮";
//        }
//        holder.cart_goods_freight.setText(context.getResources().getString(R.string.goods_freight) + ':' +
//                context.getResources().getString(R.string.cycle_money) + ' ' + postMoney);
        holder.cart_goods_name.setText(productInfoAdd.getProductName());
        holder.cart_goods_num.setText(productInfoAdd.getCount());
        holder.cart_goods_price.setText(context.getResources().getString(R.string.cycle_money) + Util.checkData(productInfoAdd.getPrice()) + "");
        holder.cart_goods_weight.setText(productInfoAdd.getWeight() + productInfoAdd.getUnit() + "");
        String discountExplain = productInfoAdd.getDiscountExplain();
        if (!TextUtils.isEmpty(discountExplain)) {
            holder.mDiscountExplain.setText(discountExplain);
        }

    }

    public void setOnDeleteItem(OnDeleteItem onDeleteItem) {
        this.onDeleteItem = onDeleteItem;
    }

    class ViewHolder extends BaseRecycleAdapter.ViewHolder {

        ImageView cart_goods_img;
        ImageView cart_goods_num_decrease;
        ImageView cart_goods_num_increase;
        TextView cart_goods_name;
        TextView cart_goods_weight;
        TextView cart_goods_price;
        //        TextView cart_goods_freight;
        EditText cart_goods_num;
        ImageView sellOut;

        TextView mDiscountExplain;

        ImageButton cart_checkbox;

        public ViewHolder(View itemView) {
            super(itemView);
            cart_goods_img = (ImageView) itemView.findViewById(R.id.cart_goods_img);
            sellOut = (ImageView) itemView.findViewById(R.id.cart_goods_sellout);
            cart_goods_num_decrease = (ImageView) itemView.findViewById(R.id.cart_goods_num_decrease);
            cart_goods_num_increase = (ImageView) itemView.findViewById(R.id.cart_goods_num_increase);
            cart_goods_name = (TextView) itemView.findViewById(R.id.cart_goods_name);
            cart_goods_weight = (TextView) itemView.findViewById(R.id.cart_goods_weight);
            cart_goods_price = (TextView) itemView.findViewById(R.id.cart_goods_price);
//            cart_goods_freight = (TextView) itemView.findViewById(R.id.cart_goods_freight);
            cart_goods_num = (EditText) itemView.findViewById(R.id.cart_goods_num);
            mDiscountExplain = (TextView) itemView.findViewById(R.id.cart_goods_discountExplain);
            cart_checkbox = (ImageButton) itemView.findViewById(R.id.cart_checkbox);
        }
    }

    @Override
    public int getItemCount() {
        if (categoryId == null || categoryId.equals("")) {
            return super.getItemCount();
        } else {
            int count = 0;
            for (ProductInfoAdd infoAdd : getList()) {
                if (infoAdd.getCategoryId().equals(categoryId)) {
                    count++;
                }
            }
            return count;
        }
    }

    @Override
    public ProductInfoAdd getItem(int position) {
        if (categoryId == null || categoryId.equals("")) {
            return super.getItem(position);
        } else {
            int count = 0;
            for (ProductInfoAdd infoAdd : getList()) {
                if (infoAdd.getCategoryId().equals(categoryId)) {
                    if (count == position) {
                        return infoAdd;
                    }
                    count++;
                }
            }
            return null;
        }
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.cart_grid_item;
    }

    public void setOnNumberChangeListener(OnNumberChange numberChange) {
        onNumberChange = numberChange;
    }

    OnSelectAllChange mOnSelectAllChange;

    public void setOnSelectAllChange(OnSelectAllChange onSelectAllChange) {
        mOnSelectAllChange = onSelectAllChange;
    }

    interface OnDeleteItem {
        void delete(int position);
    }

    interface OnNumberChange {
        void numberChange();
    }

    interface OnSelectAllChange {
        void selectChange();
    }

    //修改库存
    public void adjustCount(String stock, final ProductInfoAdd productInfo, final ViewHolder viewHolder) {
        ((BaseActivity) context).showToast(context.getResources().getString(R.string.goods_detail_num_warnH1) + stock);
        productInfo.editNum(stock, productInfo.getCount(), productInfo, new ProductInfoAdd.UpdateNumCallback() {
            @Override
            public void update() {
                viewHolder.cart_goods_num.setText(productInfo.getCount());
                viewHolder.cart_goods_num.clearFocus();
                Selection.setSelection(viewHolder.cart_goods_num.getText(), viewHolder.cart_goods_num.getText().length());
                onNumberChange.numberChange();
            }

            @Override
            public void rollback() {
                viewHolder.cart_goods_num.setText(productInfo.getCount());
                viewHolder.cart_goods_num.clearFocus();
                Selection.setSelection(viewHolder.cart_goods_num.getText(), viewHolder.cart_goods_num.getText().length());
            }
        });
    }


    public void selectChange() {
        if (null != categoryId) {
            for (int i = 0; i < getList().size(); i++) {
                ProductInfoAdd productInfoAdd = getList().get(i);
                if (productInfoAdd.getCategoryId().equals(categoryId)) {

                }
            }
        }
    }


}
