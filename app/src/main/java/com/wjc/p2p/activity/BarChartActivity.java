package com.wjc.p2p.activity;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wjc.p2p.R;
import com.wjc.p2p.common.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class BarChartActivity extends BaseActivity {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.bar_chart)
    BarChart barChart;

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        ivTopSettings.setVisibility(View.INVISIBLE);
        tvTopTitle.setText("柱状图演示");
    }

    @OnClick(R.id.iv_top_back)
    public void back(View view) {
        closeCurrentActivity();
    }

    private Typeface mTf;
    @Override
    protected void initData() {
        //初始化字体库
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        //图表的描述
        barChart.setDescription("三星note7爆炸事件关注度");
        //设置网格背景
        barChart.setDrawGridBackground(false);
        //是否设置阴影的显示
        barChart.setDrawBarShadow(false);

        //获取x轴
        XAxis xAxis = barChart.getXAxis();
        //设置x轴的显示位置
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        //设置x轴的字体
        xAxis.setTypeface(mTf);
        //是否绘制x轴网格线
        xAxis.setDrawGridLines(false);
        //是否绘制x轴轴线
        xAxis.setDrawAxisLine(true);

        //获取y轴
        YAxis leftAxis = barChart.getAxisLeft();
        //设置y轴的字体
        leftAxis.setTypeface(mTf);
        //参数1：设置显示的区间的个数。参数2：是否均匀分布。fasle:均匀显示区间的端点值。
        leftAxis.setLabelCount(5, false);
        //设置最高的柱状图距离顶端的距离
        leftAxis.setSpaceTop(50f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);//是否显示右边的y轴

        BarData mChartData = generateDataBar();
        mChartData.setValueTypeface(mTf);

        // set data
        barChart.setData(mChartData);

        // do not forget to refresh the chart
//        barChart.invalidate();
        barChart.animateY(700);

    }

    /**
     * 生成柱状图的数据
     *
     * @return
     */
    private BarData generateDataBar() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry((int) (Math.random() * 70) + 30, i));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet ");
        //设置柱状图之间的间距
        d.setBarSpacePercent(20f);
        //设置显示的柱状图的颜色
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        //设置高亮的透明度：当点击柱状图时显示的透明度
        d.setHighLightAlpha(255);

        BarData cd = new BarData(getMonths(), d);
        return cd;
    }
    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");

        return m;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bar_chart;
    }
}
