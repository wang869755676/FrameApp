package com.company.ui.activty;

import android.Manifest;
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
import com.company.exception.ApiException;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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
        EventBus.getDefault().register(this);
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
        MyApplication.getInstance().getHttpUtils().getUsers(1,false)  // 对其进行全局处理
                .map(new Function<Reply<List<User>>, List<User>>() {

            @Override
            public List<User> apply(@NonNull Reply<List<User>> listReply) throws Exception {
                if(!listReply.isEncrypted()){
                    throw  new ApiException(2,"返回码异常");
                }
                return listReply.getData();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(@NonNull List<User>listReply) throws Exception {
                        for (User user :
                                listReply) {
                            Log.e("===", user.toString());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e("===",throwable.getMessage());
                    }
                });

        permission();
    }
     // 权限管理
    private void permission() {
        RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity instance
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                       Log.e("===","granted");
                    } else {
                        Log.e("===","deny");
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

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
