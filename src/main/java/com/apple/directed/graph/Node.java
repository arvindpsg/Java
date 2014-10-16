package com.apple.directed.graph;

import java.util.HashSet;
import java.util.Set;

public class Node {
    
    //name of the person/Node
    private final String name;
    
    //children
    private final Set<Node> children;
    

    //outgoing edges
    private final Set<Edge> outEdges;
    
    //incoming edges
    private final Set<Edge> inEdges;
    
    
    public Node(String n) {
        name = n;
        inEdges = new HashSet<Edge>();
        outEdges = new HashSet<Edge>();
        children =  new HashSet<Node>();
      }
    
    
    
    public void addChild(Node child) {
        Edge out = new Edge(this, child);
        outEdges.add(out);
        children.add(child);
      }

     
      public void addParent(Node parent) {
        Edge out = new Edge(this, parent);
        inEdges.add(out);
      }
      
      
      public boolean addEdge(Edge e) {
          if (e.getFrom() == this)
          {
            outEdges.add(e);
            children.add(e.getTo());
          }
          else if (e.getTo() == this)
          {
            inEdges.add(e);
          }
          else
          {
            return false;
          }
          return true;
      }
      
      public Edge findEdge(Node dest) {
          for (Edge e : outEdges) {
            if (e.getTo() == dest)
              return e;
          }
          return null;
        }
      
      public Edge findEdge(Edge e) {
          if (outEdges.contains(e))
            return e;
          else
            return null;
        }
      
    public boolean hasEdge(Edge e) {
        if (e.getFrom() == this)
            return inEdges.contains(e);
        else if (e.getTo() == this)
            return outEdges.contains(e);
        else
            return false;
    }


    public String getName() {
        return name;
    }



    public Set<Node> getChildren() {
        return children;
    }



    public Set<Edge> getOutEdges() {
        return outEdges;
    }



    public Set<Edge> getInEdges() {
        return inEdges;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Node other = (Node) obj;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        
        return true;
    }



    @Override
    public String toString() {
        return "Node [name=" + name + "]";
    }
    

}