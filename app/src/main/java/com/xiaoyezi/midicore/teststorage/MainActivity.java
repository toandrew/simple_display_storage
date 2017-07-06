package com.xiaoyezi.midicore.teststorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.system_total_size)
    EditText mSystemTotalSizeView;

    @BindView(R.id.system_free_size)
    EditText mSystemFreeSizeView;

    @BindView(R.id.data_total_size)
    EditText mDataTotalSizeView;

    @BindView(R.id.data_free_size)
    EditText mDataFreeSizeView;

    @BindView(R.id.sd_total_size)
    EditText mSDTotalSizeView;

    @BindView(R.id.sd_free_size)
    EditText mSDFreeSizeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        int systemTotalSize = AppUtils.getTotalSystemBlocks() / 1024;
        mSystemTotalSizeView.setText(Integer.toString(systemTotalSize) + "M");

        int systemFreeSize = AppUtils.getAvailableSystemBlocks() / 1024;
        mSystemFreeSizeView.setText(Integer.toString(systemFreeSize) + "M");

        long dataTotalSize = (long)(AppUtils.getTotalInternalMemorySize()/(1024*1024));
        mDataTotalSizeView.setText(Long.toString(dataTotalSize) + "M");

        int dataFreeSize = (int)(AppUtils.getAvailableInternalMemorySize()/(1024*1024));
        mDataFreeSizeView.setText(Integer.toString(dataFreeSize) + "M");

        int sdTotalSize = AppUtils.getTotalSDCardBlocks() / 1024;
        mSDTotalSizeView.setText(Integer.toString(sdTotalSize) + "M");

        int sdFreeSize = AppUtils.getAvailableSDCardBlocks() / 1024;
        mSDFreeSizeView.setText(Integer.toString(sdFreeSize) + "M");
    }

}
