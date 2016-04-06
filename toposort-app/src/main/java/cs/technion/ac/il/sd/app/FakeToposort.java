package cs.technion.ac.il.sd.app;

import com.google.inject.Inject;
import cs.technion.ac.il.sd.External;

import java.io.File;

public class FakeToposort implements Toposort {
  private final External external;

  @Inject
  public FakeToposort(External external) {
    this.external = external;
  }

  @Override
  public void processFile(File file) {
    if (file.getName().equals("small_graph.txt")) {
      external.process(1);
      external.process(2);
    } else if (file.getName().equals("cycle_graph.txt"))
      external.fail();
    else if (file.getName().equals("large_graph.txt"))
      for(int i = 1; i <= 1000; i++)
        external.process(i);
    else
      throw new UnsupportedOperationException("http://i.imgflip.com/112boa.jpg");
  }
}
