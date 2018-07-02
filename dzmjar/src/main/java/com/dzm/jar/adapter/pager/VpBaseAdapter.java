package com.dzm.jar.adapter.pager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dzm
 *         date 2017/8/23 14:35
 */

public abstract class VpBaseAdapter<T> extends PagerAdapter {

    protected List<T> list;
    protected SparseArray<BasePagerHolder<T>> sparseArray;
    private OnItemClickListener<T> onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public VpBaseAdapter() {
        this(null);
    }

    public VpBaseAdapter(List<T> list) {
        if (null == list) {
            this.list = new ArrayList<>();
        } else {
            this.list = list;
        }
        sparseArray = new SparseArray<>();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    public String getIcon(int position) {
        return null;
    }

    public List<T> getDatas(){
        return list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePagerHolder<T> holder = sparseArray.get(getSparsePosition(position));
        if (null == holder) {
            holder = onBundHolder(container, position);
            holder.setPosition(position);
            holder.bindDada(getData(position), position);
            sparseArray.put(getSparsePosition(position), holder);
        }
        View view = holder.itemView;
        view.setTag(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    int po = (int) v.getTag();
                    onItemClickListener.onItemClick(list.get(po % list.size()), po,VpBaseAdapter.this);
                }
            }
        });
        container.addView(view);
        return view;
    }

    protected T getData(int position) {
        return list.get(position % list.size());
    }

    public void notifyDataSetChanged(List<T> list) {
        this.list.clear();
        if (null != list)
            this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        sparseArray.clear();
        super.notifyDataSetChanged();
    }

    protected int getSparsePosition(int position) {
        return (position + 10) * 2;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((View) object).setOnClickListener(null);
        container.removeView((View) object);
    }

    protected View inflate(int resorce, ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(resorce, viewGroup, false);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    protected abstract BasePagerHolder<T> onBundHolder(ViewGroup container, int position);

    public void onDestroy() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public interface OnItemClickListener<T> {
        void onItemClick(T t, int position,VpBaseAdapter<T> adapter);
    }

    public static abstract class BasePagerHolder<D> {

        Context mContext;

        SparseArray<View> sparseArray;
        View itemView;
        int position;

        public BasePagerHolder(View itemView) {
            this.itemView = itemView;
            this.mContext = itemView.getContext();
            sparseArray = new SparseArray<>();
            initView();
        }

        protected abstract void initView();

        public <T extends View> T findViewById(int id) {
            T view = (T) sparseArray.get(id);
            if (null == view) {
                view = itemView.findViewById(id);
                sparseArray.append(id, view);
            }
            return view;
        }

        public abstract void bindDada(D t, int position);

        public void onActivityResult(int requestCode, int resultCode, Intent data) {

        }

        void setPosition(int position) {
            this.position = position;

        }

        public int getPosition() {
            return position;
        }

        public void onDestroy() {
            sparseArray.clear();
        }
    }
}
