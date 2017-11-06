package project.agile.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Oneplus on 2017/4/21.
 */

public class WriteToSD {
    static String filePath = android.os.Environment.getExternalStorageDirectory()+"/project.agile.nbaapp";

    public static void writeToSD(Context context, String fileName){
        if(!isExist(fileName)){
            write(context, fileName);
        }
    }

    private static void write(Context context, String fileName){
        InputStream inputStream;
        try {
            inputStream = context.getResources().getAssets().open(fileName);
            File file = new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(filePath + "/" + fileName);
            byte[] buffer = new byte[1024];
            int count = 0;
            while((count = inputStream.read(buffer)) > 0){
                fileOutputStream.write(buffer, 0 ,count);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
            System.out.println("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static boolean isExist(String fileName){
        File file = new File(filePath + "/" + fileName);
        if(file.exists()){
            return true;
        }else{
            return false;
        }
    }
}
