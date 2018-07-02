package com.dzm.jar.adapter.recyclerview;

import android.view.View;

/**
 *
 * @author dzm
 * date 2017/11/8 17:23
 */

public interface OnItemClickListener<D> {
    void itemClick(D data, int position, View view, RvBaseAdapter<D> adapter);
}
