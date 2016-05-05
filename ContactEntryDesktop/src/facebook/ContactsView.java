package facebook;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

public class ContactsView {
	Graph<Integer, String> g;

	/** Creates a new instance of ContactsView */
	public ContactsView(HashSet<Contact> contacts) {
		// Graph<V, E> where V is the type of the vertices and E is the type of
		// the edges
		g = new SparseMultigraph<Integer, String>(); // Integer is our node adn
														// String is our edge
		// Add some vertices. From above we defined these to be type Integer.
		Iterator<Contact> it = contacts.iterator();
		for(int i = 0; i < 100 ; i++)
		{
			g.addVertex(it.next().getpID());
		}
		
		// Note that the default is for undirected edges, our Edges are Strings.
	}

	public static void makeContactsGraphView(HashSet<Contact> contacts) {
		ContactsView sgv = new ContactsView(contacts); // We create our graph in here
		// The Layout<V, E> is parameterized by the vertex and edge types
		Layout<Integer, String> layout = new CircleLayout(sgv.g);
		layout.setSize(new Dimension(900, 700)); // sets the initial size of the layout space
		// The BasicVisualizationServer<V,E> is parameterized by the vertex and
		// edge types
		BasicVisualizationServer<Integer, String> vv = new BasicVisualizationServer<Integer, String>(layout);
		vv.setPreferredSize(new Dimension(1000, 800)); // Sets the viewing area
														// size

		// This will set the colors of our nodes based on closeness
		//Sets colors to red or orange
		Transformer<Integer, Paint> vertexPaint = new Transformer<Integer, Paint>() {
			public Paint transform(Integer i) {
				return i > 1 ? Color.RED : Color.ORANGE;
			}
		};
		// This will set node names
		//Sets names
		Transformer<Integer, String> vertTexNames = new Transformer<Integer, String>() {
			public String transform(Integer i) {
				return Parser.getContactName(i);
			}
		};

		// Set up a new stroke Transformer for the edges
		float dash[] = { 10.0f };// CHANGES HOW DASHED IT IS
		final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash,
				0.0f);

		// First number is the thickness
		Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
			public Stroke transform(String s) {
				return edgeStroke;
			}
		};

		// Transforms
		//vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint); // sets
																			// the
																			// color
																			// of
																			// nodes
		// vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
		// //sets the edge stroke
		vv.getRenderContext().setVertexLabelTransformer(vertTexNames);
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		
		//Position.AUTO puts the labels automatically placed
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.AUTO);

		JFrame frame = new JFrame("Contacts");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
	}

}
