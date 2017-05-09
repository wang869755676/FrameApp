package com.company.ui.activty;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

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
    }

    class Holer extends RecyclerView.ViewHolder {

        public TextView tv;

        public Holer(View itemView) {
            super(itemView);
            tv = (TextView) itemView;
        }
    }
}
