package com.haozhang.android.ui.adapter;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhang.android.R;
import com.haozhang.android.modle.WelfareItemDatas;


/**
 * @author HaoZhang
 * @date 2016/7/24.
 */
public class GlideAdapter extends BaseQuickAdapter<WelfareItemDatas> {
    private Activity mContext;
    public GlideAdapter(Activity act) {
        super(R.layout.loader_item_glide, null);
        mContext = act;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WelfareItemDatas welfareItemDatas) {
        Uri uri = Uri.parse(welfareItemDatas.getUrl());
        Glide.with(mContext).load(uri).dontAnimate().centerCrop().into((ImageView) baseViewHolder.getView(R.id.image));
    }
}