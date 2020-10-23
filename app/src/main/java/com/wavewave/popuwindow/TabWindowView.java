package com.wavewave.popuwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

/**
 * @author wavewave
 * @CreateDate: 2020/10/22 10:56 AM
 * @Description:
 * @Version: 1.0
 */
public class TabWindowView extends FrameLayout {
    private Context context;
    private List<String> list;
    private TabLayout mTabLayout;

    public TabWindowView(Context context) {
        this(context, null);
    }

    public TabWindowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.tabStyle);
    }

    public TabWindowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tab_window, null);
        mTabLayout = view.findViewById(R.id.mTabLayout);
        addView(view);
    }

    public void setArray(List<String> list) {
        this.list = list;
        if (list != null && list.size() > 0) {
            if (list.size() > 4) {
                mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            } else {
                mTabLayout.setTabMode(TabLayout.MODE_FIXED);
                mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            }
            for (int i = 0; i < list.size(); i++) {
                TabLayout.Tab tab = mTabLayout.newTab();
                tab.setText(list.get(i) + "");
                tab.setCustomView(getTabView(i, list.get(i) + "", R.mipmap.icon_up_down, R.mipmap.icon_up));
                mTabLayout.addTab(tab);
            }
            mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    Log.d("aaaaaaaaa", "onTabSelected333" + tab.getText());
                    showPOPU(tab);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    Log.d("aaaaaaaaa", "onTabUnselected222" + tab.getText());
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    Log.d("aaaaaaaaa", "onTabReselected111" + tab.getText());
                    showPOPU(tab);
                }
            });
        }
    }

    private void showPOPU(TabLayout.Tab tab) {
        View customView = tab.getCustomView();
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_popu_window, null);
        PopupWindow popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RecyclerView mRecyclerView = inflate.findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new MyAdapter());
        // 外部点击事件
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                        mTextView.setTextColor(ContextCompat.getColor(context, R.color.color_748098));
//                        mTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, icons), null);
            }
        });
        popupWindow.showAsDropDown(customView);
//        popupWindow.showAtLocation(customView, Gravity.NO_GRAVITY, 0, 0);
    }

    /**
     * 设置 tabLayout view
     *
     * @param position
     * @return
     */
    protected View getTabView(int position, String titles, final int icons, final int selectIcons) {
        final LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setGravity(Gravity.CENTER);
        final TextView mTextView = new TextView(context);
        mTextView.setCompoundDrawablePadding(10);
        linearLayout.addView(mTextView);
        mTextView.setText(titles);
//        if (position == 0) {
//            mTextView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
//            mTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, selectIcons), null);
//        } else {
        mTextView.setTextColor(ContextCompat.getColor(context, R.color.color_748098));
        mTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, icons), null);
//        }
        return linearLayout;
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
            textView.setText("测试测试");
            textView.setGravity(Gravity.CENTER_VERTICAL);
            return new MyHolder(textView);
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        }

        class MyHolder extends RecyclerView.ViewHolder {

            public MyHolder(@NonNull View itemView) {
                super(itemView);

            }
        }
    }
}
