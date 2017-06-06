package com.company.ui.activty;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.MyApplication;
import com.company.R;
import com.company.db.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.rx_cache2.Reply;

public class MainActivity extends Activity {
  /*  @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tabs)
    TabLayout tabs;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(this)
                .build();
        Realm realm = Realm.getInstance(realmConfig);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

            }
        });
       /* rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new RecyclerView.Adapter<Holer>() {

            @Override
            public Holer onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView textView = new TextView(MainActivity.this);
                return new Holer(textView);
            }

            @Override
            public void onBindViewHolder(Holer holder, int position) {
                holder.tv.setText("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
            }

            @Override
            public int getItemCount() {
                return 30;
            }
        });
        tabs.addTab(tabs.newTab().setText("tab1"));
        tabs.addTab(tabs.newTab().setText("tab2"));
        tabs.addTab(tabs.newTab().setText("tab2"));*/
        MyApplication.getInstance().getHttpUtils().getUsers(1,false)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Reply<List<User>>>() {
                    @Override
                    public void accept(@NonNull Reply<List<User>> listReply) throws Exception {
                        for (User user :
                                listReply.getData()) {
                            Log.e("===", user.toString());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e("===",throwable.getMessage());
                    }
                });
    }

    class Holer extends RecyclerView.ViewHolder {

        public TextView tv;

        public Holer(View itemView) {
            super(itemView);
            tv = (TextView) itemView;
        }
    }
}
