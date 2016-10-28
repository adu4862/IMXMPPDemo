package com.yl.imxmppdemo.fragment;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yl.imxmppdemo.R;
import com.yl.imxmppdemo.provider.ContactProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class SelfInfoFragment extends BaseFragment {

    private ListView contact_list;
    private Cursor c;
    private MyAdapter myAdapter;
    private MyObserver myObserver = new MyObserver(new Handler());

    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getContentResolver().registerContentObserver(ContactProvider.CONTACT_URI,true,myObserver);
        View view = View.inflate(getContext(), R.layout.fragment_selfinfo, null);
        contact_list = (ListView) view.findViewById(R.id.lv_contact_list);
        setAdapter();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getContentResolver().unregisterContentObserver(myObserver);
    }

    private void setAdapter() {

        c = getActivity().getContentResolver().query(ContactProvider.CONTACT_URI, null, null, null, ContactProvider.CONTACT.SORT + " ASC");
        if (c.getCount() < 1) {
            return;
        }
        //第三个参数表示观察到requry执行,自动刷新
        myAdapter = new MyAdapter(getActivity(), c, true);
        contact_list.setAdapter(myAdapter);
    }

    private class MyObserver extends ContentObserver {
        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            myAdapter.getCursor().requery();
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            myAdapter.getCursor().requery();
        }
    }



    class MyAdapter extends CursorAdapter {
        Cursor c;
        Context context;
        public MyAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
            this.c = c;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_contact, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            c.moveToPosition(position);//##游标移动
            String account = c.getString(c.getColumnIndex(ContactProvider.CONTACT.ACCOUNT));

            String nick = c.getString(c.getColumnIndex(ContactProvider.CONTACT.NICK));
            viewHolder.account.setText(account);
            viewHolder.nick.setText(nick);
            return convertView;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return null;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
        }

        class ViewHolder {
            @BindView(R.id.head)
            ImageView head;
            @BindView(R.id.nick)
            TextView nick;
            @BindView(R.id.account)
            TextView account;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
