package com.cpxiao.twofortwo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cpxiao.R;
import com.cpxiao.twofortwo.mode.extra.GameMode;
import com.cpxiao.twofortwo.view.GameView;

/**
 * @author cpxiao on 2017/08/23.
 */

public class GameFragment extends Fragment {

    private int mCountX = GameMode.COUNT_XY_DEFAULT;
    private int mCountY = GameMode.COUNT_XY_DEFAULT;

    public static GameFragment newInstance(Bundle bundle) {
        GameFragment fragment = new GameFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        Bundle bundle;
        //        if (savedInstanceState != null) {
        //            bundle = savedInstanceState;
        //        } else {
        //            bundle = getArguments();
        //        }
        bundle = getArguments();
        if (bundle != null) {
            mCountX = bundle.getInt(GameMode.MODE_X, GameMode.COUNT_XY_DEFAULT);
            mCountY = bundle.getInt(GameMode.MODE_Y, GameMode.COUNT_XY_DEFAULT);
        }

        initWidget(view);
        return view;
    }

    private void initWidget(View view) {
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout_game);
        GameView gameView = new GameView(view.getContext(), mCountX, mCountY);
        layout.addView(gameView);
    }
}
