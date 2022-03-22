package com.techyourchance.multithreading.demonstrations.atomicity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.techyourchance.multithreading.R;
import com.techyourchance.multithreading.common.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.atomic.AtomicInteger;


@SuppressLint("SetTextI18n")
public class AtomicityDemonstrationFragment extends BaseFragment {

    private static final int COUNT_UP_TO = 1000;
    private static final int NUM_OF_COUNTER_THREADS = 100;

    public static Fragment newInstance() {
        return new AtomicityDemonstrationFragment();
    }

    private TextView mTxtFinalCount;
    private Button mBtnStartCount;
    private final AtomicInteger mCount = new AtomicInteger(0);
    private Handler mUiHandler = new Handler(Looper.getMainLooper());
    private int daveCount=0;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_atomicity_demonstration, container, false);

        mTxtFinalCount = view.findViewById(R.id.txt_final_count);

        mBtnStartCount = view.findViewById(R.id.btn_start_count);
        mBtnStartCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCount();
            }
        });

        return view;
    }

    @Override
    protected String getScreenTitle() {
        return "";
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void startCount() {

        //here I am going to span 100 threads
        //make each thread count upto 1000
        //display the result while disabling the button

        mCount.set(0);
        mBtnStartCount.setEnabled(false);
        mTxtFinalCount.setText("");

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < COUNT_UP_TO; i++){
                    startCountThread();
                }
            }
        }).start();

        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBtnStartCount.setEnabled(true);
                mTxtFinalCount.setText(String.valueOf(mCount.get()));

            }
        }, NUM_OF_COUNTER_THREADS *20);




    }

    private void startCountThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < NUM_OF_COUNTER_THREADS; i++ ){
                    mCount.getAndIncrement();
                }
            }
        }).start();



    }


}
