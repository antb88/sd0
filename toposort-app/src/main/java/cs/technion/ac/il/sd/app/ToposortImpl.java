package cs.technion.ac.il.sd.app;

import com.google.inject.Inject;
import cs.technion.ac.il.sd.External;

import java.io.File;

/**
 * Created by Nati on 06/04/2016.
 */
public class ToposortImpl implements Toposort
{
    private final External external;

    @Inject
    public ToposortImpl(External external)
    {
        this.external = external;
    }

    @Override
    public void processFile(File file) {

    }
}
