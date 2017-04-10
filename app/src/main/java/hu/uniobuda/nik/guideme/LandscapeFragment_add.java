package hu.uniobuda.nik.guideme;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tothb on 2017. 04. 10..
 */

public class LandscapeFragment_add extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.add_landscape,container,false);
        return v;
    }
}
