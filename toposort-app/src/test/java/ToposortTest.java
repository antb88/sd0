import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import cs.technion.ac.il.sd.External;
import cs.technion.ac.il.sd.app.Toposort;
import cs.technion.ac.il.sd.app.ToposortModule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToposortTest {
  private List<Integer> vertexList = new ArrayList<>();
  private boolean isFail = false;
  private External external = new External() {
    @Override
    public void process(int i) {
      vertexList.add(i);
    }

    @Override
    public void fail() {
      isFail = true;
    }
  };
  private final Injector injector = Guice.createInjector(new ToposortModule(), new AbstractModule() {
    @Override
    protected void configure() {
      // will be replaced with a real implementation in staff tests
      bind(External.class).toInstance(external);
    }
  });
  private final Toposort $ = injector.getInstance(Toposort.class);

  private void processFile(String name) {
    $.processFile(new File(getClass().getResource(name + "_graph.txt").getFile()));
  }

  @Rule
  public Timeout globalTimeout = Timeout.seconds(60);

  @Before
  public void initTest()
  {
    vertexList.clear();
    isFail = false;
  }

  @Test
  public void testSimple() {
    processFile("small");
    Assert.assertTrue(vertexList.equals(Arrays.asList(1, 2)));
    Assert.assertFalse(isFail);
  }

  @Test
  public void testFail() throws Exception {
    processFile("cycle");
    Assert.assertTrue(isFail);
  }

  @Test
  public void largeTest() throws Exception {
    processFile("large");
    List<Integer> res = new ArrayList<>();
    for (int i = 1; i <= 1000; i++) {
      res.add(i);
    }
    Assert.assertTrue(vertexList.equals(res));
    Assert.assertFalse(isFail);
  }
  @Test
  public void largerTest() throws Exception {
    processFile("larger");
    List<Integer> res = new ArrayList<>();
    for (int i = 0; i < 10000; i++) {
      res.add(i);
    }
    Assert.assertEquals(vertexList, res);
    Assert.assertFalse(isFail);
  }
  @Test
  public void emptyFileReturnsEmptySort() throws Exception {
    processFile("empty");
    Assert.assertEquals(vertexList, new ArrayList<Integer>());
    Assert.assertFalse(isFail);
  }

  @Test
  public void emptyFileWithWhiteSpacesReturnsEmptySort() throws Exception {
    processFile("empty_newLines");
    Assert.assertEquals(vertexList, new ArrayList<Integer>());
    Assert.assertFalse(isFail);
  }
  @Test
  public void onlyVertexFileReturnsAnyValidSort() throws Exception {
    processFile("only_vertex");
    for (int i = 1; i < 10; i++) {
      Assert.assertTrue(vertexList.contains(i));
    }
    Assert.assertEquals(vertexList.size(), 10);
    Assert.assertFalse(isFail);
  }
}
