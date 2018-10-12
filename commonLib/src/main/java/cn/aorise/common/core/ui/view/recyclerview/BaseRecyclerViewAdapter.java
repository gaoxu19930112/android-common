package cn.aorise.common.core.ui.view.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

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
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener, View.OnLongClickListener {
    protected static final int TYPE_HEADER = 0;
    protected static final int TYPE_FOOTER = 1;
    protected static final int TYPE_ITEM = 2;
    protected static final int TYPE_EMPTY = 3;
    protected View mEmptyView;
    protected View mFooterView;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mList;
    protected IItemClickListener<T> mItemClickListener;
    protected IItemLongClickListener<T> mItemLongClickListener;

    public BaseRecyclerViewAdapter(Context context) {
        this.mContext = context;
        this.mInflater = ((Activity) context).getLayoutInflater();
    }

    public BaseRecyclerViewAdapter(Context context, List<T> list) {
        this(context);
        this.mList = list;
    }

    public void setList(List<T> list) {
        mList = list;
        removeEmptyView();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (null != mEmptyView) {
            return TYPE_EMPTY;
        } else {
            if (getFooterCount() == 0) {
                return TYPE_ITEM;
            } else {
                // 有footerview
                if (position == getItemCount() - 1) {
                    return TYPE_FOOTER;
                }
            }
            return TYPE_ITEM;
        }
    }

    public T getItem(int position) {
        int type = getItemViewType(position);
        if (TYPE_ITEM == type) {
            if (null == mList)
                return null;
            return mList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        if (null != mEmptyView) {
            return 1;
        } else {
            if (null == mList) {
                return getFooterCount();
            } else {
                return mList.size() + getFooterCount();
            }
        }
    }

    /**
     * 给databing的item使用
     */
    public static class BindingItemViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingItemViewHolder(View itemView) {
            super(itemView);
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return this.binding;
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public CustomViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 删除列表为空的显示view
     */
    public void removeEmptyView() {
        mEmptyView = null;
        notifyDataSetChanged();
    }

    /**
     * 增加列表为空的显示view
     *
     * @param emptyView view对象
     */
    public void addEmptyView(View emptyView) {
        mEmptyView = emptyView;
        if (null != mList) {
            mList.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 删除列表底部的显示view
     */
    public void removeFooterView() {
        mFooterView = null;
        notifyDataSetChanged();
    }

    /**
     * 增加列表底部的显示view
     *
     * @param footerView view对象
     */
    public void addFooterView(View footerView) {
        mFooterView = footerView;
        notifyDataSetChanged();
    }

    public int getFooterCount() {
        return null != mFooterView ? 1 : 0;
    }

    /**
     * 设置列表点击事件
     *
     * @param itemClickListener 点击事件
     */
    public void setItemClickListener(IItemClickListener<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    /**
     * 设置列表长按事件
     *
     * @param itemLongClickListener 长按事件
     */
    public void setItemLongClickListener(IItemLongClickListener<T> itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onClick(View view) {
        if (null != mItemClickListener) {
            mItemClickListener.onItemClick(view, (T) view.getTag());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean onLongClick(View view) {
        if (null != mItemLongClickListener) {
            mItemLongClickListener.onItemLongClick(view, (T) view.getTag());
        }
        return true;
    }

    /**
     * 列表Item点击事件接口
     *
     * @param <T> 列表数据类型
     */
    public interface IItemClickListener<T> {
        void onItemClick(View view, T t);
    }

    /**
     * 列表Item长按事件接口
     *
     * @param <T> 列表数据类型
     */
    public interface IItemLongClickListener<T> {
        void onItemLongClick(View view, T t);
    }
}
