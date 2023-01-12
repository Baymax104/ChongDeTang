package com.cdtde.chongdetang.util;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 相机、相册工具类
 */
public class CameraUtil {

    public static Intent startCamera(File file) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (file != null) {
            Uri uri = UriUtils.file2Uri(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            return intent;
        }
        return null;
    }

    public static File createNewFile() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        String filename = format.format(new Date());
        String parent = PathUtils.getExternalAppFilesPath();
        if (parent.equals("")) {
            return null;
        }
        File file = new File(parent, filename + ".jpg");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static Intent startPhotoPicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        return intent;
    }

}
