package shortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shortestPath.HypergraphProto.Hyperedge;
import shortestPath.HypergraphProto.Hypergraph;
import shortestPath.HypergraphProto.Vertex;
import junit.framework.TestCase;

public class BaseTest extends TestCase {
	
	public BaseTest() {
		super();
	}
	
	static Hypergraph createHypergraph() {
		List<Vertex> vertices = createVertices();
		List<Hyperedge> edges = createEdges(vertices);
		Hypergraph h = Hypergraph.newBuilder().addAllEdges(edges).addAllVertices(vertices).build();
		return h;
	}
	
	static List<Vertex> createVertices() {
		List<Vertex> vertices = new ArrayList<Vertex>();
		vertices.add(Vertex.newBuilder().setId(1).setName("the").build());
		vertices.add(Vertex.newBuilder().setId(2).setName("man").build());
		vertices.add(Vertex.newBuilder().setId(3).setName("laughed").build());
		vertices.add(Vertex.newBuilder().setId(4).setName("D").build());
		vertices.add(Vertex.newBuilder().setId(5).setName("N").build());
		vertices.add(Vertex.newBuilder().setId(6).setName("V").build());
		vertices.add(Vertex.newBuilder().setId(7).setName("NP").build());
		vertices.add(Vertex.newBuilder().setId(8).setName("VP").build());
		vertices.add(Vertex.newBuilder().setId(9).setName("S").build());
		return vertices;
	}
	
	static List<Hyperedge> createEdges(List<Vertex> vertices) {
		List<Hyperedge> edges = new ArrayList<Hyperedge>();
		edges.add(Hyperedge.newBuilder()
				.setId(1).setParentId(6).addAllChildrenIds(Arrays.asList(1)).setWeight(1.0).build());
		edges.add(Hyperedge.newBuilder()
				.setId(2).setParentId(5).addAllChildrenIds(Arrays.asList(2)).setWeight(1.0).build());
		edges.add(Hyperedge.newBuilder()
				.setId(3).setParentId(4).addAllChildrenIds(Arrays.asList(3)).setWeight(1.0).build());
		edges.add(Hyperedge.newBuilder()
				.setId(4).setParentId(7).addAllChildrenIds(Arrays.asList(5, 6)).setWeight(1.0)
				.build());
		//edges.add(createHyperedge(4, 7, Arrays.asList(5, 6), 1.0));
		edges.add(Hyperedge.newBuilder()
				.setId(5).setParentId(7).addAllChildrenIds(Arrays.asList(5)).setWeight(0.0).build());
		edges.add(Hyperedge.newBuilder()
				.setId(6).setParentId(8).addAllChildrenIds(Arrays.asList(4)).setWeight(1.0).build());
		edges.add(Hyperedge.newBuilder()
				.setId(7).setParentId(8).addAllChildrenIds(Arrays.asList(4, 7)).setWeight(0.0)
				.build());
		edges.add(Hyperedge.newBuilder()
				.setId(8).setParentId(9).addAllChildrenIds(Arrays.asList(7, 8)).setWeight(1.0)
				.build());
		//edges.add(createHyperedge(7, 8, Arrays.asList(4, 7), 0.0));
		//edges.add(createHyperedge(8, 9, Arrays.asList(7, 8), 1.0));
		return edges;
	}
	
	static Hyperedge createHyperedge(int id, int parent, List<Integer> children, double weight) {
		Hyperedge.Builder builder = Hyperedge.newBuilder();
		builder.setId(id).setParentId(parent).setWeight(weight).addAllChildrenIds(children);
		return builder.build();
	}

}
