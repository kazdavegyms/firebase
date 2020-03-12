package mcq.kasun.acer.firebase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import mcq.kasun.acer.firebase.common.common;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class playing extends AppCompatActivity implements OnClickListener {

    final static long INTERVEL = 1000;
    final static long TIMEOUT = 30000;
    int ProgressValue = 0;
    ImageView markkk;

    public String answerA;
    AlertDialog mydialoge,myskipdialog;

    String kk = answerA;

    ListView listView = null;
    ListView listskip = null;


    //private FloatingActionButton fab_widget,fab1_sad;
    private PopupWindow window;
    String TAG = "MainActivity";

    int k = 70;
    int intArray[] = new int[k];
    boolean corr;


    CountDownTimer mCountDown,clockz;
  public static int index;
           int score = 0, thisQuestion = 0, totalQustion, correctAnswer, i = 0;

    //firebase
    FirebaseDatabase database;
    DatabaseReference questions;

    ProgressBar pbar;
    PhotoView imgv;
    Button ba, bb, bc, bd, be, bk, skip, enouh, back, mark, mlist,slist;
    TextView tscore, tqno, tqes;
    List<Integer> skip_ayyay_list = new ArrayList<Integer>();
    List<Integer> mark_list = new ArrayList<Integer>();
    List<Integer> questionanswerd = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(mcq.kasun.acer.firebase.R.layout.activity_playing);
        //firebase
        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");
        imgv = (PhotoView) findViewById(mcq.kasun.acer.firebase.R.id.photo_view);
        markkk = findViewById(mcq.kasun.acer.firebase.R.id.imjmark);
        markkk.setVisibility(View.INVISIBLE);


        tscore = (TextView) findViewById(mcq.kasun.acer.firebase.R.id.tscore);
        tqno = (TextView) findViewById(mcq.kasun.acer.firebase.R.id.numqest);
        tqes = (TextView) findViewById(mcq.kasun.acer.firebase.R.id.questions);

        pbar = (ProgressBar) findViewById(mcq.kasun.acer.firebase.R.id.prog);

        mlist = (Button) findViewById(mcq.kasun.acer.firebase.R.id.marklist);
        slist = (Button) findViewById(mcq.kasun.acer.firebase.R.id.skiplist);
        skip = (Button) findViewById(mcq.kasun.acer.firebase.R.id.skip);
        enouh = (Button) findViewById(mcq.kasun.acer.firebase.R.id.Enough);
        mark = (Button) findViewById(mcq.kasun.acer.firebase.R.id.mark);
        back = (Button) findViewById(mcq.kasun.acer.firebase.R.id.backbtn);
        ba = (Button) findViewById(mcq.kasun.acer.firebase.R.id.buttonAA);
        bb = (Button) findViewById(mcq.kasun.acer.firebase.R.id.buttonB);
        bc = (Button) findViewById(mcq.kasun.acer.firebase.R.id.buttonC);
        bd = (Button) findViewById(mcq.kasun.acer.firebase.R.id.buttonD);
        be = (Button) findViewById(mcq.kasun.acer.firebase.R.id.buttonF);

        ba.setOnClickListener(this);
        bb.setOnClickListener(this);
        bc.setOnClickListener(this);
        bd.setOnClickListener(this);
        be.setOnClickListener(this);

        // Create an instance of a ListView

        listView = new ListView(this);
        listskip = new ListView(this);

        markkk.setImageDrawable(getResources().getDrawable(mcq.kasun.acer.firebase.R.drawable.ic_bookmark_152545));
        markkk.setVisibility(View.INVISIBLE);

        //time counter


       int duration=3600000; //6 hours
       clockz = new CountDownTimer(duration, 1000) {

            public void onTick(long millisUntilFinished) {
                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;

                long elapsedHours = millisUntilFinished / hoursInMilli;
                millisUntilFinished = millisUntilFinished % hoursInMilli;

                long elapsedMinutes = millisUntilFinished / minutesInMilli;
                millisUntilFinished = millisUntilFinished % minutesInMilli;

                long elapsedSeconds = millisUntilFinished / secondsInMilli;

                String yy = String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes,elapsedSeconds);
                tscore.setText(yy);
            }

            public void onFinish() {

                tscore.setText("00:00:00");
                Intent intent = new Intent(playing.this, done.class);
                Bundle dataSend = new Bundle();

                int sscore = common.wrong_answers.size();
                dataSend.putInt("SCORE", sscore);
                dataSend.putInt("TOTAL", totalQustion);
                dataSend.putInt("COREECT", correctAnswer);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();

            }
        }.start();

        /*
        clockz =  new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {


                long millis = millisUntilFinished;

               // String ms = String.format("%02d:%02d:%02d", millis / 3600,
                  //      (millis % 3600) / 60, (millis % 60));

                tscore.setText("seconds remaining: " + formatMilliSecondsToTime(300000));
            }

            public void onFinish() {

                Intent intent = new Intent(playing.this, done.class);
                Bundle dataSend = new Bundle();

                int sscore = common.wrong_answers.size();
                dataSend.putInt("SCORE", sscore);
                dataSend.putInt("TOTAL", totalQustion);
                dataSend.putInt("COREECT", correctAnswer);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
               // tscore.setText("done!");

            }

        }.start();

        */

        // Add data to the ListView



        

        common.Right.indexOf(1);

        final ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                mcq.kasun.acer.firebase.R.layout.activity_listview, mcq.kasun.acer.firebase.R.id.txtitem, mark_list);

        final ArrayAdapter<Integer> adapterskip = new ArrayAdapter<Integer>(this,
                mcq.kasun.acer.firebase.R.layout.activity_listview, mcq.kasun.acer.firebase.R.id.txtitem, skip_ayyay_list);


        listView.setAdapter(adapter);
        listskip.setAdapter(adapterskip);


        // Perform action when an item is clicked

        listView.setOnItemClickListener(new
                                                AdapterView.OnItemClickListener() {

                                                    @Override

                                                    public void onItemClick(AdapterView<?> parent, View viewb, int
                                                            position, long id) {

                                                        ViewGroup vg = (ViewGroup) viewb;

                                                        TextView txt = (TextView) vg.findViewById(mcq.kasun.acer.firebase.R.id.txtitem);

                                                        Toast.makeText(playing.this, txt.getText().toString(), Toast.LENGTH_LONG).show();
                                                        String number = txt.getText().toString();
                                                        int result = Integer.parseInt(number);


                                                       ShowQuestlist(result);
                                                       // ShowQuest(result);



                                                        int item = mark_list.get(position);


                                                    }

                                                });

        listskip.setOnItemClickListener(new
                                                AdapterView.OnItemClickListener() {

                                                    @Override

                                                    public void onItemClick(AdapterView<?> parent, View view, int
                                                            position, long id) {

                                                        ViewGroup vgg = (ViewGroup) view;

                                                        TextView txt = (TextView) vgg.findViewById(mcq.kasun.acer.firebase.R.id.txtitem);

                                                        Toast.makeText(playing.this, txt.getText().toString(), Toast.LENGTH_LONG).show();
                                                        String number = txt.getText().toString();


                                                        int result = Integer.parseInt(number);
                                                        //ShowQuest(++index);
                                                        ShowQuestlist(result);

                                                        /*

                                                        int item = common.wrong_answers.get(position);
                                                        common.wrong_answers.remove(position);
                                                        adapter.notifyDataSetChanged();   */

                                                    }

                                                });


        //fab_widget = (FloatingActionButton)findViewById(R.id.fab);
        /*/
        fab_widget.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, " open popup window touch");
               // ShowPopupWindoww();
                showDialogListView();


            }

        });*/
        slist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Collections.sort(skip_ayyay_list);
               showDialogListView();


            }
        });

        mlist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                marklistshow();



            }
        });

        mark.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                markkk.setVisibility(View.VISIBLE);


                int position = -1;
                position = mark_list.indexOf(index);
                if (position == -1) {
                    Log.e(TAG, "Object not found in List");
                    mark_list.add(index);
                    Toast.makeText(playing.this, "7Object not found in List", Toast.LENGTH_SHORT).show();

                } else {
                    Log.i(TAG, "" + position);
                    mark_list.remove(new Integer(index));
                    markkk.setVisibility(View.INVISIBLE);


                }

            }
        });

        skip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                int positionnn = -1;
                positionnn = questionanswerd.indexOf(index);
                if (positionnn == -1) {

                    Log.e(TAG, "Object not found in List");


                    int position = -1;
                    position = skip_ayyay_list.indexOf(index);
                    if (position == -1) {

                        skip_ayyay_list.add(index);

                    } else {


                    }
                    //Toast.makeText(playing.this, "kasun you great", Toast.LENGTH_SHORT).show();

                } else {
                    Log.i(TAG, "" + positionnn);
                   // common.wrong_answers.remove(new Integer(index));

                    //score += 1;
                    //Toast.makeText(playing.this, "found it .. remove it", Toast.LENGTH_SHORT).show();

                }

