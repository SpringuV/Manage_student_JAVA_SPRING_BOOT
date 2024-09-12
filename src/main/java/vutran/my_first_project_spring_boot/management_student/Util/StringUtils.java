package vutran.my_first_project_spring_boot.management_student.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

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

    public static String shortenSignature(String fullMethodSignature){
        // detach the last nameClass and method
        // Example: "Page vutran.my_first_project_spring_boot.management_student.Service.SubjectServiceImple.getListSubjectDTOByClassGrade"
        // Get: v.m.m.Service.SubjectServiceImple.getListSubjectDTOByClassGrade
        System.out.println(fullMethodSignature);
        String[] detach = fullMethodSignature.split("\\.|\\s+"); // take "." or " "
        String res = "";
        res += detach[0]+" "; // add 'Page' into res
        for(int i = 1; i< detach.length; i++){ // ignore 'Page': type-pattern
            // substring
            if(i < 4){
                res += detach[i].trim().charAt(0);
            } else {
                res += detach[i].trim();
            }
            if(i != detach.length - 1){ // không thêm dấu chấm vào cuối
                res +=".";
            }
        }
        return res.trim();
    }

//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
////        String test = sc.nextLine();
////        System.out.println(formatString(test));
//        System.out.println(shortenSignature("vutran.my_first_project_spring_boot.management_student.Service.SubjectServiceImple.getListSubjectDTOByClassGrade"));
//    }
}
