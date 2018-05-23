package com.zyf.bings.bingos.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/9 上午11:09
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/9 上午11:09
 * @modify by reason:{方法名}:{原因}
 */
public abstract class BaseRecycleAdapter<T, VH extends BaseRecycleAdapter.ViewHolder> extends RecyclerView.Adapter<VH> {

    private ArrayList<T> list;

    private OnItemClickListener itemClickListener;

    public BaseRecycleAdapter(ArrayList<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("list cant be null");
        }
        this.list = list;
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        holder.itemView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (itemClickListener!=null) {
                    itemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public T getItem(int position) {
        return list.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setItemClickListener(OnItemClickListener clickListener) {
        itemClickListener = clickListener;
    }

    public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

//    protected View getView(ViewGroup viewGroup,int viewType){
//        return (LayoutInflater.from(viewGroup.getContext()).inflate(getItemLayoutId(viewType), viewGroup, false));
//    }

    /**
     * 获取布局
     *
//     * @param viewType 类型
     * @return 资源Id
     */
//    protected abstract int getItemLayoutId(int viewType);

    public List<T> getList() {
        return list;
    }
}
