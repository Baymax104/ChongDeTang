package com.cdtde.chongdetang.utils

import android.app.Activity
import android.content.Context
import android.view.View
import com.cdtde.chongdetang.base.web.NetLoadingView
import com.github.gzuliyujiang.wheelpicker.AddressPicker
import com.github.gzuliyujiang.wheelpicker.annotation.AddressMode
import com.github.gzuliyujiang.wheelpicker.contract.OnAddressPickedListener
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.impl.LoadingPopupView
import com.lxj.xpopupext.listener.TimePickerListener
import com.lxj.xpopupext.popup.TimePickerPopup
import java.util.*

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/6/20 11:40
 *@Version 1
 */
object DialogUtil {

    /**
     * 反射调用构造器Constructor(Context context)创建XPopup对话框并显示
     * @param context context
     * @param type 对话框Class对象
     * @param builder 对话框builder对象
     * @param <T> 对话框类型
     */
    @JvmStatic
    @JvmOverloads
    @Suppress("Unchecked_Cast")
    fun <T : BasePopupView> create(
        context: Context,
        type: Class<T>,
        builder: XPopup.Builder? = null
    ): T {
        val mBuilder = builder ?: XPopup.Builder(context)
        val constructor = type.getConstructor(Context::class.java)
        val dialog = constructor.newInstance(context)
        return mBuilder.asCustom(dialog) as T
    }

    @JvmStatic
    fun createLoading(context: Context): LoadingPopupView {
        return XPopup.Builder(context)
            .dismissOnTouchOutside(false)
            .asLoading()
    }

    @JvmStatic
    fun createTimePicker(context: Context, onConfirm: OnTimePickerConfirm): TimePickerPopup {
        val start = Calendar.getInstance()
        start[Calendar.YEAR] = 1900
        start[Calendar.MONTH] = 0
        start[Calendar.DAY_OF_MONTH] = 1
        val end = Calendar.getInstance()
        val dialog = TimePickerPopup(context)
            .setDefaultDate(end)
            .setDateRange(start, end)
            .setTimePickerListener(object : TimePickerListener {
                override fun onTimeChanged(date: Date?) {
                }

                override fun onTimeConfirm(date: Date?, view: View?) {
                    onConfirm.onConfirm(date)
                }

                override fun onCancel() {
                }
            })
        return XPopup.Builder(context).asCustom(dialog) as TimePickerPopup
    }

    @JvmStatic
    @Suppress("Unchecked_Cast")
    fun <T : AttachPopupView> createAttachDialog(context: Context, cl: Class<T>, view: View): T {
        val builder = XPopup.Builder(context).hasBlurBg(false).atView(view)
        val constructor = cl.getConstructor(Context::class.java)
        val dialog = constructor.newInstance(context)
        return builder.asCustom(dialog) as T
    }

    @JvmStatic
    fun createAddressPicker(activity: Activity, listener: OnAddressPickedListener): AddressPicker {
        return AddressPicker(activity).apply {
            setAddressMode(AddressMode.PROVINCE_CITY)
            setTitle("选择城市")
            wheelLayout.setSelectedTextBold(true)
            setOnAddressPickedListener(listener)
        }
    }

    @JvmStatic
    fun createNetLoading(activity: Activity): NetLoadingView {
        val dialog = NetLoadingView(activity)
        return XPopup.Builder(activity)
            .dismissOnTouchOutside(false)
            .asCustom(dialog) as NetLoadingView
    }

}

@FunctionalInterface
fun interface OnTimePickerConfirm {
    fun onConfirm(date: Date?)
}
