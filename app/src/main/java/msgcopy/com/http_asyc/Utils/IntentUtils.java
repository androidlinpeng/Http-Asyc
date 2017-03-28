package msgcopy.com.http_asyc.Utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by daniel on 15-7-8.
 */
public class IntentUtils {

    public static void startIntentToPickPhoto(Activity act, int requestCode){
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        act.startActivityForResult(intent, requestCode);
    }

    public static void startIntentToTakePhoto(Activity act, int requestCode, File imgFile){
        if (null != imgFile && imgFile.exists()){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(imgFile));
            act.startActivityForResult(intent, requestCode);
        }
    }

    public static void startIntentToTakeVideo(Activity act, int requestCode, File videoFile){
        if (null != videoFile && videoFile.exists()){
            Uri uri = Uri.fromFile(videoFile);
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.8);//画质
//                                    http://stackoverflow.com/questions/7010356/android-samsung-video-extra-size-limit-600k-max-mms
            intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 9437184L); // 9*1024*1024
//					                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 300);//70s
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            act.startActivityForResult(intent, requestCode);//CAMERA_ACTIVITY = 1
        }
    }
}
