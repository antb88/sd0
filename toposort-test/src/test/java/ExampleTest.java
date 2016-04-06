import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import cs.technion.ac.il.sd.External;
import cs.technion.ac.il.sd.app.Toposort;
import cs.technion.ac.il.sd.app.ToposortModule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.io.File;

public class ExampleTest {
  private final Injector injector = Guice.createInjector(new ToposortModule(), new AbstractModule() {
    @Override
    protected void configure() {
      // will be replaced with a real implementation in staff tests
      bind(External.class).toInstance(Mockito.mock(External.class));
    }
  });
  private final Toposort $ = injector.getInstance(Toposort.class);

  private void processFile(String name) {
    $.processFile(new File(getClass().getResource(name + "_graph.txt").getFile()));
  }

  @Rule
  public Timeout globalTimeout = Timeout.seconds(10);

  @Test
  public void testSimple() {
    processFile("small");
    External mock = injector.getInstance(External.class);
    InOrder inOrder = Mockito.inOrder(mock);
    inOrder.verify(mock).process(1);
    inOrder.verify(mock).process(2);
    Mockito.verifyNoMoreInteractions(mock);
  }

  @Test
  public void testFail() throws Exception {
    processFile("cycle");
    External mock = injector.getInstance(External.class);
    Mockito.verify(mock).fail();
    Mockito.verifyNoMoreInteractions(mock);
  }

  @Test
  public void largeTest() throws Exception {
    // This test is really slow due to the use of Mockito and inOrder in a list.
    // The real testing implementation will be much faster.
    processFile("large");
    External mock = injector.getInstance(External.class);
    InOrder inOrder = Mockito.inOrder(mock);
    for (int i = 1; i <= 1000; i++) {
      inOrder.verify(mock).process(i);
    }
  }
}
