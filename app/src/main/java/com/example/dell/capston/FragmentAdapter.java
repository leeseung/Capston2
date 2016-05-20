package com.example.dell.capston;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dell.capston.fragment.BeaconFragment;
import com.example.dell.capston.fragment.LLSettingsFragment;
import com.example.dell.capston.fragment.MacroFragment;
import com.example.dell.capston.interfaces.IFragmentListener;

import java.util.Locale;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
	
	public static final String TAG = "FragmentAdapter";
	
	// Total count
	public static final int FRAGMENT_COUNT = 3;
	
    // Fragment position
    public static final int FRAGMENT_POS_BEACON = 0;
    public static final int FRAGMENT_POS_MACRO = 1;
    public static final int FRAGMENT_POS_SETTINGS = 2;
    
	public static final String ARG_SECTION_NUMBER = "section_number";
    
    // System
    private Context mContext = null;
    private IFragmentListener mFragmentListener = null;
    private Handler mActivityHandler = null;
    
    private Fragment mTimelineFragment = null;
    private Fragment mGraphFragment = null;
    private Fragment mLLSettingsFragment = null;
    
    public FragmentAdapter(FragmentManager fm, Context c, IFragmentListener l) {
		super(fm);
		mContext = c;
		mFragmentListener = l;

	}
    
	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		Fragment fragment;
		//boolean needToSetArguments = false;
		
		if(position == FRAGMENT_POS_BEACON) {
			if(mTimelineFragment == null) {
				mTimelineFragment = new BeaconFragment(mContext, mFragmentListener, mActivityHandler);
				//needToSetArguments = true;
			}
			fragment = mTimelineFragment;

		} else if(position == FRAGMENT_POS_MACRO) {
			if(mGraphFragment == null) {
				mGraphFragment = new MacroFragment(mContext, mFragmentListener, mActivityHandler);
				//needToSetArguments = true;
			}
			fragment = mGraphFragment;
			
		} else if(position == FRAGMENT_POS_SETTINGS) {
			if(mLLSettingsFragment == null) {
				mLLSettingsFragment = new LLSettingsFragment(mContext, mFragmentListener, mActivityHandler);
				//needToSetArguments = true;
			}
			fragment = mLLSettingsFragment;
			
		} else {
			fragment = null;
		}
		
		// TODO: If you have something to notify to the fragment.
		/*
		if(needToSetArguments) {
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
		}
		*/
		
		return fragment;
	}

	@Override
	public int getCount() {
		return FRAGMENT_COUNT;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case FRAGMENT_POS_BEACON:
			return mContext.getString(R.string.title_beacon).toUpperCase(l);
		case FRAGMENT_POS_MACRO:
			return mContext.getString(R.string.title_macro).toUpperCase(l);
		case FRAGMENT_POS_SETTINGS:
			return mContext.getString(R.string.title_ll_settings).toUpperCase(l);
		}
		return null;
	}
    
    
}
