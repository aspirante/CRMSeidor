package com.crystalis.menubar;

import java.util.HashMap;
import java.util.Set;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.crystalis.view.R;

public class FragmentSwapManager {
	// Variables
	private static int animationEnter;
	private static int animationExit;

	public static void changeFragment(FragmentActivity actualActivity, Fragment fragmentInit, Fragment fragmentSwap,
			HashMap<String, String> extras, boolean isAnimated, AnimationType animation) {
		getAnimation(animation);
		setParams(extras, fragmentSwap);
		FragmentTransaction ft = actualActivity.getSupportFragmentManager().beginTransaction();
		if (isAnimated)
			ft.setCustomAnimations(animationEnter, animationExit);
		ft.replace(fragmentInit.getId(), fragmentSwap);
		// ft.hide(fragmentInit);
		// ft.show(fragmentSwap);

		ft.commit();
	}

	public static void changeFragment(FragmentActivity actualActivity, int id, Fragment fragmentSwap,
			HashMap<String, String> extras, boolean isAnimated, AnimationType animation) {
		getAnimation(animation);
		setParams(extras, fragmentSwap);
		FragmentTransaction ft = actualActivity.getSupportFragmentManager().beginTransaction();
		if (isAnimated)
			ft.setCustomAnimations(animationEnter, animationExit);
		ft.replace(id, fragmentSwap);
		// ft.hide(fragmentInit);
		// ft.show(fragmentSwap);

		ft.commit();
	}

	public static void changeRemoveFragment(FragmentActivity actualActivity, Fragment fragmentInit,
			Fragment fragmentSwap, Fragment fragmentRemove, HashMap<String, String> extras, boolean isAnimated,
			AnimationType animation) {
		getAnimation(animation);
		setParams(extras, fragmentSwap);
		FragmentTransaction ft = actualActivity.getSupportFragmentManager().beginTransaction();
		if (isAnimated)
			ft.setCustomAnimations(animationEnter, animationExit);
		ft.replace(fragmentInit.getId(), fragmentSwap);
		// ft.show(fragmentInit);
		// ft.hide(fragmentSwap);
		ft.remove(fragmentRemove);
		ft.commit();
	}

	private static void getAnimation(AnimationType animation) {
		switch (animation) {
			case NoAnimation:
				animationEnter = animationExit = 0;
				break;
			case LeftToRight:
				animationEnter = R.anim.animation_enter_left_right;
				animationExit = R.anim.animation_exit_left_right;
				break;
			case RightToLeft:
				animationEnter = R.anim.animation_enter_right_left;
				animationExit = R.anim.animation_exit_right_left;
				break;
			case UpToDown:
				animationEnter = R.anim.animation_exit_up_down;
				animationExit = R.anim.animation_enter_up_down;
				break;
			case DownToUp:
				animationEnter = R.anim.animation_enter_down_up;
				animationExit = R.anim.animation_exit_down_up;
				break;
		}
	}

	private static void setParams(HashMap<String, String> extras, Fragment fragmentSwap) {
		if (extras != null) {
			Bundle bundle = new Bundle();
			Set<String> keys = extras.keySet();
			for (String key : keys) {
				bundle.putString(key, extras.get(key));
			}
			fragmentSwap.setArguments(bundle);
		}
	}
}
