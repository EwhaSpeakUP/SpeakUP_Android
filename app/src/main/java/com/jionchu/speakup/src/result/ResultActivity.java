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
import com.jionchu.speakup.src.result.interfaces.ResultActivityView;
import com.jionchu.speakup.src.result.models.ResultResult;

import java.util.ArrayList;

public class ResultActivity extends BaseActivity implements ResultActivityView {

    private WebView mWebView;
    private BarChart mBarChart;
    private PieChart mPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvTitle = findViewById(R.id.result_tv_title);
        mWebView = findViewById(R.id.result_webview);
        mBarChart = findViewById(R.id.result_barchart);
        mPieChart = findViewById(R.id.result_piechart);

        tvTitle.setText(getIntent().getStringExtra("assignmentName"));
        tryGetResult(ApplicationClass.sSharedPreferences.getInt("assignmentId", 0));

        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDisplayZoomControls(false);
    }

    public void customOnClick(View v) {
        if (v.getId() == R.id.result_iv_back) {
            finish();
        }
    }

    private void tryGetResult(int assignmentId) {
        showProgressDialog();
        final ResultService resultService = new ResultService(this);
        resultService.getResult(assignmentId);
    }

    private void setHtml(ArrayList<String> htmlList) {
        String result = "";
        for (int i=0;i<htmlList.size();i++) {
            result = result + htmlList.get(i) + "<br></br>";
        }
        mWebView.loadData(result, "text/html", "UTF-8");
    }

    private void setBarChart(ArrayList<Integer> countList, BarChart barChart) {
        ArrayList<BarEntry> entryList = new ArrayList<>();
        for (int i=0;i<3;i++) {
            entryList.add(new BarEntry(countList.get(i),i));
        }

        ArrayList<String> filler = new ArrayList<>();
        filler.add("음");
        filler.add("그");
        filler.add("어");

        barChart.animateY(1000);
        barChart.setMinimumHeight(500);
        barChart.setDescription(null);
        BarDataSet barDataSet = new BarDataSet(entryList, "filler");
        barDataSet.setValueTextSize(11);
        BarData data = new BarData(filler, barDataSet);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setData(data);
    }

    private void setPieChart(ArrayList<Integer> countList, PieChart pieChart) {
        ArrayList<Entry> entryList = new ArrayList<>();
        for (int i=0;i<countList.size();i++) {
            entryList.add(new Entry(countList.get(i),i));
        }

        ArrayList<String> type = new ArrayList<>();
        type.add("개시지연시간");
        type.add("침묵");
        type.add("발화");

        pieChart.animateXY(1000,1000);
        pieChart.setMinimumHeight(700);
        pieChart.setDescription(null);
        PieDataSet pieDataSet = new PieDataSet(entryList, "");
        pieDataSet.setValueTextSize(11);
        PieData data = new PieData(type, pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setData(data);
    }

    @Override
    public void getResultSuccess(String message, ResultResult result) {
        setHtml(result.getHtml());
        setBarChart(result.getFillerStatistics(), mBarChart);
        setPieChart(result.getSilenceStatistics(), mPieChart);
        hideProgressDialog();
    }

    @Override
    public void getResultFailure(String message) {
        hideProgressDialog();
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }
}