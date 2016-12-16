package andfans.com.mylbs.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import andfans.com.mylbs.R;
import andfans.com.mylbs.util.RecyclerAdapterBankCard;

public class ManageBankCard extends AppCompatActivity {

    RecyclerView bankView;
    RecyclerAdapterBankCard adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bank_card);

        initView();
    }

    private void initView() {
        bankView = (RecyclerView) findViewById(R.id.bankRecyclerView);
        adapter = new RecyclerAdapterBankCard(R.layout.recyclerview_bankcard_item, this);
        bankView.setHasFixedSize(true);
        bankView.setLayoutManager(new LinearLayoutManager(this));
        bankView.setAdapter(adapter);
    }
}
