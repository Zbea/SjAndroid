package com.hazz.kuangji.widget.Tools;

/**
 * Created by admin on 2016/7/22.
 */
import java.util.Comparator;

/**
 *
 * 类简要描述
 *
 * <p>
 * 类详细描述
 * </p>
 *
 * @author duanbokan
 *
 */

public class CountryComparator implements Comparator<CountrySortModel>
{

    @Override
    public int compare(CountrySortModel o1, CountrySortModel o2)
    {

        if (o1.sortLetters.equals("@") || o2.sortLetters.equals("#"))
        {
            return -1;
        }
        else if (o1.sortLetters.equals("#") || o2.sortLetters.equals("@"))
        {
            return 1;
        }
        else
        {
            return o1.sortLetters.compareTo(o2.sortLetters);
        }
    }

}
