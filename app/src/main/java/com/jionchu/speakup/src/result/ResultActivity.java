package com.jionchu.speakup.src.result;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jionchu.speakup.R;
import com.jionchu.speakup.src.ApplicationClass;
import com.jionchu.speakup.src.BaseActivity;

import java.util.ArrayList;

public class ResultActivity extends BaseActivity {

    private WebView mWebView;
    private BarChart mBarChart;
    private PieChart mPieChart;
    private ArrayList<Integer> mBarList;
    private ArrayList<Integer> mPieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvTitle = findViewById(R.id.result_tv_title);
        mWebView = findViewById(R.id.result_webview);
        mBarChart = findViewById(R.id.result_barchart);
        mPieChart = findViewById(R.id.result_piechart);

        tvTitle.setText(getIntent().getStringExtra("assignmentName"));

        mBarList = new ArrayList<>();
        mPieList = new ArrayList<>();

        mBarList.add(10);
        mBarList.add(20);
        mBarList.add(25);
        setBarChart(mBarList, mBarChart);

        mPieList.add(10);
        mPieList.add(20);
        mPieList.add(25);
        setPieChart(mPieList, mPieChart);

        String result = "<html>\n" +
                "  <head>\n" +
                "    <title>결과</title>\n" +
                "  </head>\n" +
                "    <body>\n" +
                "     <font size=1 color=blue>(15초).. </font><font size=3 color=black>게이오기주쿠대학 야마모토 다치고 교수는 헌법 13조라는 관점에서 문제를 제기하고 있습니다 일본의 헌법 13조에 따르면 모든 국민은 개인으로서 존중받아야 권리가 있다라고 쓰여 있는데요 </font><font size=1 color=blue>(2초).. </font><font size=3 color=black>디지털 학습을 한 ai로 개인의 실체가 개인의 그런 정보에 점점 더 접근해야하는 프로파일링 기술이 점점 더 발전하고 있습니다 </font><font size=1 color=blue>(1초).. </font><font size=3 color=black>이는 인재채용을 할 때나 신용등급을 </font><font size=1 color=blue>(2초).. </font><font size=3 color=red>어(추임새) </font><font size=3 color=black>파악하는 등 금융 서비스에서도 활용이 가능하다 </font>\n" +
                "    </body>\n" +
                "</html>";
        result = result + "<br></br>"+result;
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.loadData(result, "text/html", "UTF-8");
        int assignmentId = ApplicationClass.sSharedPreferences.getInt("assignmentId", 0);
    }

    public void customOnClick(View v) {
        if (v.getId() == R.id.result_iv_back) {
            finish();
        }
    }

    private void setBarChart(ArrayList<Integer> countList, BarChart barChart) {
        ArrayList<BarEntry> entryList = new ArrayList();

        for (int i=0;i<countList.size();i++) {
            entryList.add(new BarEntry(countList.get(i),i));
        }

        ArrayList<String> filler = new ArrayList<>();
        filler.add("음");
        filler.add("그");
        filler.add("어");

        barChart.animateY(1000);
        barChart.setMinimumHeight(500);
        BarDataSet barDataSet = new BarDataSet(entryList, "filler");
        BarData data = new BarData(filler, barDataSet);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setData(data);
    }

    private void setPieChart(ArrayList<Integer> countList, PieChart pieChart) {
        ArrayList<Entry> entryList = new ArrayList<>();
        for (int i=0;i<countList.size();i++) {
            entryList.add(new Entry(countList.get(i),i));
        }
        PieDataSet pieDatSet = new PieDataSet(entryList, "silence");

        ArrayList<String> type = new ArrayList<>();
        type.add("개시지연시간");
        type.add("침묵");
        type.add("발화");

        pieChart.setMinimumHeight(700);
        PieData data = new PieData(type, pieDatSet);
        pieChart.setData(data);
        pieDatSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(1000,1000);
    }
}