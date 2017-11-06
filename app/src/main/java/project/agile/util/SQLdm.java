package project.agile.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Oneplus on 2017/4/21.
 */

public class SQLdm {
    public static final String TAG = "SQLdm";

    //数据库存储路径
    String filePath = "data/data/project.agile.nbaapp/nba_data.db";
    //数据库存放的文件夹
    String pathStr = "data/data/project.agile.nbaapp";

    SQLiteDatabase database;
    // 存在则获取数据库，不存在则创建数据库
    public  SQLiteDatabase openDatabase(Context context){
        System.out.println("filePath:"+filePath);
        File jhPath=new File(filePath);
        //查看数据库文件是否存在
        if(jhPath.exists()){
            Log.i(TAG, "存在数据库");
            //存在则直接返回打开的数据库
            return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
        }else{
            //不存在先创建文件夹
            File path=new File(pathStr);
            Log.i(TAG, "pathStr="+path);
            if (path.mkdir()){
                Log.i(TAG, "创建成功");
            }else{
                Log.i(TAG, "创建失败");
            }
            try {
                //得到资源
                AssetManager am= context.getAssets();
                //得到数据库的输入流
                InputStream is=am.open("nba_data.db");
                Log.i(TAG, is+"");
                //用输出流写到SDcard上面
                FileOutputStream fos=new FileOutputStream(jhPath);
                Log.i(TAG, "fos="+fos);
                Log.i(TAG, "jhPath="+jhPath);
                byte[] buffer=new byte[1024];
                int count = 0;
                while((count = is.read(buffer))>0){
                    Log.i(TAG, "得到");
                    fos.write(buffer,0,count);
                }
                //关闭
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            return openDatabase(context);
        }
    }

}
