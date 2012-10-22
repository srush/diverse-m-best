package shortestPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shortestPath.HypergraphProto.Hyperedge;
import shortestPath.HypergraphProto.Hypergraph;
import shortestPath.HypergraphProto.Vertex;

public class HypergraphUtils {
	
	/**
	 * Runs in O(|V|+|E|).
	 * @return
	 */
	static Map<Integer, List<Hyperedge>> generateOutgoingMap(Hypergraph h) {
		Map<Integer, List<Hyperedge>> outMap = new HashMap<Integer, List<Hyperedge>>();
		for(Hyperedge e: h.getEdgesList()) {
			
			for (Integer childId : e.getChildrenIdsList()) {
				List<Hyperedge> outgoing;
				if (outMap.containsKey(childId)) {
					outgoing = outMap.get(childId);
				} else {
					outgoing = new ArrayList<Hyperedge>();
				}
				outgoing.add(e);
				outMap.put(childId, outgoing);
			}			
		}
		return outMap;
	}
	
	/** Construct a map that stores the list of incoming hyperedges for a parent vertex */
	static Map<Integer, List<Hyperedge>> generateIncomingMap(Hypergraph h) {
		Map<Integer, List<Hyperedge>> inMap = new HashMap<Integer, List<Hyperedge>>();
		for(Hyperedge e: h.getEdgesList()) {
			List<Hyperedge> incoming;
			if (inMap.containsKey(e.getParentId())) {
				incoming = inMap.get(e.getParentId());
			} else {
				incoming = new ArrayList<Hyperedge>();
			}
			incoming.add(e);
			inMap.put(e.getParentId(), incoming);
		}
		return inMap;
	}

	/** Get the list of ids for the vertices which are not parents for any hyperedge */
	static List<Integer> getTerminals(Hypergraph h) {
		List<Integer> terminals = new ArrayList<Integer>();
		// Assuming all the vertices are terminals
		for (Vertex v : h.getVerticesList()) {
			terminals.add(v.getId());
		}
		
		for (Hyperedge e : h.getEdgesList()) {
			int index = terminals.indexOf(e.getParentId());
			if (index != -1) {
				terminals.remove(index);
			}
		}
		return terminals;
	}
}
