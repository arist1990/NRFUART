package com.example.Zan.nrfuart;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;

/**
 * Created by nodgd on 2017/09/16.
 */

class MonitorCardData extends BaseCardData {

    private static final String TAG = "MonitorCardData";

    private int channel;
    private List<Integer> message;
    private List<PointValue> valueList;
    private static int ChartLength = 30;


    public MonitorCardData() {
        super();
        message = new ArrayList<>();
    }

    @Override
    public String getTitle() {
        if (title.equals("")) {
            return "No Tag Monitor" ;
        } else {
            return title + " (Monitor)";
        }
    }

    //选择的通道
    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getChannel() {
        return channel;
    }

    //显示出来的数据数组
    public void setMessage(int[] message) {
        this.message = new ArrayList<>();
        for (int i = 0; i < message.length; i++) {
            this.message.add(message[i]);
        }
    }

    public void setMessage(List<Integer> message) {
        this.message = message == null ? new ArrayList<Integer>() : message;
    }

    public void addMessage(int moreMessage) {
        message.add(moreMessage);


        //if (message.size()>=30) {message.remove(0);}
        //valueList.add(new PointValue(valueList.size(),moreMessage));

    }

    public void addMessage(int[] moreMessage) {
        for (int moreMessageItem : moreMessage) {
            message.add(moreMessageItem);
            //valueList.add(new PointValue(valueList.size(),moreMessageItem));

        }
    }

    public void addMessage(List<Integer> moreMessage) {
        for (Integer moreMessageItem : moreMessage) {
            message.add(moreMessageItem);
            //valueList.add(new PointValue(valueList.size(),moreMessageItem));
        }
    }

    public void clearMessage() {
        message.clear();
        valueList.clear();
    }

    public List<Integer> getMessage() {
        return message;
    }

    //把数据数组变成LineChartData类型
    public LineChartData getLineChartData() {

        //申明有效点集和透明点集
        valueList = new ArrayList<>();
        List<PointValue> voidValueList = new ArrayList<>();

        //流数据变成点序列

        if (message != null) {
            for (int i = (message.size()<=ChartLength)? 0: (message.size()-1-ChartLength); i < message.size(); i++) {
                valueList.add(new PointValue(i, message.get(i)));
                Log.d("MonitorCardData", "getLineChartData()  "+i+"  "+message.get(i));
            }
        }

        //点序列变成线
        Line line = new Line(valueList);
        line.setColor(R.color.ZanDark);
        line.setCubic(false);
        line.setFilled(false);
        line.setHasPoints(false);
        line.setStrokeWidth(1);

        //创建一个ChartLength长度的透明线
        for (int i = (message == null||message.size()<=ChartLength)? 0: (message.size()-1-ChartLength); i < ((message == null||message.size()<=ChartLength)? ChartLength: message.size()); i++)
        {
            PointValue pv = new PointValue(i, 0);
            voidValueList.add(pv);
        }
        Line voidLine = new Line(voidValueList);
        voidLine.setColor(0x00000000);


        //线变成线序列
        List<Line> lineList = new ArrayList<>();
        lineList.add(voidLine);
        lineList.add(line);


        //x轴
        Axis axisX = new Axis();
        axisX.setTextColor(R.color.ZanAxis);
        axisX.setTextSize(10);
        axisX.setAutoGenerated(true);

        //y轴
        Axis axisY = new Axis();
        axisY.setTextColor(R.color.ZanAxis);
        axisY.setTextSize(10);
        axisY.setAutoGenerated(true);

        //新建一个可以传入的数据，填入数据
        LineChartData lineChartData = new LineChartData();
        lineChartData.setLines(lineList);
        lineChartData.setAxisXBottom(axisX);
        lineChartData.setAxisYLeft(axisY);
        return lineChartData;
    }
}
