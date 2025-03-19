import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
//My imports
import java.util.Deque;
import java.util.ArrayDeque;


public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    if(vertex == null) {
      return;
    }
    if(vertex.neighbors.isEmpty()) {
      if(vertex.data.length() < k) {
        System.out.println(vertex.data);
      }
      return;
    }

    Deque<Vertex<String>> deq = new ArrayDeque<>();
    Set<Vertex<String>> seen = new HashSet<>();

    deq.addFirst(vertex);
    while(deq.size() > 0) {
      Vertex<String> current = deq.removeFirst();

      if(!seen.contains(current)) {
        seen.add(current);

        if(current.data.length() < k) {
          System.out.println(current.data);
        }

        for(Vertex<String> neighbor : current.neighbors) {
          deq.addFirst(neighbor);
        }
      }
    }

  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    if(vertex == null) {
      return "";
    }
    if(vertex.neighbors.isEmpty()) {
      return vertex.data;
    }

    Deque<Vertex<String>> deq = new ArrayDeque<>();
    Set<Vertex<String>> seen = new HashSet<>();

    int maxLen = 0;
    String ret = "";

    deq.addFirst(vertex);
    while(deq.size() > 0) {
      Vertex<String> current = deq.removeFirst();

      if(!seen.contains(current)) {
        seen.add(current);

        if(current.data.length() > maxLen) {
          maxLen = current.data.length();
          ret = current.data;
        }

        for(Vertex<String> neighbor : current.neighbors) {
          deq.addFirst(neighbor);
        }
      }
    }

    return ret;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    if(vertex == null) {
      return;
    }
    if(vertex.neighbors.isEmpty()) {
      return;
    }

    Deque<Vertex<T>> deq = new ArrayDeque<>();
    Set<Vertex<T>> seen = new HashSet<>();

    deq.addFirst(vertex);
    while(deq.size() > 0) {
      Vertex<T> current = deq.removeFirst();

      if(!seen.contains(current)) {
        seen.add(current);

        if(current.neighbors.contains(current)) {
          System.out.println(current.data);
        }

        for(Vertex<T> neighbor : current.neighbors) {
          deq.addFirst(neighbor);
        }
      }
    }

  }

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    if(start == destination) {
      //Seriously iffy here; I don't like comparisons that aren't .equals()
      return true;
    }
    if(start.getOutboundFlights().isEmpty()) {
      return false;
    }

    Deque<Airport> deq = new ArrayDeque<>();
    Set<Airport> seen = new HashSet<>();

    deq.addFirst(start);
    while(deq.size() > 0) {
      Airport current = deq.removeFirst();

      if(current == destination) {
        return true;
      }

      List<Airport> outbounds = current.getOutboundFlights();

      if(!seen.contains(current)) {
        seen.add(current);
        for(Airport neighbor : outbounds) {
          deq.addFirst(neighbor);
        }
      }
    }
    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {

    Set<T> possible = graph.keySet();
    if(!possible.contains(starting)) {
      return possible;
    }
    if(graph.get(starting).isEmpty()) {
      return possible;
    }

    Set<T> seen = new HashSet<>();
    Deque<T> deq = new ArrayDeque<>();

    deq.add(starting);
    while(deq.size() > 0) {
      T current = deq.removeFirst();
      if(!seen.contains(current)) {
        seen.add(current);
        for(T elem : graph.get(current)) {
          deq.addFirst(elem);
        }
      }
    }
    //possible intersection complement seen. 
    possible.removeAll(seen);
    return possible;
  }
}
