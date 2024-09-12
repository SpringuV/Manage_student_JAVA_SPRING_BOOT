package vutran.my_first_project_spring_boot.management_student.Util;

import java.util.Base64;

public class EncodeUtils {
    public static String encodeId(int id){
        return Base64.getEncoder().encodeToString(String.valueOf(id).getBytes());
    }
    public static int decodeId(String encodeId){
        return Integer.parseInt(new String(Base64.getDecoder().decode(encodeId)));
    }

    public static String encodeString(String stringImportant){
        return Base64.getEncoder().encodeToString(stringImportant.getBytes());
    }

    public static String decodeString(String stringImportant){
        return new String(Base64.getDecoder().decode(stringImportant));
    }
}
