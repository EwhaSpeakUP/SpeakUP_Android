package com.jionchu.speakup.src.result;

import android.os.Bundle;
import android.webkit.WebView;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.ApplicationClass;
import com.jionchu.speakup.src.BaseActivity;

public class ResultActivity extends BaseActivity {

    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mWebView = findViewById(R.id.result_webview);

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
}