package com.wjc.p2p.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.wjc.p2p.R;
import com.wjc.p2p.common.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class LineChartActivity extends BaseActivity {
    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.line_chart)
    com.github.mikephil.charting.charts.LineChart lineChart;

    private Typeface mTf;


    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        ivTopSettings.setVisibility(View.INVISIBLE);
        tvTopTitle.setText("折线图演示");
    }

    @OnClick(R.id.iv_top_back)
    public void back(View view){
        closeCurrentActivity();
    }

    @Override
    protected void initData() {
        //初始化字体库
        mTf = Typeface.createFromAsset(this.getAssets(), "OpenSans-Regular.ttf");
        //设置图表的描述
        lineChart.setDescription("王宝强事件的关注度曲线");
        //是否绘制网格背景
        lineChart.setDrawGridBackground(false);

        //获取图表的x轴
        XAxis xAxis = lineChart.getXAxis();
        //设置x轴的显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置x轴字体
        xAxis.setTypeface(mTf);
        //是否绘制x轴网格线
        xAxis.setDrawGridLines(false);
        //是否绘制x轴轴线
        xAxis.setDrawAxisLine(true);

        //获取左边的y轴
        YAxis leftAxis = lineChart.getAxisLeft();
        //设置字体
        leftAxis.setTypeface(mTf);
        //参数1：分为5个区间段。参数2：是否需要均匀显示。false：要均匀分布区间的端点值。true:不需要均匀分布
        leftAxis.setLabelCount(10, false);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);//设置右边的y轴是否显示

        // set data
        lineChart.setData(generateDataLine());

        // do not forget to refresh the chart
        // lineChart.invalidate();
        lineChart.animateX(750);

    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private LineData generateDataLine() {

        ArrayList<Entry> e1 = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            e1.add(new Entry((int) (Math.random() * 65) + 40, i));
        }

        LineDataSet d1 = new LineDataSet(e1, "New DataSet ");
        //折线的宽度
        d1.setLineWidth(4.5f);
        //设置圆的大小
        d1.setCircleSize(4.5f);
        //设置高亮的显示。点击折线显示的坐标线的颜色
        d1.setHighLightColor(Color.rgb(0, 117, 117));
        //是否显示折线点处的数值
        d1.setDrawValues(true);

        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);

        LineData cd = new LineData(getMonths(), sets);
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
        return R.layout.activity_line_chart;
    }

}
