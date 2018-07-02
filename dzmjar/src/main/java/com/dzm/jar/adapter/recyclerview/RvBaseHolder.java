package com.dzm.jar.adapter.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 *
 * @author dzm
 * date 2017/11/13 15:44
 */

public abstract class RvBaseHolder<D> extends RecyclerView.ViewHolder{

    protected Context mContext;

    private SparseArray<View> sparseArray;

    /**
     *
     * @param itemView view
     */
    public RvBaseHolder(View itemView) {
        super(itemView);
        this.mContext = itemView.getContext();
        sparseArray = new SparseArray<>();
        initView();
    }

    public abstract void initView();

    public <V extends View> V findViewById(int id){
        V view = (V) sparseArray.get(id);
        if(null == view){
            view = itemView.findViewById(id);
            sparseArray.append(id,view);
        }
        return view;
    }

    /**
     * @param d d
     */
    public abstract void bindDada(D d, RvBaseAdapter adapter2, int position);

    public void onItemClick(D d){

    }

    public void onSelect(boolean isSelect){

    }

    public boolean isSelect(){
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void setTag(Object object){

    }
}
