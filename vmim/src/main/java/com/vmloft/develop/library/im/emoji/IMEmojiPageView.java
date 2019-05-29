package com.vmloft.develop.library.im.emoji;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.vmloft.develop.library.tools.adapter.VMAdapter;
import com.vmloft.develop.library.tools.utils.VMDimen;

/**
 * Created by lzan13 on 2019/05/29 15:00
 *
 * 表情分组列表页面控件
 */
public class IMEmojiPageView extends RelativeLayout {

    // 实现表情网格布局
    private RecyclerView mRecyclerView;
    private IMEmojiAdapter mAdapter;

    // 当前页面展示哪个表情包
    private IMEmojiGroup mEmojiGroup;

    // 动态添加到布局中的空视图
    private View emptyView;
    // 表情网格显示多少列
    private int mColumnCount;

    /**
     * 默认列数的构造方法
     *
     * @param context
     * @param group
     */
    public IMEmojiPageView(Context context, IMEmojiGroup group) {
        this(context, group, IMEmojiManager.IM_EMOJI_COLUMN_COUNT_DEFAULT);
    }

    /**
     * 动态设置网格列的构造方法
     *
     * @param context
     * @param group
     * @param count
     */
    public IMEmojiPageView(Context context, IMEmojiGroup group, int count) {
        super(context);
        mEmojiGroup = group;
        mColumnCount = count;

        init();
    }

    /**
     * 获取当前页面加载的表情组
     */
    public IMEmojiGroup getEmojiGroup() {
        return mEmojiGroup;
    }

    private void init() {
        if (mEmojiGroup.getEmojiCount() <= 0) {
        } else {
            initRecyclerView();
        }
    }

    /**
     * 初始化表情列表，加载表情数据
     */
    private void initRecyclerView() {
        mRecyclerView = new RecyclerView(getContext());

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (mEmojiGroup.isEmoji) {
            params.topMargin = VMDimen.dp2px(4);
        }
        addView(mRecyclerView, params);

        mAdapter = new IMEmojiAdapter(getContext(), mEmojiGroup.getEmojiItemList());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(new VMAdapter.IClickListener() {
            @Override
            public void onItemAction(int action, Object object) {
                if (mInnerListener != null) {
                    mInnerListener.onEmojiClick(mEmojiGroup, (IMEmojiItem) object);
                }
            }

            @Override
            public boolean onItemLongAction(int action, Object object) {
                return false;
            }
        });

    }

    /**
     * ----------------------- 表情内部点击接口 -----------------------
     */
    private EmojiInnerListener mInnerListener;

    public void setEmojiInnerListener(EmojiInnerListener listener) {
        mInnerListener = listener;
    }

    /**
     * 表情点击回调接口
     */
    public interface EmojiInnerListener {
        void onEmojiClick(IMEmojiGroup group, IMEmojiItem item);
    }
}
