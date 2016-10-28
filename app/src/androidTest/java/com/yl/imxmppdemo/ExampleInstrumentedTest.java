package com.yl.imxmppdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.yl.imxmppdemo.provider.ContactProvider;
import com.yl.imxmppdemo.utils.NickUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private Context appContext;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.yl.imxmppdemo", appContext.getPackageName());
    }
    public void testInsert() throws Exception{
        appContext = InstrumentationRegistry.getTargetContext();
        ContentValues values = new ContentValues();
        values.put(ContactProvider.CONTACT.ACCOUNT, "老王 @als-20160917znn");
        values.put(ContactProvider.CONTACT.NICK, "老王");
        values.put(ContactProvider.CONTACT.AVATAR, 0);
        values.put(ContactProvider.CONTACT.SORT, NickUtil.getNick("老王"));
        appContext.getContentResolver().insert(ContactProvider.CONTACT_URI, values);
    }

    public void testUpdate() throws Exception{
        appContext = InstrumentationRegistry.getTargetContext();
        ContentValues values = new ContentValues();
        values.put(ContactProvider.CONTACT.ACCOUNT, "老王 @als-20160917znn");
        values.put(ContactProvider.CONTACT.NICK, "老王222");
        values.put(ContactProvider.CONTACT.AVATAR, 0);
        values.put(ContactProvider.CONTACT.SORT, NickUtil.getNick("老王22"));
        appContext.getContentResolver().update(ContactProvider.CONTACT_URI, values, ContactProvider.CONTACT.ACCOUNT + "=?", new String[]{"老王 @als-20160917znn"});
    }

    public void testDelete() throws Exception{
        appContext = InstrumentationRegistry.getTargetContext();

        appContext.getContentResolver().delete(ContactProvider.CONTACT_URI, ContactProvider.CONTACT.ACCOUNT + "=?", new String[]{"老王 @als-20160917znn"});
    }

    public void testQuery() {
        appContext = InstrumentationRegistry.getTargetContext();
        Cursor c = appContext.getContentResolver().query(ContactProvider.CONTACT_URI, null, null, null, ContactProvider.CONTACT.SORT + " ASC");// A-->Z
        while (c.moveToNext()) {
            for (int i = 0; i < c.getColumnCount(); i++) {
                System.out.print(c.getString(i) + "  ");
            }
            System.out.println();
        }
        c.close();
    }
}
