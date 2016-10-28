package com.yl.imxmppdemo.utils;

import android.content.Context;
import android.database.Cursor;

import com.yl.imxmppdemo.MyApp;
import com.yl.imxmppdemo.provider.ContactProvider;

import opensource.jpinyin.PinyinFormat;
import opensource.jpinyin.PinyinHelper;


public class NickUtil {

	public static String getNick(String nick) {
		// PinyinHelper.convertToPinyinString(汉字, 符号, 格式);ge shi
		return PinyinHelper.convertToPinyinString(nick, "", PinyinFormat.WITHOUT_TONE).toUpperCase();
	}
	
	//老王@itheima.com/Sparck 2.63
	public static String filterAccount(String account) {
		account=	account.substring(0,account.indexOf("@"));
		account=account+"@"+ MyApp.SERVICE_NAME;
		return account;
	}

	public static String getNick(Context context, String account) {
		String nick = "";
		Cursor c = context.getContentResolver().query(ContactProvider.CONTACT_URI, null, ContactProvider.CONTACT.ACCOUNT + "=?", new String[] { account }, null);

		if (c.moveToFirst()) {
			nick = c.getString(c.getColumnIndex(ContactProvider.CONTACT.NICK));
		}
		c.close();

		if (nick == null || "".equals(nick)) {
			nick = account.substring(0, account.indexOf("@"));
		}
		return nick;
	}
}
