package com.cdtde.chongdetang.view.my.adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListInScroll extends ListView {
    public ListInScroll(Context context) {
        super(context);
    }

    public ListInScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListInScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredHeight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);//只写了这一句就搞定了
        super.onMeasure(widthMeasureSpec, measuredHeight);//这里需要将第二个参数改为我们测量好的measureHeight
    }
}


