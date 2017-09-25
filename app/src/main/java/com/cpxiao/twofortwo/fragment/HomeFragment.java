package com.cpxiao.twofortwo.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.cpxiao.R;
import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.gamelib.fragment.BaseZAdsFragment;
import com.cpxiao.twofortwo.mode.extra.Extra;
import com.cpxiao.twofortwo.mode.extra.GameMode;

/**
 * @author cpxiao on 2017/8/23.
 * @version cpxiao on 2017/9/25. 改为继承BaseZAdsFragment
 */

public class HomeFragment extends BaseZAdsFragment implements View.OnClickListener {

    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment fragment = new HomeFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Button buttonEasy = (Button) view.findViewById(R.id.easy);
        Button buttonNormal = (Button) view.findViewById(R.id.normal);
        Button buttonHard = (Button) view.findViewById(R.id.hard);
        Button buttonInsane = (Button) view.findViewById(R.id.insane);
        Button buttonBestScore = (Button) view.findViewById(R.id.best_score);
        Button buttonSettings = (Button) view.findViewById(R.id.settings);
        Button buttonQuit = (Button) view.findViewById(R.id.quit);

        buttonEasy.setOnClickListener(this);
        buttonNormal.setOnClickListener(this);
        buttonHard.setOnClickListener(this);
        buttonInsane.setOnClickListener(this);
        buttonBestScore.setOnClickListener(this);
        buttonSettings.setOnClickListener(this);
        buttonQuit.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        Context context = getHoldingActivity();

        Bundle bundle = new Bundle();

        switch (id) {
            case R.id.easy: {
                bundle.putInt(GameMode.MODE, GameMode.EASY[0]);
                bundle.putInt(GameMode.MODE_X, GameMode.EASY[1]);
                bundle.putInt(GameMode.MODE_Y, GameMode.EASY[2]);
                addFragment(GameFragment.newInstance(bundle));
                break;
            }
            case R.id.normal: {
                bundle.putInt(GameMode.MODE, GameMode.NORMAL[0]);
                bundle.putInt(GameMode.MODE_X, GameMode.NORMAL[1]);
                bundle.putInt(GameMode.MODE_Y, GameMode.NORMAL[2]);
                addFragment(GameFragment.newInstance(bundle));
                break;
            }
            case R.id.hard: {
                bundle.putInt(GameMode.MODE, GameMode.HARD[0]);
                bundle.putInt(GameMode.MODE_X, GameMode.HARD[1]);
                bundle.putInt(GameMode.MODE_Y, GameMode.HARD[2]);
                addFragment(GameFragment.newInstance(bundle));
                break;
            }
            case R.id.insane: {
                bundle.putInt(GameMode.MODE, GameMode.INSANE[0]);
                bundle.putInt(GameMode.MODE_X, GameMode.INSANE[1]);
                bundle.putInt(GameMode.MODE_Y, GameMode.INSANE[2]);
                addFragment(GameFragment.newInstance(bundle));
                break;
            }
            case R.id.best_score: {
                showBestScoreDialog(context);
                break;
            }
            case R.id.settings: {
                //                addFragment(SettingsFragment.newInstance(null));
                break;
            }
            case R.id.quit: {
                removeFragment();
                break;
            }

        }
    }

    private void showBestScoreDialog(Context context) {
        String format = "%-20s";
        String easy = getString(R.string.easy) + ": ";
        String normal = getString(R.string.normal) + ": ";
        String hard = getString(R.string.hard) + ": ";
        String insane = getString(R.string.insane) + ": ";
        String msg = String.format(format, easy) + PreferencesUtils.getLong(context, Extra.Key.getBestScoreKey(GameMode.EASY[0]), 0) + "\n"
                + String.format(format, normal) + PreferencesUtils.getLong(context, Extra.Key.getBestScoreKey(GameMode.NORMAL[0]), 0) + "\n"
                + String.format(format, hard) + PreferencesUtils.getLong(context, Extra.Key.getBestScoreKey(GameMode.HARD[0]), 0) + "\n"
                + String.format(format, insane) + PreferencesUtils.getLong(context, Extra.Key.getBestScoreKey(GameMode.INSANE[0]), 0);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.best_score)
                .setMessage(msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

}
