package cn.aorise.common.core.ui.view.recyclerview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : RecyclerView.Adapter基类
 *     version: 1.0
 * </pre>
 */
public abstract class BindingRecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T> {

    public BindingRecyclerViewAdapter(Context context, List<T> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    getLayoutId(), parent, false);
            binding.getRoot().setOnClickListener(this);
            binding.getRoot().setOnLongClickListener(this);
            BindingItemViewHolder holder = new BindingItemViewHolder(binding.getRoot());
            holder.setBinding(binding);
            return holder;
        } else if (viewType == TYPE_EMPTY) {
            return new CustomViewHolder(mEmptyView);
        } else if (viewType == TYPE_FOOTER) {
            return new CustomViewHolder(mFooterView);
        }
        throw new RuntimeException("there is no type that matches the type "
                + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // 绑定数据
        if (holder instanceof BindingItemViewHolder) {
            BindingItemViewHolder itemViewHolder = (BindingItemViewHolder) holder;
            itemViewHolder.itemView.setTag(mList.get(position));
            itemViewHolder.getBinding().setVariable(getVariableId(), mList.get(position));
            itemViewHolder.getBinding().executePendingBindings();
        }
    }

    /**
     * 列表布局ID
     *
     * @return
     */
    protected abstract
    @LayoutRes
    int getLayoutId();

    /**
     * 列表布局binding的变量ID
     *
     * @return
     */
    protected abstract int getVariableId();
}
