package com.cookandroid.flowerdesign.flower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cookandroid.flowerdesign.R;
import com.cookandroid.flowerdesign.model.TodaysItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodaysActivity extends AppCompatActivity {
    private static final String TAG = "Tag";
    private String key="fb5XH94gYvD4%2Fc9fkCuOYkvJftb%2Boga6stsc%2BdEXU%2BGxdcFY%2BpHbsI3QReVR4WTpf%2FO8QUOnTRDfTNnuvrdBwA%3D%3D";
    String getMonth;
    String getDay;
    ImageView img1, img2, img3;
    TextView fname, fsname, fename,flang,fcon,fuse,fgrow,ftype;
    TodaysItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);
        img3=(ImageView)findViewById(R.id.img3);
        fname=(TextView)findViewById(R.id.fname);
        fsname=(TextView)findViewById(R.id.fsname);
        fename=(TextView)findViewById(R.id.fename);
        flang=(TextView)findViewById(R.id.flang);
        fcon=(TextView)findViewById(R.id.fcon);
        fuse=(TextView)findViewById(R.id.fuse);
        fgrow=(TextView)findViewById(R.id.fgrow);
        ftype=(TextView)findViewById(R.id.ftype);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat month = new SimpleDateFormat("M");
        SimpleDateFormat day = new SimpleDateFormat("d");
        getMonth = month.format(date);
        getDay = day.format(date);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(item.getImgUrl1() != "" && item.getImgUrl1() !=null){
                            Glide.with(TodaysActivity.this).load(item.getImgUrl1()).into(img1);
                        }
                        if(item.getImgUrl1() != "" && item.getImgUrl1() !=null){
                            Glide.with(TodaysActivity.this).load(item.getImgUrl2()).into(img2);
                        }
                        if(item.getImgUrl1() != "" && item.getImgUrl1() !=null){
                            Glide.with(TodaysActivity.this).load(item.getImgUrl3()).into(img3);
                        }
                        if(item.getFlowNm() != "" && item.getFlowNm() !=null){
                            fname.setText(item.getFlowNm());
                        }
                        if(item.getfSctNm() != "" && item.getfSctNm() !=null){
                            fsname.setText(item.getfSctNm());
                        }
                        if(item.getfEngNm() != "" && item.getfEngNm() !=null){
                            fename.setText(item.getfEngNm());
                        }
                        if(item.getFlowLang() != "" && item.getFlowLang() !=null){
                            flang.setText(item.getFlowLang());
                        }
                        if(item.getfContent() != "" && item.getfContent() !=null){
                            fcon.setText(item.getfContent());
                        }
                        if(item.getfUse() != "" && item.getImgUrl1() !=null){
                            fuse.setText(item.getfUse());
                        }
                        if(item.getfGrow() != "" && item.getfGrow() !=null){
                            fgrow.setText(item.getfGrow());
                        }
                        if(item.getfType() != "" && item.getfType() !=null){
                            ftype.setText(item.getfType());
                        }
                    }
                });
            }
        }).start();
    }

    private void getData() {
        StringBuffer buffer = new StringBuffer();
        String queryUrl = "http://apis.data.go.kr/1390804/NihhsTodayFlowerInfo01/selectTodayFlower01?serviceKey="
                +key+"&fMonth="+getMonth+"&fDay="+getDay;
        try {
            URL url = new URL(queryUrl); //문자열로 된 요청 url을 URL객체로 생성
            InputStream is = url.openStream(); //url위치로 입력스트림 연결
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream으로부터 xml입력받기
            item=new TodaysItem();
            String tag;
            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기

                        if (tag.equals("result")) {

                        }else if (tag.equals("flowNm")) {
                            xpp.next();
                            buffer.append(xpp.getText()); //flowNm요소의 text 읽어와서 문자열 버퍼에 추가
                            item.setFlowNm("오늘의 꽃: "+xpp.getText());
                        }else if (tag.equals("fSctNm")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            item.setfSctNm("[학문명]  "+xpp.getText());
                        }else if (tag.equals("fEngNm")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            item.setfEngNm("[영문명]  "+xpp.getText());
                        }else if (tag.equals("flowLang")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            item.setFlowLang("[꽃말]  "+xpp.getText());
                        }else if (tag.equals("fContent")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            item.setfContent(xpp.getText());
                        }else if (tag.equals("fUse")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            item.setfUse(xpp.getText());
                        }else if (tag.equals("fGrow")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            item.setfGrow(xpp.getText());
                        }else if (tag.equals("fType")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            item.setfType(xpp.getText());
                        }else if (tag.equals("imgUrl1")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            item.setImgUrl1(xpp.getText());
                        }else if (tag.equals("imgUrl2") && tag != null) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            item.setImgUrl2(xpp.getText());
                        }else if (tag.equals("imgUrl3") && tag != null) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            item.setImgUrl3(xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();
                        if (tag.equals("result"))
                            break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}