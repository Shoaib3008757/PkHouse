package pk.house.pkhouse.adapter;

/**
 * Created by Shoaib Anwar on 17-Mar-17.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import pk.house.pkhouse.SubmitFirstPage;
import pk.house.pkhouse.SubmitSecondPage;
import pk.house.pkhouse.SubmitThirldPage;

/**
 * Created by User-10 on 13-Mar-17.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumberOfTabs;

    public PagerAdapter (FragmentManager fm, int NumberOfTabs){
        super(fm);
        this.mNumberOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position){

        switch (position){
            case  0:
                SubmitFirstPage sb = new SubmitFirstPage();
                return sb;
            case 1:
                SubmitSecondPage ss = new SubmitSecondPage();
                return ss;
            case 2:
                SubmitThirldPage st = new SubmitThirldPage();
                return st;
            default:
                return null;
        }
    }

    @Override
    public int getCount(){
        return mNumberOfTabs;
    }
}
