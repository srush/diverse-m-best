package semiring;

import hypergraph.HypergraphUtils;
import hypergraph.HypergraphProto.Hyperedge;
import hypergraph.HypergraphProto.Hypergraph;
import hypergraph.HypergraphProto.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





public class OneBestSemiringViterbi {

private Hypergraph h;
	
	/** Dynamic programming state saving variables */
		
	List<Hyperedge> backPointers;
	OneBestSemiring vs;
	
	OneBestSemiringViterbi(Hypergraph h) {
		this.h = h;
		backPointers = new ArrayList<Hyperedge>(h.getVerticesCount());
		vs = new OneBestSemiring();
	}
	
	/** Initializes the weight of terminal nodes to 1.0 and the rest of the nodes to 0.0 */
	List<Double> initialize() {
		List<Integer> terminalIds = HypergraphUtils.getTerminals(h);
		List<Double> pi = new ArrayList<Double>();
		for (Vertex v : h.getVerticesList()) {
			pi.add(v.getId() - 1, 0.0);
		}
		for(Integer id: terminalIds) {
			pi.set(id - 1, 1.0);
		}
		vs.setElements(pi);
		return pi;
	}
	
	/**
	 * Run CKY and get a list of vertex ids which result in the highest probability structure
	 */
	List<Hyperedge> run() {
		Map<Integer, List<Hyperedge>> inMap = HypergraphUtils.generateIncomingMap(h);
		List<Integer> vertices = HypergraphUtils.toposort(h);
		initialize();
		for (Integer v: vertices) {
			List<Double> elements = vs.getElements();
			for (Hyperedge e : inMap.get(v)) {
				
				List<Double> toBeMultiplied = new ArrayList<Double>();
				toBeMultiplied.add(e.getWeight());
				for (Integer i : e.getChildrenIdsList()) {
					
					toBeMultiplied.add(elements.get(i-1));
				}
				
				double product = vs.multiply(toBeMultiplied);
				List<Double> toBeAdded = Arrays.asList(elements.get(v-1), product);
				elements.set(v-1, vs.add(toBeAdded));
				if (vs.argmax(toBeAdded) != toBeAdded.size() - 1)
					backPointers.add(v-1, e);
			}
		}
		return backPointers;
	}
	
	/** Displays the 1-best parse */
	String renderResult(List<Hyperedge> edges) {
		StringBuilder builder = new StringBuilder();
		Map<Integer, String> names = new HashMap<Integer, String>();
		for (Vertex v : h.getVerticesList()) {
			names.put(v.getId(), v.getName());
		}
		Collections.reverse(edges);
		for (Hyperedge e : edges) {
			if (e != null)
				builder.append(names.get(e.getParentId()));
				builder.append(" ");
		}
		return builder.toString();
	}
}
