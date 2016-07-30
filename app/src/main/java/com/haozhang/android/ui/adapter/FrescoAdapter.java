package com.haozhang.android.ui.adapter;


import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.haozhang.android.R;
import com.haozhang.android.modle.WelfareItemDatas;


/**
 * @author HaoZhang
 * @date 2016/7/24.
 */
public class FrescoAdapter extends BaseQuickAdapter<WelfareItemDatas> {

    public FrescoAdapter() {
        super(R.layout.loader_item_fresco, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WelfareItemDatas welfareItemDatas) {
        Uri uri = Uri.parse(welfareItemDatas.getUrl());
        SimpleDraweeView sdv = baseViewHolder.getView(R.id.img);
        sdv.setImageURI(uri);
    }
}
