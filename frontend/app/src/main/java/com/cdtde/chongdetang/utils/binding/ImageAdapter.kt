package com.cdtde.chongdetang.utils.binding

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ImageUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.ImageViewTarget
import com.cdtde.chongdetang.R
import com.cdtde.chongdetang.repository.AppKey
import com.cdtde.chongdetang.utils.ValidateUtil

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/6/16 18:17
 *@Version 1
 */
object ImageAdapter {

    class ImageTarget(view: ImageView) : ImageViewTarget<Bitmap>(view) {
        override fun setResource(resource: Bitmap?) {
            if (resource == null) return
            val imgWidth = ConvertUtils.dp2px(resource.width.toFloat())
            val imgHeight = ConvertUtils.dp2px(resource.height.toFloat())
            val scaleWidth = view.width
            val scaleHeight = scaleWidth * imgHeight / imgWidth
            val bm = ImageUtils.scale(resource, scaleWidth, scaleHeight)
            val params = view.layoutParams
            params.height = scaleHeight
            params.width = scaleWidth
            view.layoutParams = params
            view.setImageBitmap(bm)
        }
    }

    @JvmStatic
    @BindingAdapter("img_url", "img_cos", "img_rounded", "img_fix", "img_size", requireAll = false)
    fun ImageView.img(url: String?, cos: Boolean, rounded: Boolean, fix: Boolean, size: Int?) {
        if (url == null) {
            Glide.with(this).asBitmap().load(R.drawable.loading).into(this)
            return
        }

        var options = RequestOptions()
            .placeholder(R.drawable.loading)
            .skipMemoryCache(true)
        if (rounded) {
            options = options.transform(CenterCrop(), RoundedCorners(ConvertUtils.dp2px(10f)))
        }
        if (size != null) {
            options = options.override(ConvertUtils.dp2px(size.toFloat()))
        }

        val path = if (cos) "${AppKey.COS_URL}/$url" else url
        val request = Glide.with(this).asBitmap().load(path).apply(options)
        if (fix) {
            request.into(this)
        } else {
            request.into(ImageTarget(this))
        }
    }

    @JvmStatic
    @BindingAdapter("img_uri")
    fun ImageView.imgUri(uri: String) = setImageURI(Uri.parse(uri))

    @JvmStatic
    @BindingAdapter("img_user_photo")
    fun ImageView.userPhoto(photo: String) {
        if (ValidateUtil.validateUri(photo)) {
            setImageURI(Uri.parse(photo))
        } else if (photo.endsWith(".jpg")) {
            val path = "${AppKey.COS_URL}/$photo"
            Glide.with(this)
                .load(path)
                .into(this)
        }
    }

    @JvmStatic
    @BindingAdapter("img_res")
    fun ImageView.imgRes(drawable: Drawable) {
        Glide.with(this)
            .load(drawable)
            .into(this)
    }
}