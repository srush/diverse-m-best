package basicViterbi;

import hypergraph.HypergraphUtils;
import hypergraph.HypergraphProto.Hyperedge;
import hypergraph.HypergraphProto.Hypergraph;
import hypergraph.HypergraphProto.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Viterbi style algorithm to find the path with maximum weight in a hypergraph.
 * The hypergraph is defined in hypergraph.proto
 * 
 * @author swabha
 */
class Viterbi {
	
	private Hypergraph h;
	
	/** Dynamic programming state saving variables */
	List<Double> pi;
	List<Hyperedge> backPointers;
	
	Viterbi(Hypergraph h){
		this.h = h;
		pi = new ArrayList<Double>(h.getVerticesCount());
		backPointers = new ArrayList<Hyperedge>(h.getVerticesCount());
		for (int i = 0; i < h.getVerticesCount(); ++i) {
			backPointers.add(null);
		}
	}
	
	/** Initializes the weight of terminal nodes to 1.0 and the rest of the nodes to 0.0 */
	List<Double> initialize() {
		List<Integer> terminalIds = HypergraphUtils.getTerminals(h);
		for (Vertex v : h.getVerticesList()) {
			pi.add(v.getId() - 1, 0.0);
		}
		for(Integer id: terminalIds) {
			pi.set(id - 1, 1.0);
		}		
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
			for (Hyperedge e : inMap.get(v)) {
				double childProduct = 1.0;
				
				for (Integer i : e.getChildrenIdsList()) {
					childProduct *= pi.get(i-1);
				}
				if (pi.get(v-1) < childProduct * e.getWeight()) {
					pi.set(v-1, childProduct * e.getWeight());
					backPointers.set(v-1, e);
				}
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