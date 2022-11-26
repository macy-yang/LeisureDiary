package com.scujcc.leisurediary.main;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scujcc.leisurediary.R;
import com.scujcc.leisurediary.ui.diarylist.DiaryListAdapter;
import com.scujcc.leisurediary.ui.diarylist.Recommend;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页faragment
 *
 * @author 杨梦婷
 * time:2022/11/16
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Recommend> homeList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.home_recycler);
        //设置RecyclerView保持固定大小,这样可以优化RecyclerView的性能
        recyclerView.setHasFixedSize(true);

        initData();

        //设置瀑布流布局为2列，垂直方向滑动
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        DiaryListAdapter diaryListAdapter = new DiaryListAdapter(this.getActivity(), homeList);

        //创建分割线对象
        recyclerView.addItemDecoration(new MyDecoration());

        recyclerView.setAdapter(diaryListAdapter);

        diaryListAdapter.setOnItemClickListener(new DiaryListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, Recommend data) {
                //监听事件的业务处理
                Toast.makeText(getActivity(), "我是item", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    //数据源
    private void initData() {
        String[] names = new String[]{"车位查询", "车位预定", "与车间距", "防盗警报", "停车时长", "车位查询", "车位预定", "与车间距", "防盗警报", "停车时长"};
        int[] ImageId = new int[]{R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};
        for (int i = 0; i < names.length; i++) {
            this.homeList.add(new Recommend(names[i], ImageId[i]));
        }
    }

    //分割线的类
    class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int gap = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);//5dp
            outRect.set(gap, gap, gap, gap);
        }
    }

}