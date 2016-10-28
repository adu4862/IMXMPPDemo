package com.yl.imxmppdemo.server;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.yl.imxmppdemo.MyApp;
import com.yl.imxmppdemo.provider.ContactProvider;
import com.yl.imxmppdemo.utils.NickUtil;
import com.yl.imxmppdemo.utils.ThreadUtils;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import java.util.Collection;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public  class Imservice extends Service {

    private static final String TAG = "Imservice";
    private Roster roster;

    @Override
    public void onCreate() {
        super.onCreate();// nitifycation
        Toast.makeText(getBaseContext(), "后台服务打开...",Toast.LENGTH_SHORT).show();
        ThreadUtils.runInThread(new Runnable() {

            @Override
            public void run() {
                roster = MyApp.conn.getRoster();
                roster.addRosterListener(listener);
                Collection<RosterEntry> list = roster.getEntries();
                for (RosterEntry person : list) {
                    saveOrUpdateRosterEntry(person);
                }

            }

        });

    }

    private RosterListener listener = new RosterListener() {
        @Override
        public void presenceChanged(Presence presence) {

        }

        // 重命名好友
        @Override
        public void entriesUpdated(Collection<String> addresses) {
            System.out.println("有人重命名好友了...");
            // printLn(addresses);
            for (String address : addresses) {
                RosterEntry person = roster.getEntry(address);
                saveOrUpdateRosterEntry(person);
            }
        }

        // 删除
        @Override
        public void entriesDeleted(Collection<String> addresses) {
            System.out.println("有人删除好友了...");
            printLn(addresses);
            for (String address : addresses) {
                getContentResolver().delete(ContactProvider.CONTACT_URI, ContactProvider.CONTACT.ACCOUNT + "=?", new String[]{address});
            }
        }

        private void printLn(Collection<String> addresses) {
            for (String address : addresses) {
                Log.d(TAG, "printLn: "+address);
            }

        }

        // 添加
        @Override
        public void entriesAdded(Collection<String> addresses) {
            System.out.println("有人添加新好友了...");
            printLn(addresses);
            for (String address : addresses) {
                RosterEntry person = roster.getEntry(address);
                saveOrUpdateRosterEntry(person);
            }
        }
    };

    public void saveOrUpdateRosterEntry(RosterEntry person) {
        ContentValues values = new ContentValues();
        String account = person.getUser();// 老王@itheima.com
        values.put(ContactProvider.CONTACT.ACCOUNT, person.getUser());
        String nick = person.getName();
        if (nick == null || "".equals(nick)) {
            nick = account.substring(0, account.indexOf("@"));
        }
        values.put(ContactProvider.CONTACT.NICK, nick);
        values.put(ContactProvider.CONTACT.AVATAR, 0);
        values.put(ContactProvider.CONTACT.SORT, NickUtil.getNick(nick));
        int count = getContentResolver().update(ContactProvider.CONTACT_URI, values, ContactProvider.CONTACT.ACCOUNT + "=?", new String[]{account});
        if (count < 1) {
            getContentResolver().insert(ContactProvider.CONTACT_URI, values);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        roster.removeRosterListener(listener);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

}

