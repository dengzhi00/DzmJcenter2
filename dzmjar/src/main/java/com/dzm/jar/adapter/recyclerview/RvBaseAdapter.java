package com.dzm.jar.adapter.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dzm
 * date 2017/8/10 16:35
 * RecyclerView adapter
 */

public abstract class RvBaseAdapter<D> extends RecyclerView.Adapter<RvBaseHolder<D>> implements View.OnClickListener {

    private static final int TYPE_MANAGER_STAGGERED_GRID = 3;
    private static final int TYPE_HEADER = 7898;
    private static final int TYPE_FOOTER = 7899;

    private LayoutInflater mLayoutInflater;

    private List<D> mDatas;

    private OnItemClickListener<D> mOnItemClickListener;

    protected Context mContext;

    private List<View> mHeaders = new ArrayList<>();
    private List<View> mFooters = new ArrayList<>();

    private int mManagerType;

    public RvBaseAdapter(Context context, OnItemClickListener<D> onItemClickListener) {
        this(context,null,onItemClickListener);
    }

    public RvBaseAdapter(Context context, List<D> datas, OnItemClickListener<D> onItemClickListener) {
        if (null == datas) {
            mDatas = new ArrayList<>();
        } else {
            mDatas = datas;
        }
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mOnItemClickListener = onItemClickListener;
//        registerAdapterDataObserver(adapterDataObserver);
    }

    public List<D> getmDatas() {
        return mDatas;
    }

    public void addHeaderView(View view,RecyclerView recyclerView){
        mHeaders.clear();
        mHeaders.add(view);
        if(null == recyclerView){
            return;
        }
        final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager){
            ((GridLayoutManager) manager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return isHeader(position)?((GridLayoutManager) manager).getSpanCount():1;
                }
            });
        }
    }

    public void removeHeaderView(){
        mHeaders.clear();
        refresh();
    }


    private int getHeadSize() {
        return mHeaders.size();
    }

    public int getFootSize() {
        return mFooters.size();
    }

    private boolean isHeader(int position) {
        return (position < mHeaders.size());
    }

    private boolean isFooter(int position) {
        return (position >= mHeaders.size() + getItemCountHF());
    }

    @Override
    public RvBaseHolder<D> onCreateViewHolder(ViewGroup parent, int type) {
        if (type != TYPE_HEADER && type != TYPE_FOOTER) {
            return initViewHolder(parent, type);
            // else we have a header/footer
        } else {
            // create a new framelayout, or inflate from a resource
            FrameLayout frameLayout = new FrameLayout(parent.getContext());
            // make sure it fills the space
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new HeaderFooterViewHolder<>(frameLayout);
        }
    }

    @Override
    public void onBindViewHolder(RvBaseHolder<D> holder, int position) {
        if (null == holder) return;
        if (isHeader(position)) {
            View v = mHeaders.get(position);
            // add our view to a header view and display it
            prepareHeaderFooter((HeaderFooterViewHolder) holder, v);
        } else if (isFooter(position)) {
            View v = mFooters.get(position - getItemCountHF() - mHeaders.size());
            // add our view to a footer view and display it
            prepareHeaderFooter((HeaderFooterViewHolder) holder, v);
        } else {
            position = position - getHeadSize();
            holder.itemView.setTag(holder);
            holder.itemView.setOnClickListener(this);
            holder.bindDada(mDatas.get(position%mDatas.size()), this, position);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return TYPE_HEADER;
        } else if (isFooter(position)) {
            return TYPE_FOOTER;
        }
        return initViewType(position - getHeadSize());
    }

    private void prepareHeaderFooter(HeaderFooterViewHolder vh, View view) {

        // if it's a staggered grid, span the whole layout
        if (mManagerType == TYPE_MANAGER_STAGGERED_GRID) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setFullSpan(true);
            vh.itemView.setLayoutParams(layoutParams);
        }

        // if the view already belongs to another layout, remove it
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }

        // empty out our FrameLayout and replace with our header/footer
        vh.base.removeAllViews();
        vh.base.addView(view);

    }

    private static class HeaderFooterViewHolder<D> extends RvBaseHolder<D> {
        FrameLayout base;

        HeaderFooterViewHolder(View itemView) {
            super(itemView);
            base = (FrameLayout) itemView;
        }

        @Override
        public void initView() {

        }

        @Override
        public void bindDada(D s, RvBaseAdapter adapter2, int position) {

        }
    }

    @Override
    public int getItemCount() {
        return mHeaders.size() + getItemCountHF() + mFooters.size();
    }

    private int getItemCountHF() {
        return mDatas == null ? 0 : mDatas.size();
    }

    protected int initViewType(int position){
        return position;
    }

    protected abstract RvBaseHolder<D> initViewHolder(ViewGroup parent, int viewType);

    protected View inflate(int resorce, ViewGroup viewGroup) {
        return mLayoutInflater.inflate(resorce, viewGroup, false);
    }

    @Override
    public void onClick(View v) {
        RvBaseHolder po = (RvBaseHolder) v.getTag();
        D d = mDatas.get(po.getAdapterPosition()-getHeadSize());
        po.onItemClick(d);
        onItemClick(d, po, v, this);
    }

    public void onItemClick(D data, RvBaseHolder po, View view, RvBaseAdapter adapter) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.itemClick(data, null==po?-1:po.getAdapterPosition()-getHeadSize(), view, adapter);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void refresh() {
        notifyDataSetChanged();
    }

    public void refresh(List<D> ls) {
        if(null != ls)
            mDatas.addAll(ls);
        refresh();
    }

    public void refreshNew(List<D> ls) {
        mDatas.clear();
        refresh(ls);
    }

    public void clearData(){
        mDatas.clear();
    }

    public void addFooter(View footer, RecyclerView recyclerView) {
        if (!mFooters.contains(footer)) {
            mFooters.add(footer);
            // animate
            notifyItemInserted(mHeaders.size() + getItemCountHF() + mFooters.size() - 1);
            if(null == recyclerView){
                return;
            }
            final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if(manager instanceof GridLayoutManager){
                ((GridLayoutManager) manager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return isFooter(position)?((GridLayoutManager) manager).getSpanCount():1;
                    }
                });
            }
        }
    }

    public void removeFooter(View footer) {
        if (mFooters.contains(footer)) {
            // animate
            notifyItemRemoved(mHeaders.size() + getItemCountHF() + mFooters.indexOf(footer));
            mFooters.remove(footer);
        }
    }

    private RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart + getHeadSize(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart + getHeadSize(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart + getHeadSize(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            notifyItemMoved(fromPosition + getHeadSize(), toPosition + getHeadSize());
        }
    };
}
