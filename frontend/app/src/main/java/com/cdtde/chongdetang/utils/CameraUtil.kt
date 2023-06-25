package com.cdtde.chongdetang.utils

import android.content.Intent
import android.provider.MediaStore
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.UriUtils
import java.io.File
import java.util.*

/**
 * 相机、相册工具类
 */
object CameraUtil {
    /**
     * 启动相机
     * @param file 照片文件File对象
     * @return intent
     */
    @JvmStatic
    fun startCamera(file: File?): Intent? {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (file != null) {
            val uri = UriUtils.file2Uri(file)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            return intent
        }
        return null
    }

    /**
     * 在/storage/emulated/0/Android/data/package/files目录下新建文件
     * @param fileDir 是否在files目录下，默认在cache目录下
     * @return file
     */
    @JvmStatic
    fun createNewFile(fileDir: Boolean): File? {
        val filename = DateDetailFormatter.format(Date())
        val parent =
            if (fileDir) PathUtils.getExternalAppFilesPath() else PathUtils.getExternalAppCachePath()
        if (parent == "") {
            return null
        }
        val file = File(parent, "$filename.jpg")
        file.createNewFile()
        return file
    }

    /**
     * 启动相册
     * @return intent
     */
    @JvmStatic
    fun startPhotoPicker(): Intent {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        return intent
    }

    @JvmStatic
    fun file2Base64(file: File?): String? {
        val bytes = FileIOUtils.readFile2BytesByStream(file) ?: return null
        return EncodeUtils.base64Encode2String(bytes)
    }

    @JvmStatic
    fun base64ToFile(base64: String?): File? {
        val bytes = EncodeUtils.base64Decode(base64)
        if (bytes.isEmpty()) {
            return null
        }
        val file = createNewFile(true)
        return if (FileIOUtils.writeFileFromBytesByStream(file, bytes)) {
            file
        } else null
    }
}