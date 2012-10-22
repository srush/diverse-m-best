package shortestPath;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import shortestPath.HypergraphProto.Hyperedge;
import shortestPath.HypergraphProto.Hypergraph;

public class HypergraphUtilsTest extends BaseTest {
	
	private Hypergraph h;

	public HypergraphUtilsTest(){
		super();
		h = createHypergraph();
	}
	
	@Test
	public void testGetTerminals() {
		System.out.println(HypergraphUtils.getTerminals(h));
	}

	@Test
	public void testGenerateIncomingMap() {
		Map<Integer, List<Hyperedge>> incomingMap = HypergraphUtils.generateIncomingMap(h);
		List<Hyperedge> edges = incomingMap.get(5);
		for (Hyperedge e :edges) {
			System.out.println(e.getId());
		}
	}
	
	@Test
	public void testGenerateOutgoingMap() {
		Map<Integer, List<Hyperedge>> outgoingMap = HypergraphUtils.generateOutgoingMap(h);
		List<Hyperedge> edges = outgoingMap.get(9);
		for (Hyperedge e :edges) {
			System.out.println(e.getId());
		}
	}
}