/*
                int position = -1;
                position = skip_ayyay_list.indexOf(index);
                if (position == -1) {
                    Log.e(TAG, "Object not found in List");
                    Toast.makeText(playing.this, "7Object not found in List", Toast.LENGTH_SHORT).show();

                } else {
                    Log.i(TAG, "" + position);
                    corr = true;
                    Toast.makeText(playing.this, "you have to answer this question again", Toast.LENGTH_SHORT).show();


                }
                */
                ShowQuestskip(++index);

                int positionn = -1;
                positionn = mark_list.indexOf(index);
                if (positionn == -1) {
                    Log.e(TAG, "Object not found in List");
                    // mark_list.add(index);
                    markkk.setVisibility(View.INVISIBLE);



                } else {
                    Log.i(TAG, "" + positionn);
                    // markkk.setImageResource(R.drawable.ic_check_box_black_24dp);
                    markkk.setVisibility(View.VISIBLE);


                }




            }
        });

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                ShowQuestt(--index);


                int position = -1;
                position = common.Right.indexOf(index);
                if (position == -1) {
                    Log.e(TAG, "Object not found in List");
                    // Toast.makeText(playing.this,"7Object not found in List",Toast.LENGTH_SHORT).show();

                } else {
                    Log.i(TAG, "" + position);
                    corr = true;
                    // Toast.makeText(playing.this,"you have to answer this question again",Toast.LENGTH_SHORT).show();


                }


                int positionn = -1;
                positionn = mark_list.indexOf(index);
                if (positionn == -1) {
                    Log.e(TAG, "Object not found in List");
                    // mark_list.add(index);
                    markkk.setVisibility(View.INVISIBLE);



                } else {
                    Log.i(TAG, "" + positionn);
                    // markkk.setImageResource(R.drawable.ic_check_box_black_24dp);
                    markkk.setVisibility(View.VISIBLE);


                }


            }
        });


        enouh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                clockz.onFinish();
