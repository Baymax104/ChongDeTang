package com.cdtde.chongdetang;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.blankj.utilcode.util.EncryptUtils;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.cdtde.chongdetang", appContext.getPackageName());
    }

    @Test
    public void testMd5() {
        String s1 = "123";
        String s2 = "456";
        String s3 = "123";
        String r1 = EncryptUtils.encryptMD5ToString(s1);
        String r2 = EncryptUtils.encryptMD5ToString(s2);
        String r3 = EncryptUtils.encryptMD5ToString(s3);
        assertEquals(r1, r3);
    }

    @Test
    public void testReg() {
        String pattern = "^[a-zA-Z\\d._]+$";
        assertTrue("wzYU1.23_4".matches(pattern));
    }
}