package mcq.kasun.acer.firebase.common;

import mcq.kasun.acer.firebase.model.questions;
import mcq.kasun.acer.firebase.model.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 4/11/2018.
 */

public class common {

    public static  String papperno;
    public static String catogeryId,catogery2Id,categoryname,pastpapername;
    public static int PPno;
    public static int attepmt;
    public static String rankuser;
    public static user currrenttUser;



    public static int intArrayy[] = new int[70];

    public  static  int arr[];
    public static List<questions>questionlist =new ArrayList<>();
    public static List<Integer> wrong_answers = new ArrayList<Integer>();
    public static List<Integer> ques_score = new ArrayList<Integer>();
    public static List<String> papername = new ArrayList<String>();
    public static List<Integer> Right = new ArrayList<Integer>();

}
