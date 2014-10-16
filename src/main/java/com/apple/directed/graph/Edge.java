package com.apple.directed.graph;

public class Edge {


    private final Node from;
    
    private final Node to;




    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
    }

   
    public Node getTo() {
        return to;
    }

  
    public Node getFrom() {
        return from;
    }




    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((from == null) ? 0 : from.hashCode());
        result = prime * result + ((to == null) ? 0 : to.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Edge other = (Edge) obj;
        if (from == null) {
            if (other.from != null) return false;
        } else if (!(from==other.from)) return false;   
        if (to == null) {
            if (other.to != null) return false;
        } else if (!(to==other.to)) return false;
        return true;
    }


    @Override
    public String toString() {
        return "Edge [from=" + from + ", to=" + to + "]";
    }

}
