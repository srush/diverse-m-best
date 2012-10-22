package shortestPath;


import org.junit.Test;

import shortestPath.HypergraphProto.Hypergraph;

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
		for (int i = 0; i < v.pi.size(); i++)
		System.out.println(i + " : " + v.pi.get(i));
	}

	public void testRun() {
		v.renderResult(v.run());
	}
}
