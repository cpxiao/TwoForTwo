package com.cpxiao.twofortwo.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cpxiao.R;
import com.cpxiao.twofortwo.mode.extra.GameMode;

/**
 * @author cpxiao on 2017/08/23.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final boolean DEBUG = false;
    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
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

//        buttonEasy.setVisibility(View.GONE);
//        buttonNormal.setVisibility(View.GONE);
//        buttonInsane.setVisibility(View.GONE);
//        buttonBestScore.setVisibility(View.GONE);
        buttonSettings.setVisibility(View.GONE);
        return view;
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        Bundle bundle = new Bundle();

        switch (id) {
            case R.id.easy: {
                bundle.putInt(GameMode.MODE_X, GameMode.EASY[0]);
                bundle.putInt(GameMode.MODE_Y, GameMode.EASY[1]);
                loadGame(bundle);
                break;
            }
            case R.id.normal: {
                bundle.putInt(GameMode.MODE_X, GameMode.NORMAL[0]);
                bundle.putInt(GameMode.MODE_Y, GameMode.NORMAL[1]);
                loadGame(bundle);
                break;
            }
            case R.id.hard: {
                bundle.putInt(GameMode.MODE_X, GameMode.HARD[0]);
                bundle.putInt(GameMode.MODE_Y, GameMode.HARD[1]);
                loadGame(bundle);
                break;
            }
            case R.id.insane: {
                bundle.putInt(GameMode.MODE_X, GameMode.INSANE[0]);
                bundle.putInt(GameMode.MODE_Y, GameMode.INSANE[1]);
                loadGame(bundle);
                break;
            }
            case R.id.best_score: {
                showBestScoreDialog();
                break;
            }
            case R.id.settings: {
                loadSetting();
                break;
            }
            case R.id.quit: {
                showQuitConfirmDialog();
                break;
            }

        }
    }

    private void loadGame(Bundle bundle) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GameFragment gameFragment = GameFragment.newInstance(bundle);
        fragmentTransaction.hide(this);
        fragmentTransaction.add(R.id.fragment_container, gameFragment, "GameFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void loadSetting() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SettingsFragment settingsFragment = SettingsFragment.newInstance(null);
        fragmentTransaction.hide(this);
        fragmentTransaction.add(R.id.fragment_container, settingsFragment, "SettingsFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void showBestScoreDialog() {

    }

    private void showQuitConfirmDialog() {
        final Activity activity = getActivity();
        Dialog dialog = new AlertDialog.Builder(activity)
                //                .setTitle(R.string.quit_msg)
                .setMessage(R.string.quit_msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.finish();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        //            dialog.setCancelable(true);
        //            dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
