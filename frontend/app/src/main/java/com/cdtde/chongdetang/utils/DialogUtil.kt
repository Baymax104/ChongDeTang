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
 * 对话框工具类，基于XPopup库.
 */
object DialogUtil {

    /**
     * 反射调用构造器Constructor(Context context)创建XPopup对话框并显示.
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

    /**
     * 创建普通Loading对话框.
     * @param context Context
     * @return 对话框对象
     */
    @JvmStatic
    fun createLoading(context: Context): LoadingPopupView {
        return XPopup.Builder(context)
            .dismissOnTouchOutside(false)
            .asLoading()
    }

    /**
     * 创建时间选择器对话框.
     * @param context Context
     * @param onConfirm 选择确定回调
     * @return 时间选择器对象
     */
    @JvmStatic
    fun createTimePicker(context: Context, onConfirm: OnTimePickerConfirm): TimePickerPopup {
        val start = Calendar.getInstance()
        start[Calendar.YEAR] = 1900
        start[Calendar.MONTH] = 0
        start[Calendar.DAY_OF_MONTH] = 1
        val now = Calendar.getInstance()
        val end = Calendar.getInstance()
        end[Calendar.YEAR] = now[Calendar.YEAR] + 20
        val dialog = TimePickerPopup(context)
            .setDefaultDate(now)
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

    /**
     * 创建依附布局的对话框.
     * @param T [AttachPopupView]子类类型
     * @param context Context
     * @param cl [AttachPopupView]子类Class对象
     * @param view 对话框依附的布局
     * @return 依附对话框对象
     */
    @JvmStatic
    @Suppress("Unchecked_Cast")
    fun <T : AttachPopupView> createAttachDialog(context: Context, cl: Class<T>, view: View): T {
        val builder = XPopup.Builder(context)
            .hasShadowBg(false)
            .isDestroyOnDismiss(true)
            .isLightStatusBar(true)
            .atView(view)
        val constructor = cl.getConstructor(Context::class.java)
        val dialog = constructor.newInstance(context)
        return builder.asCustom(dialog) as T
    }

    /**
     * 创建地址选择器.
     * @param activity Activity
     * @param listener 选择确定回调
     * @return 地址选择器对象
     */
    @JvmStatic
    fun createAddressPicker(activity: Activity, listener: OnAddressPickedListener): AddressPicker {
        return AddressPicker(activity).apply {
            setAddressMode(AddressMode.PROVINCE_CITY)
            setTitle("选择城市")
            wheelLayout.setSelectedTextBold(true)
            setOnAddressPickedListener(listener)
        }
    }

    /**
     * 创建可观察网络生命周期的加载对话框.
     * @see NetLoadingView
     * @param activity Activity
     * @return 加载对话框对象
     */
    @JvmStatic
    fun createNetLoading(activity: Activity): NetLoadingView {
        val dialog = NetLoadingView(activity)
        return XPopup.Builder(activity)
            .dismissOnTouchOutside(false)
            .asCustom(dialog) as NetLoadingView
    }

}

/**
 * 时间选择器确认回调接口.
 */
interface OnTimePickerConfirm {
    fun onConfirm(date: Date?)
}
