package hypergraph;


import hypergraph.HypergraphProto.Hyperedge;
import hypergraph.HypergraphProto.Hypergraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import basicViterbi.BaseTest;



public class HypergraphUtilsTest extends BaseTest {
	
	private Hypergraph h;

	public HypergraphUtilsTest(){
		super();
		h = createHypergraph();
	}
	
	@Test
	public void testGetTerminals() {
		// Elements need to be in order
		List<Integer> expected = Arrays.asList(1, 2, 3);
		List<Integer> actual = HypergraphUtils.getTerminals(h);
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testGenerateIncomingMap() {
		Map<Integer, List<Hyperedge>> actualMap = HypergraphUtils.generateIncomingMap(h);
		List<Integer> actual = new ArrayList<Integer>();
		for (Hyperedge e :actualMap.get(8)) {
			actual.add(e.getId());
		}
		List<Integer> expected = Arrays.asList(6, 7);
		assertTrue(actual.equals(expected));
	}

	@Test
	public void testGenerateIncomingMap_SourceVertices() {
		Map<Integer, List<Hyperedge>> actualMap = HypergraphUtils.generateIncomingMap(h);
		List<Integer> actual = new ArrayList<Integer>();
		for (Hyperedge e :actualMap.get(3)) {
			actual.add(e.getId());
		}
		List<Integer> expected = new ArrayList<Integer>();
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testGenerateOutgoingMap() {
		Map<Integer, List<Hyperedge>> actualMap = HypergraphUtils.generateOutgoingMap(h);
		List<Integer> actual = new ArrayList<Integer>();
		for (Hyperedge e :actualMap.get(7)) {
			actual.add(e.getId());
		}
		List<Integer> expected = Arrays.asList(7, 8);
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testGenerateOutgoingMap_TargetVertex() {
		Map<Integer, List<Hyperedge>> outgoingMap = HypergraphUtils.generateOutgoingMap(h);
		List<Integer> actual = new ArrayList<Integer>();
		for (Hyperedge e :outgoingMap.get(9)) {
			actual.add(e.getId());
		}
		List<Integer> expected = new ArrayList<Integer>();
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testToposort() {
		List<Integer> actual = HypergraphUtils.toposort(h);
		List<Integer> expected = Arrays.asList(1, 6, 2, 5, 7, 3, 4, 8, 9);
		assertTrue(actual.equals(expected));
	}
}
