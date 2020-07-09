package ir.tdaapp.diako.shaar.ETC;

import java.util.Random;

/**
 * Created by Diako on 7/9/2019.
 */

public class GetRandom {

    public static long GetLong(){

        Random random=new Random();
        int A=random.nextInt(100);
        int B=random.nextInt(110);

        return A*B;
    }
}
