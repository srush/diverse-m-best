package shortestPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shortestPath.HypergraphProto.Hyperedge;
import shortestPath.HypergraphProto.Hypergraph;
import shortestPath.HypergraphProto.Vertex;

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
	}
	
	/** Initializes the weight of terminal nodes to 1.0 and the rest of the nodes to 0.0 */
	List<Double> initialize() {
		List<Integer> terminalIds = HypergraphUtils.getTerminals(h);
		for (Vertex v : h.getVerticesList()) {
			pi.add(v.getId() - 1, 0.0);
		}
		for(Integer id: terminalIds) {
			pi.add(id - 1, 1.0);
		}
		
		return pi;
	}
	
	/**
	 * Run CKY and get a list of vertex ids which result in the highest probability structure
	 */
	List<Hyperedge> run() {
		Map<Integer, List<Hyperedge>> inMap = HypergraphUtils.generateIncomingMap(h);
		List<Integer> vertices = toposort(HypergraphUtils.getTerminals(h), h);
		initialize();
		for (Integer v: vertices) {
			for (Hyperedge e : inMap.get(v)) {
				double childProduct = 1.0;
				//List<Hyperedge> bestEdges;
				for (Integer i : e.getChildrenIdsList()) {
					childProduct *= pi.get(i);
				}
				
				if (pi.get(v) < childProduct * e.getWeight()) {
					pi.add(v, childProduct * e.getWeight());
					/*if (backPointers.get(v) != null) {
						bestEdges = backPointers.get(v);
					} else {
						bestEdges = new ArrayList<Hyperedge>();
					}
					bestEdges.add(e);
					backPointers.add(v, bestEdges);*/
					backPointers.add(v, e);
				}
			}
		}
		return backPointers;//.get(vertices.get(vertices.size() - 1));
	}
	
	/** Displays the 1-best parse */
	void renderResult(List<Hyperedge> edges) {
		Hyperedge top = edges.get(h.getVerticesCount()-1);
		while (top != null) {
			System.out.println(h.getVertices(top.getParentId()).getName());
		}
	}
	
	/**
	 * Topologically sorts all vertices given a list of terminal vertices
	 * Runs in O(|V|+|E|).
	 */
	List<Integer> toposort(List<Integer> terminals, Hypergraph h) {
		List<Integer> sorted = new ArrayList<Integer>();
		Map<Integer, Boolean> visited = new HashMap<Integer, Boolean>();
		Map<Integer, List<Hyperedge>> outMap = HypergraphUtils.generateOutgoingMap(h);
		for(Vertex v : h.getVerticesList()) {
			visited.put(v.getId(), false);
		}
		
		for (Integer v : terminals) {
			visit(v, sorted, visited, outMap);
		}
		return sorted;
	}
	
	/** Hypergraph topological sort recursion. Runs in O(|V|+|E|). */
	void visit(
			Integer vertexId,
			List<Integer> sorted,
			Map<Integer, Boolean> visited, 
			Map<Integer, List<Hyperedge>> outMap) {
		if (!visited.get(vertexId)) {
			visited.put(vertexId, true);
			sorted.add(vertexId);
			// get children of hyperedge that has vertexId as parent
			for (Hyperedge e : outMap.get(vertexId)) {
				boolean shouldVisitParent = true;
				for (Integer v : e.getChildrenIdsList()) {
					if (!visited.get(v)){
						shouldVisitParent = false;
						break;
					}
					if (shouldVisitParent) {
						visit(e.getParentId(), sorted, visited, outMap);
					}
				}
			}
		}
	}
	
}
