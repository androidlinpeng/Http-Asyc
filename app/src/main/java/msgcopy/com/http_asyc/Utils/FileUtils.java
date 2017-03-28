package msgcopy.com.http_asyc.Utils;

import android.os.Environment;

import java.io.File;

import msgcopy.com.http_asyc.R;
import msgcopy.com.http_asyc.asynchttp.MsgApplication;

/**
 * Created by liang on 2017/3/2.
 */
public class FileUtils {

    public static String getTempPath() {
        return Environment.getExternalStorageDirectory() +
                File.separator + "magcopy" +
                File.separator + MsgApplication.getInstance().getPackageName() +
                File.separator + "tmp" + File.separator;
    }

    // 根据filename创建一个处于/sdcard/msgcopy/包名/tmp文件夹下的临时文件
    public static File createTmpFile(String fileName) {
        if (!CommonUtil.isBlank(fileName)) {
            try{
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    File file = new File(getTempPath() + fileName);
                    file.getParentFile().mkdirs();
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                    return file;
                }else{
                    ToastUtils.showShort(MsgApplication.getInstance(), R.string.str_sdcard_not_available);
                    return null;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }
}
















