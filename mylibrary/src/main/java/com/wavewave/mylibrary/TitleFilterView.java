package com.wavewave.mylibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

/**
 * @author wavewave
 * @CreateDate: 2020/10/22 3:23 PM
 * @Description: 类似TabLayout 没有下划线，视图自动适配居中，当超过一屏自动左右滑动
 * 可以自己设置默认间距
 * @Version: 1.0
 */
public class TitleFilterView extends HorizontalScrollView {

    private TitleLinearLayout mTitleLinearLayout;
    /**
     * 默认标签间距，当不超过屏幕会平均分
     */
    private int textViewMarginLeft = 10;
    /**
     * 默认高度
     */
    private int itemHeight = 88;
    private TitleFilterInterface titleFilterInterface;

    public void addTitleFilterInterface(TitleFilterInterface titleFilterInterface) {
        this.titleFilterInterface = titleFilterInterface;
    }

    public TitleFilterView(Context context) {
        this(context, null);
    }

    public TitleFilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleFilterView);
        textViewMarginLeft = typedArray.getInt(R.styleable.TitleFilterView_itemMargin, textViewMarginLeft);
        itemHeight = typedArray.getInt(R.styleable.TitleFilterView_titleHeight, itemHeight);
    }

    private void init() {
        mTitleLinearLayout = new TitleLinearLayout(getContext());
        mTitleLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        super.addView(mTitleLinearLayout, 0,
                new HorizontalScrollView.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

    }

    @SuppressLint("NewApi")
    public void setData(List<String> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        mTitleLinearLayout.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
            int[] attrs = new int[]{android.R.attr.selectableItemBackground};
            TypedArray ta = getContext().obtainStyledAttributes(attrs);
            Drawable mDefaultFocusHighlightCache = ta.getDrawable(0);
            ta.recycle();
            linearLayout.setForeground(mDefaultFocusHighlightCache);

            linearLayout.setGravity(Gravity.CENTER);
            final ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.mipmap.icon_up_down);
            imageView.setPadding(5, 0, 0, 0);
            final TextView mTextView = new TextView(getContext());
//            mTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
            mTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, itemHeight));
            mTextView.setTextColor(Color.BLACK);
            mTextView.setGravity(Gravity.CENTER);
            mTextView.setText(list.get(i));

            linearLayout.setPadding(textViewMarginLeft, 0, textViewMarginLeft, 0);
            linearLayout.addView(mTextView);
            linearLayout.addView(imageView);
            final int finalI = i;
            linearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (titleFilterInterface != null) {
                        titleFilterInterface.onItemClickTitle(finalI, mTextView, imageView);
                    }
                }
            });
            mTitleLinearLayout.addView(linearLayout);
        }
    }

    public class TitleLinearLayout extends LinearLayout {
        private int maxWidth;
        private int currentWidth = 0;
        private boolean isLine = true;

        public TitleLinearLayout(Context context) {
            super(context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int childCount = getChildCount();
            maxWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
            if (childCount > 0) {
                currentWidth = 0;
                for (int i = 0; i < childCount; i++) {
                    Log.d("aaaaaa", "currentWidth::" + currentWidth);
                    View childAt = getChildAt(i);
                    measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
                    if (maxWidth <= currentWidth + childAt.getMeasuredWidth()) {
                        isLine = false;
                        break;
                    } else {
                        isLine = true;
                        currentWidth += childAt.getMeasuredWidth();
                    }
                }
                Log.d("aaaaaa", "isLine::" + isLine);

            }
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            if (isLine) {//一行可以展示完毕
                int childCount = getChildCount();
                int addChildWidth = (maxWidth - currentWidth) / childCount;
                for (int i = 0; i < getChildCount(); i++) {
                    View childAt = getChildAt(i);
                    int paddingLeft = textViewMarginLeft + addChildWidth / 2;
                    int paddingTop = childAt.getPaddingTop();
                    int paddingRight = textViewMarginLeft + addChildWidth / 2;
                    int paddingBottom = childAt.getPaddingBottom();
                    Log.d("aaaaa", "paddingLeft:" + paddingLeft + "  paddingTop:" + paddingTop + "  paddingRight:" + paddingRight + "  paddingBottom:" + paddingBottom);
                    childAt.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                }
                Log.d("aaaaaa", "onLayout isLine::" + isLine);
            } else {

            }
        }
    }

    public interface TitleFilterInterface {
        void onItemClickTitle(int position, TextView textView, ImageView imageView);
    }
}
