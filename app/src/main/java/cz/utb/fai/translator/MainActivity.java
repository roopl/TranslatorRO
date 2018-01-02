package cz.utb.fai.translator;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cz.utb.fai.translator.adapter.HistoryAdapter;
import cz.utb.fai.translator.adapter.ViewPagerAdapter;
public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private List<String> mHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        mHistory = new ArrayList<String>();
        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(new ViewPagerAdapter(this));
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout = (TabLayout)findViewById(R.id.tableLayout);
// adds view pager into tab layout
        mTabLayout.setupWithViewPager(mViewPager);
        new PageHome(this);

        //set recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HistoryAdapter(mHistory);

        mRecyclerView.setAdapter(mAdapter);

    }

    public List<String> getHistoryList() {

        return this.mHistory;
    }

    public RecyclerView.Adapter returnAdapter () {
        return mAdapter;
    }
}
