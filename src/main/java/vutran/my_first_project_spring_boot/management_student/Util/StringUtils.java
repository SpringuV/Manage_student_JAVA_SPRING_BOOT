package vutran.my_first_project_spring_boot.management_student.Util;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class StringUtils {

    // Format string
    public static String formatString(String pattern){
        ArrayList<String> arr = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(pattern);
        while (stringTokenizer.hasMoreTokens()){
            arr.add(stringTokenizer.nextToken());
        }
        String res = "";
        for (String s : arr){
            res += s.toUpperCase().charAt(0);
            for (int i = 1; i < s.length(); i++){
                res += s.toLowerCase().charAt(i);
            }
            res += " ";
        }
        return res.trim();
    }



//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        String test = sc.nextLine();
//        System.out.println(formatString(test));
//    }
}
