package com.wavewave.popuwindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> stringList = new ArrayList<>();
    private TitleFilterView filterView;
    private ImageView imageView;
    private View view_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stringList.add("拍卖分类");
        stringList.add("拍品年份");
        stringList.add("拍卖公司");
        stringList.add("高级筛选");
        initView();
        initLineView();
    }

    private void initLineView() {
        view_line = findViewById(R.id.view_line);
        filterView = findViewById(R.id.myLineView);
        filterView.setData(stringList);
        filterView.addTitleFilterInterface(new TitleFilterView.TitleFilterInterface() {
            @Override
            public void onItemClickTitle(int position, TextView textView, ImageView imageView) {
                MainActivity.this.imageView = imageView;
                imageView.setImageResource(R.mipmap.icon_up);
                showPopupWindow();
            }
        });
    }

    private void showPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_popu_window, null);
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        RecyclerView mRecyclerView = view.findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new TitleAdapter());

        RecyclerView mRecyclerViewRight = view.findViewById(R.id.mRecyclerViewRight);
        mRecyclerViewRight.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewRight.setAdapter(new TitleAdapter());

        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (imageView != null)
                    imageView.setImageResource(R.mipmap.icon_up_down);


//                WindowManager.LayoutParams lp = getWindow().getAttributes();
//                lp.alpha = 1.0f; //0.0-1.0
//                getWindow().setAttributes(lp);
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
        popupWindow.showAsDropDown(view_line);
//        //变暗
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = 0.6f; //0.0-1.0
//        getWindow().setAttributes(lp);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void initView() {
//        TabWindowView mTabWindowView = findViewById(R.id.mTabWindowView);

//        mTabWindowView.setArray(stringList);
    }
}