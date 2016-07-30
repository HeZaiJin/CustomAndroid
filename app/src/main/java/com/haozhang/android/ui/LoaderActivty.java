package com.haozhang.android.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhang.android.R;
import com.haozhang.android.modle.*;
import com.haozhang.android.modle.WelfareItemDatas;
import com.haozhang.android.rest.RESTClient;
import com.haozhang.android.ui.adapter.FrescoAdapter;
import com.haozhang.android.ui.adapter.GlideAdapter;
import com.haozhang.android.utils.RecycleViewDivider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoaderActivty extends AppCompatActivity {
    SwipeRefreshLayout mRefreshLayout;
    RecyclerView mRecyclerView;
    BaseQuickAdapter mAdapter;
    int mIndex = 1;
    private int mMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_activty);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 15, getResources().getColor(R.color.accent)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        initAdapter(mMode);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mRefreshLayout.setColorSchemeColors(R.color.primary);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    public void refresh() {
        mIndex = 1;
        RESTClient.getWelfares(mIndex, new Callback<BaseData<WelfareItemDatas>>() {
            @Override
            public void onResponse(Call<BaseData<WelfareItemDatas>> call, Response<BaseData<WelfareItemDatas>> response) {
                BaseData<WelfareItemDatas> body = response.body();
                if (null == body) return;
                mAdapter.setNewData(body.getResults());
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<BaseData<WelfareItemDatas>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRefreshLayout.setProgressViewOffset(false, 0, 30);
        mRefreshLayout.setRefreshing(true);
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_loader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        int mode = 0;
        if (itemId == R.id.loader_glide) {
            mAdapter = new GlideAdapter(this);
        } else if (itemId == R.id.loader_fresco) {
            mode = 1;
            mAdapter = new FrescoAdapter();
        }
        if (mMode != mode) {
            initAdapter(mode);
            mRecyclerView.setAdapter(mAdapter);
            mRefreshLayout.setProgressViewOffset(false, 0, 30);
            mRefreshLayout.setRefreshing(true);
            refresh();
        }
        return super.onOptionsItemSelected(item);
    }

    public BaseQuickAdapter initAdapter(int mode) {
        mMode = mode;
        if (mode == 0) {
            mAdapter = new GlideAdapter(this);
        } else {
            mAdapter = new FrescoAdapter();
        }

        mAdapter.openLoadMore(10, true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mIndex++;
                RESTClient.getWelfares(mIndex, new Callback<BaseData<WelfareItemDatas>>() {
                    @Override
                    public void onResponse(Call<BaseData<WelfareItemDatas>> call, Response<BaseData<WelfareItemDatas>> response) {
                        BaseData<WelfareItemDatas> body = response.body();
                        if (null == body) return;
                        mAdapter.notifyDataChangedAfterLoadMore(body.getResults(), true);
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<BaseData<WelfareItemDatas>> call, Throwable t) {

                    }
                });
            }
        });
        return mAdapter;
    }
}
