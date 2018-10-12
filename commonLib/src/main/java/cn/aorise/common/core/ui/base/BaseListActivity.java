package cn.aorise.common.core.ui.base;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import cn.aorise.common.R;
import cn.aorise.common.core.ui.view.recyclerview.BindingRecyclerViewAdapter;
import cn.aorise.common.core.ui.view.recyclerview.LoadMoreListener;
import cn.aorise.common.core.util.AoriseLog;
import cn.aorise.common.databinding.AoriseActivityBaseListBinding;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : ListActivity基类<br> K 是列表适配器类型<br> T 是列表数据类型
 *     version: 1.0
 * </pre>
 */
public abstract class BaseListActivity<K extends BindingRecyclerViewAdapter<T>, T> extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener
        , LoadMoreListener.OnLoadMoreListener {
    private static final String TAG = BaseListActivity.class.getSimpleName();
    protected static final String REFRESH = "refresh";
    protected static final String LOAD_MORE = "load_more";
    protected LoadMoreListener mLoadMoreListener;
    protected AoriseActivityBaseListBinding mBinding;
    protected K mAdapter;
    protected List<T> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initData() {
        AoriseLog.i(TAG, "initData");
        // mList = new ArrayList<>();
        // mAdapter = new BindingHospitalAdapter(this, mList);
        mLoadMoreListener = new LoadMoreListener();
        mLoadMoreListener.setLoadMoreListener(this);
    }

    @Override
    protected void initView() {
        AoriseLog.i(TAG, "initView");
        mBinding = DataBindingUtil.setContentView(this, R.layout.aorise_activity_base_list);
        mBinding.swipeRefresh.setOnRefreshListener(this);

        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.addOnScrollListener(mLoadMoreListener);
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        AoriseLog.i(TAG, "initEvent");
        refresh();
    }

    @Override
    public void onRefresh() {
        AoriseLog.i(TAG, "onRefresh");
        refresh();
    }


    @Override
    public void onLoadMore() {
        loadMore();
    }

    /**
     * UI刷新
     */
    protected void swipeRefresh() {
        mBinding.swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                AoriseLog.i(TAG, "swipeRefresh");
                mBinding.swipeRefresh.setRefreshing(true);
            }
        });
    }

    /**
     * 下拉刷新数据
     */
    protected abstract void refresh();

    /**
     * 上拉加载更多数据
     */
    protected abstract void loadMore();
}
