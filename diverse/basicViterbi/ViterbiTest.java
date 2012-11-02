package basicViterbi;


import hypergraph.HypergraphProto.Hypergraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;



public class ViterbiTest extends BaseTest {

	private Hypergraph h;
	private Viterbi v;

	public ViterbiTest(){ 
		super();
		h = createHypergraph();
		v = new Viterbi(h);
	}
	
	@Test
	public void testInitialize() {
		v.initialize();
		List<Double> actual = new ArrayList<Double>();
		for (int i = 0; i < v.pi.size(); i++)
			actual.add(v.pi.get(i));
		List<Double> expected = Arrays.asList(1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
		assertTrue(actual.equals(expected));
	}

	@Test
	public void testRun() {
		
	}
	
	@Test
	public void testRenderResult() {	
		String expected = "S VP NP V N D    ";
		String actual = (v.renderResult(v.run()));
		assertEquals(expected, actual);
	}
	
	
}
