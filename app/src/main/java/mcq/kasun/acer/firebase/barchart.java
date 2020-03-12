package mcq.kasun.acer.firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mcq.kasun.acer.firebase.common.common;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class barchart extends AppCompatActivity {

    BarChart barChart;
    FirebaseDatabase database;
    DatabaseReference qscore;



    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mcq.kasun.acer.firebase.R.layout.barchrt);

        barChart = (BarChart) findViewById(mcq.kasun.acer.firebase.R.id.bargraph);
        database = FirebaseDatabase.getInstance();
        qscore = database.getReference("Question_Score");




      //  createRandomBarGraph("2016/05/05", "2016/06/01");

        BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();

        AddValuesToBARENTRY();

        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "Projects");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        barChart.setData(BARDATA);

        barChart.animateY(3000);

        barChart.setVisibleXRangeMaximum(5);


    }

    public void AddValuesToBARENTRY(){


       for(int i = 0; i< common.ques_score.size(); i++){

           BARENTRY.add(new BarEntry(common.ques_score.get(i), i));
    }

       /* BARENTRY.add(new BarEntry(2, 0));
        BARENTRY.add(new BarEntry(4, 1));
        BARENTRY.add(new BarEntry(6, 2));
        BARENTRY.add(new BarEntry(8, 3));
        BARENTRY.add(new BarEntry(7, 4));
        BARENTRY.add(new BarEntry(3, 5));
        BARENTRY.add(new BarEntry(3, 6));
        BARENTRY.add(new BarEntry(7, 7));  */


    }
    public void AddValuesToBarEntryLabels(){

       for(int i=0;i< common.ques_score.size();i++){
           BarEntryLabels.add(common.papername.get(i));
        }

    }




   /* public void createRandomBarGraph(String Date1, String Date2){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        try {
            Date date1 = simpleDateFormat.parse(Date1);
            Date date2 = simpleDateFormat.parse(Date2);

            Calendar mDate1 = Calendar.getInstance();
            Calendar mDate2 = Calendar.getInstance();
            mDate1.clear();
            mDate2.clear();

            mDate1.setTime(date1);
            mDate2.setTime(date2);

            dates = new ArrayList<>();
            dates = getList(mDate1,mDate2);

            barEntries = new ArrayList<>();
            float max = 0f;


            float value = 0f;


            random = new Random();
            for(int j = 0; j< dates.size();j++){
                for (int i =0;i<dateyt.size();i++){

                    barEntries.add(new BarEntry(dateyt.get(i),j));
                    break;
                }

                max = 100f;
                value = random.nextFloat()*max;


               // barEntries.add(new BarEntry(value,j));
            }

        }catch(ParseException e){
            e.printStackTrace();
        }

        BarDataSet barDataSet = new BarDataSet(barEntries,"Dates");
        BarData barData = new BarData(dates,barDataSet);
        barChart.setData(barData);
        barChart.setDescription("My First Bar Graph!");

    }  */

  /*  public ArrayList<String> getList(Calendar startDate, Calendar endDate){
        ArrayList<String> list = new ArrayList<String>();
        while(startDate.compareTo(endDate)<=0){
            list.add(getDate(startDate));
            startDate.add(Calendar.DAY_OF_MONTH,1);
        }
        return list;
    } */

   /* public String getDate(Calendar cld){
        String curDate = cld.get(Calendar.YEAR) + "/" + (cld.get(Calendar.MONTH) + 1) + "/"
                +cld.get(Calendar.DAY_OF_MONTH);
        try{
            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(curDate);
            curDate =  new SimpleDateFormat("yyy/MM/dd").format(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
        return curDate;
    } */

}