/*
                Intent intent = new Intent(playing.this, done.class);
                //wrong_answers.clear();
                Bundle dataSend = new Bundle();



/*
                dataSend.putInt("SCORE", common.wrong_answers.size());
                dataSend.putInt("TOTAL", totalQustion);
                dataSend.putInt("COREECT", correctAnswer);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
                Toast.makeText(playing.this, "7load", Toast.LENGTH_SHORT).show();

                */


            }
        });




    }


    private String formatMilliSecondsToTime(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : "
                + twoDigitString(seconds);
    }

    private String twoDigitString(long number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }


    public void ShowPopupWindoww() {

        try {


            LayoutInflater inflater = (LayoutInflater) playing.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(mcq.kasun.acer.firebase.R.layout.pppppop, null);
            window = new PopupWindow(layout, 500, 500, true);

            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setOutsideTouchable(true);
            window.showAtLocation(layout, Gravity.CENTER, 40, 60);

           /* String[] mobileArray = {"Java", "C++", "C#", "CSS",
                    "HTML", "XML", ".Net", "VisualBasic", "SQL", "Python", "PHP"};

            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.activity_listview, mobileArray);
                    */

            ImageView imageViewnew = (ImageView) findViewById(mcq.kasun.acer.firebase.R.id.windoimage);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////

            if (common.questionlist.get(index).getIsimageQuestion().equals("true")) {

                //if fs a image
                Picasso.with(getBaseContext())
                        .load(common.questionlist.get(index).getQuestion())
                        .into(imageViewnew);

                imageViewnew.setVisibility(View.VISIBLE);
                tqes.setVisibility(View.INVISIBLE);
                // Toast.makeText(playing.this,"5load",Toast.LENGTH_SHORT).show();

            } else {

                //  tqes.setText(common.questionlist.get(index).getQuestion());

                // imgv.setVisibility(View.INVISIBLE);
                // tqes.setVisibility(View.VISIBLE);

                Picasso.with(getBaseContext())
                        .load(common.questionlist.get(index).getQuestion())
                        .into(imageViewnew);

                imageViewnew.setVisibility(View.INVISIBLE);
                tqes.setVisibility(View.VISIBLE);

                //   Toast.makeText(playing.this,"6load",Toast.LENGTH_SHORT).show();

            }
/////////////////////////////////////////////////////////////////////////////////////
    /*
            ListView listView = (ListView) findViewById(R.id.mobile_list);
            listView.setAdapter(adapter);
*/

            Toast.makeText(playing.this, "done until here", Toast.LENGTH_SHORT).show();


        } catch (Exception e) {


            Toast.makeText(playing.this, "done until here", Toast.LENGTH_SHORT).show();

        }
    }

    public void showDialogListView() {


        if (mydialoge == null) {
            AlertDialog.Builder builder = new
                    AlertDialog.Builder(playing.this);

            builder.setCancelable(true);

            builder.setPositiveButton("OK", null);

            builder.setTitle("skip list");

            builder.setView(listskip);

            mydialoge = builder.create();

            mydialoge.show();
        } else {
            mydialoge.show();
        }


    }

    public void marklistshow() {


        if (myskipdialog == null) {
            AlertDialog.Builder builder = new
                    AlertDialog.Builder(playing.this);

            builder.setCancelable(true);

            builder.setPositiveButton("OK", null);

            builder.setTitle("Marked Questions");

            builder.setView(listView);

            myskipdialog = builder.create();

            myskipdialog.show();
        } else {
            myskipdialog.show();
        }


    }


    @Override
    public void onClick(View v) {



        //on.setText("answerA");
        Log.e(TAG, " button call press ");
        // window.dismiss();

      //  String numberAsString = Integer.toString(index);
       // Toast.makeText(playing.this,"onclick values list "+numberAsString,Toast.LENGTH_SHORT).show();


        int positionnew = -1;
        positionnew = questionanswerd.indexOf(index);
        if (positionnew == -1) {
            Log.e(TAG, "Object not found in List");
            questionanswerd.add(index);


           // int nn = index;
           // int last= nn-1;

            int position = -1;
            position = skip_ayyay_list.indexOf(index);
            if (position == -1) {
                Log.e(TAG, "Object not found in List");
                 Toast.makeText(playing.this,"skip list  not found in List",Toast.LENGTH_SHORT).show();

            } else {


                int pos =skip_ayyay_list.indexOf(index);
                Toast.makeText(playing.this,"positions values  "+pos,Toast.LENGTH_SHORT).show();

                skip_ayyay_list.remove(new Integer(index));
                Toast.makeText(playing.this,"skip removed",Toast.LENGTH_SHORT).show();



            }

            // Toast.makeText(playing.this,"7Object not found in List",Toast.LENGTH_SHORT).show();

        } else {
            Log.i(TAG, "" + positionnew);


            // Toast.makeText(playing.this,"you have to answer this question again",Toast.LENGTH_SHORT).show();


        }


        mCountDown.cancel();
        if (index < totalQustion) {

            Button clickedButton = (Button) v;
            // Toast.makeText(playing.this,"1load",Toast.LENGTH_SHORT).show();
            int positionn = -1;
            positionn = common.wrong_answers.indexOf(index);
            if (positionn == -1) {
                Log.e(TAG, "Object not found in List");
               // Toast.makeText(playing.this, "kasun you great", Toast.LENGTH_SHORT).show();

            } else {
                Log.i(TAG, "" + positionn);
                common.wrong_answers.remove(new Integer(index));
                //score += 1;
               // Toast.makeText(playing.this, "found it .. remove it", Toast.LENGTH_SHORT).show();

            }


            if (clickedButton.getText().equals(common.questionlist.get(index).getCorrectAnswer())) {


                if (corr == true) {

                  //  Toast.makeText(playing.this, "already given the correct answer", Toast.LENGTH_SHORT).show();
                    common.Right.remove(new Integer(index));


                } else {

                    common.Right.add(index);


                    score += 1;
                    correctAnswer++;
                   // Toast.makeText(playing.this, "right again", Toast.LENGTH_SHORT).show();
                    i++;
                }

                ShowQuest(++index);


            } else {


                common.wrong_answers.add(index);

                if (corr == true) {


                    common.Right.remove(new Integer(index));
                    common.intArrayy[i] = index;
                    intArray[i] = index;
                    ShowQuest(++index);
                    i++;
                    score--;
                   // Toast.makeText(playing.this, "remove the right answer you gave before", Toast.LENGTH_SHORT).show();


                } else {


                    common.intArrayy[i] = index;
                    intArray[i] = index;
                    ShowQuest(++index);
                    i++;
                }


                //Toast.makeText(playing.this, "wrong answer loade", Toast.LENGTH_SHORT).show();
            }



            int position = -1;
            position = mark_list.indexOf(index);
            if (position == -1) {
                Log.e(TAG, "Object not found in List");
                // mark_list.add(index);
                markkk.setVisibility(View.INVISIBLE);



            } else {
                Log.i(TAG, "" + position);
                // markkk.setImageResource(R.drawable.ic_check_box_black_24dp);
                markkk.setVisibility(View.VISIBLE);


            }

        }
    }

    private void ShowQuestlist(int num){

        thisQuestion = num;
        tqno.setText(String.format("%d/%s", thisQuestion , totalQustion - 1));
        pbar.setProgress(0);
        ProgressValue = 0;
       // index++;

        index = num;
        String numberAsString = Integer.toString(index);
        Toast.makeText(playing.this,"this is the qest value for index "+ numberAsString,Toast.LENGTH_SHORT).show();



        if (common.questionlist.get(num).getIsimageQuestion().equals("true")) {

            //if fs a image
            Picasso.with(getBaseContext())
                    .load(common.questionlist.get(num).getQuestion())
                    .into(imgv);

            imgv.setVisibility(View.VISIBLE);
            tqes.setVisibility(View.INVISIBLE);
            // Toast.makeText(playing.this,"5load",Toast.LENGTH_SHORT).show();

        } else {

            //  tqes.setText(common.questionlist.get(index).getQuestion());

            // imgv.setVisibility(View.INVISIBLE);
            // tqes.setVisibility(View.VISIBLE);

            Picasso.with(getBaseContext())
                    .load(common.questionlist.get(num).getQuestion())
                    .into(imgv);

            imgv.setVisibility(View.INVISIBLE);
            tqes.setVisibility(View.VISIBLE);

            //   Toast.makeText(playing.this,"6load",Toast.LENGTH_SHORT).show();

        }

        ba.setText(common.questionlist.get(num).getAnswerA());
        bb.setText(common.questionlist.get(num).getAnswerB());
        bc.setText(common.questionlist.get(num).getAnswerC());
        bd.setText(common.questionlist.get(num).getAnswerD());
        be.setText(common.questionlist.get(num).getAnswerE());
        // on.setText(common.questionlist.get(index).getAnswerE());
        mCountDown.start(); //sta



    }

    private void ShowQuest(int indexquest) {


        int position = -1;
        position = common.Right.indexOf(indexquest);
        if (position == -1) {
            Log.e(TAG, "Object not found in List");
            // Toast.makeText(playing.this,"7Object not found in List",Toast.LENGTH_SHORT).show();

        } else {
            Log.i(TAG, "" + position);
            corr = true;
            // Toast.makeText(playing.this,"you have to answer this question again",Toast.LENGTH_SHORT).show();


        }


        if (indexquest < totalQustion) {


            tqno.setText(String.format("%d/%s", thisQuestion + 1, totalQustion - 1));
            thisQuestion++;
            pbar.setProgress(0);
            ProgressValue = 0;
            // Toast.makeText(playing.this,indexquest,Toast.LENGTH_SHORT).show();
            //  String tt,kk;
            //  char kk = answerA;


            if (common.questionlist.get(indexquest).getIsimageQuestion().equals("true")) {

                //if fs a image
                Picasso.with(getBaseContext())
                        .load(common.questionlist.get(indexquest).getQuestion())
                        .into(imgv);

                imgv.setVisibility(View.VISIBLE);
                tqes.setVisibility(View.INVISIBLE);
                // Toast.makeText(playing.this,"5load",Toast.LENGTH_SHORT).show();

            } else {

                //  tqes.setText(common.questionlist.get(indexquest).getQuestion());

                // imgv.setVisibility(View.INVISIBLE);
                // tqes.setVisibility(View.VISIBLE);

                Picasso.with(getBaseContext())
                        .load(common.questionlist.get(indexquest).getQuestion())
                        .into(imgv);

                imgv.setVisibility(View.INVISIBLE);
                tqes.setVisibility(View.VISIBLE);

                //   Toast.makeText(playing.this,"6load",Toast.LENGTH_SHORT).show();

            }

            ba.setText(common.questionlist.get(indexquest).getAnswerA());
            bb.setText(common.questionlist.get(indexquest).getAnswerB());
            bc.setText(common.questionlist.get(indexquest).getAnswerC());
            bd.setText(common.questionlist.get(indexquest).getAnswerD());
            be.setText(common.questionlist.get(indexquest).getAnswerE());
            // on.setText(common.questionlist.get(indexquest).getAnswerE());
          //  mCountDown.start(); //sta



        } else {

            //if it is the final question
            Intent intent = new Intent(this, done.class);
            Bundle dataSend = new Bundle();

            int sscore = common.wrong_answers.size();
            dataSend.putInt("SCORE", sscore);
            dataSend.putInt("TOTAL", totalQustion);
            dataSend.putInt("COREECT", correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();


        }


    }

    private void ShowQuestt(int indexx) {


        if (thisQuestion == 1) {

            index++;


            Toast.makeText(playing.this, "cant go back from here", Toast.LENGTH_SHORT).show();


        } else {


            if (indexx < totalQustion) {

                thisQuestion--;
                tqno.setText(String.format("%d/%s", thisQuestion, totalQustion - 1));

                pbar.setProgress(0);
                ProgressValue = 0;
                // Toast.makeText(playing.this,indexx,Toast.LENGTH_SHORT).show();
                //  String tt,kk;
                //  char kk = answerA;


                if (common.questionlist.get(indexx).getIsimageQuestion().equals("true")) {

                    //if fs a image
                    Picasso.with(getBaseContext())
                            .load(common.questionlist.get(indexx).getQuestion())
                            .into(imgv);

                    imgv.setVisibility(View.VISIBLE);
                    tqes.setVisibility(View.INVISIBLE);
                    // Toast.makeText(playing.this,"5load",Toast.LENGTH_SHORT).show();

                } else {

                    //  tqes.setText(common.questionlist.get(indexx).getQuestion());

                    // imgv.setVisibility(View.INVISIBLE);
                    // tqes.setVisibility(View.VISIBLE);

                    Picasso.with(getBaseContext())
                            .load(common.questionlist.get(indexx).getQuestion())
                            .into(imgv);

                    imgv.setVisibility(View.INVISIBLE);
                    tqes.setVisibility(View.VISIBLE);

                    //   Toast.makeText(playing.this,"6load",Toast.LENGTH_SHORT).show();

                }

                ba.setText(common.questionlist.get(indexx).getAnswerA());
                bb.setText(common.questionlist.get(indexx).getAnswerB());
                bc.setText(common.questionlist.get(indexx).getAnswerC());
                bd.setText(common.questionlist.get(indexx).getAnswerD());
                be.setText(common.questionlist.get(indexx).getAnswerE());
                // on.setText(common.questionlist.get(indexx).getAnswerE());

                mCountDown.start(); //start timer


            } else {


                //if it is the final question
                Intent intent = new Intent(this, done.class);
                Bundle dataSend = new Bundle();
                dataSend.putInt("SCORE", score);
                dataSend.putInt("TOTAL", totalQustion);
                dataSend.putInt("COREECT", correctAnswer);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
                Toast.makeText(playing.this, "7load", Toast.LENGTH_SHORT).show();

            }


        }

    }

    private void ShowQuestskip(int index) {

        if (totalQustion - 1 == thisQuestion) {


        } else {


        }

        if (index < totalQustion) {


            tqno.setText(String.format("%d/%s", thisQuestion + 1, totalQustion - 1));
            thisQuestion++;
            pbar.setProgress(0);
            ProgressValue = 0;
            // Toast.makeText(playing.this,index,Toast.LENGTH_SHORT).show();
            //  String tt,kk;
            //  char kk = answerA;


            if (common.questionlist.get(index).getIsimageQuestion().equals("true")) {

                //if fs a image
                Picasso.with(getBaseContext())
                        .load(common.questionlist.get(index).getQuestion())
                        .into(imgv);

                imgv.setVisibility(View.VISIBLE);
                tqes.setVisibility(View.INVISIBLE);
                // Toast.makeText(playing.this,"5load",Toast.LENGTH_SHORT).show();

            } else {

                //  tqes.setText(common.questionlist.get(index).getQuestion());

                // imgv.setVisibility(View.INVISIBLE);
                // tqes.setVisibility(View.VISIBLE);

                Picasso.with(getBaseContext())
                        .load(common.questionlist.get(index).getQuestion())
                        .into(imgv);

                imgv.setVisibility(View.INVISIBLE);
                tqes.setVisibility(View.VISIBLE);

                //   Toast.makeText(playing.this,"6load",Toast.LENGTH_SHORT).show();

            }

            ba.setText(common.questionlist.get(index).getAnswerA());
            bb.setText(common.questionlist.get(index).getAnswerB());
            bc.setText(common.questionlist.get(index).getAnswerC());
            bd.setText(common.questionlist.get(index).getAnswerD());
            be.setText(common.questionlist.get(index).getAnswerE());
            // on.setText(common.questionlist.get(index).getAnswerE());

            mCountDown.start(); //start timer


        } else {

            //if it is the final question
            Intent intent = new Intent(this, done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQustion);
            dataSend.putInt("COREECT", correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
            Toast.makeText(playing.this, "7load", Toast.LENGTH_SHORT).show();

        }


    }


    @Override
    protected void onResume() {
        super.onResume();

        totalQustion = common.questionlist.size();
        mCountDown = new CountDownTimer(TIMEOUT, INTERVEL) {
            @Override
            public void onTick(long millisUntilFinished) {
                pbar.setProgress(ProgressValue);
                ProgressValue++;


            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                //ShowQuest(++index);


            }
        };


        // ihave edit this on 6/10  ++index to index
        ShowQuest(++index);

    }


}

