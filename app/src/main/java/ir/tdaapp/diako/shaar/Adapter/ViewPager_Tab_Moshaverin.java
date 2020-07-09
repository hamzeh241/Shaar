package ir.tdaapp.diako.shaar.Adapter;



import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by Diako on 5/25/2019.
 */

public class ViewPager_Tab_Moshaverin extends FragmentStatePagerAdapter {


    List<Fragment> fragmentList;
    List<String> stringList;

    public ViewPager_Tab_Moshaverin(FragmentManager fm) {
        super(fm);
        fragmentList=new ArrayList<>();
        stringList=new ArrayList<>();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
       return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        stringList.add(title);
    }
}
