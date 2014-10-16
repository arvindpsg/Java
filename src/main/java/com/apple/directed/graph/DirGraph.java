package com.apple.directed.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;


public class DirGraph {



    // Nodes in this graph
    private List<Node> nodes;

    public DirGraph() {

        nodes = new ArrayList<Node>();;
    }



    /**
     * function that traverses the graph and returns whether or not two people are "connected" and what "level" (or
     * depth) the connection is within the graph. If more than one path then the 'min depth' is chosen among the
     * existing paths.
     * 
     * @param node1 start node
     * @param node2 destination node
     * @return 'minimum depth level' among the existing paths (if more than one path then the min depth is chosen
     *         between 2 nodes), '-1' if no path between nodes, or throws 'IllegalArgumentException' if either one or
     *         more nodes in the input don't even exist.
     */
    public int connected(Node node1, Node node2) {

        if (nodes.contains(node1) == false || nodes.contains(node2) == false) {
            throw new IllegalArgumentException("invalid input");
        }

        node1 = nodes.get(nodes.indexOf(node1));
        node2 = nodes.get(nodes.indexOf(node2));

        return pathDepth(node1, node2, 1, new ArrayList<Node>());


    }


    private int pathDepth(Node source, Node dest, int level, List<Node> visitedNodes) {
        int status = -1;
        Set<Integer> minDistance = new TreeSet<Integer>();
        visitedNodes.add(source);
        Set<Node> children = source.getChildren();
        if (children.contains(dest)) return level;
        level += 1;
        for (Node child : children) {
            if (!visitedNodes.contains(child)) {
                status = pathDepth(child, dest, level, visitedNodes);
                if (status > 0) {
                    minDistance.add(status);
                }
            }

        }

        if (minDistance.isEmpty())
            return -1;
        else
            return (Integer) (minDistance.toArray())[0];

    }


    /**
     * function that traverses the graph and returns a map that consists of all people in the graph
     * 
     * @param node start Node
     * @param depth
     * @return returns a map that consists of all people in the graph as "keys" that map to a list of all associated
     *         connections up to a certain depth.
     */
    public Map<Node, List<Node>> findConnections(Node node, int depth) {

        if (nodes.contains(node) == false) {
            throw new IllegalArgumentException("invalid input");
        }

        node = nodes.get(nodes.indexOf(node));
        Map<Node, List<Node>> results = new HashMap<Node, List<Node>>();
        return findConnections(node, depth, new ArrayList<Node>(), results);

    }


    private Map<Node, List<Node>> findConnections(Node node, int depth, List<Node> visitedNodes,
                                                  Map<Node, List<Node>> results) {

        visitedNodes.add(node);
        results.put(node, findChildren(node, depth));

        Set<Node> children = node.getChildren();
        if (children != null) {
            for (Node child : children) {
                if (visitedNodes.contains(child) == false) {
                    findConnections(child, depth, visitedNodes, results);
                }
            }
        }

        return results;
    }



    private List<Node> findChildren(Node node, int depth) {



        List<Node> result = null;
        List<Node> traversed = new LinkedList<Node>();
        traversed.add(node);

        int count = 0;
        while (!traversed.isEmpty() && (count < depth)) {

            Node tmp = traversed.remove(0);
            count += 1;

            Set<Node> children = tmp.getChildren();

            if (children == null || children.isEmpty()) {

                continue;
            }

            if ((depth - (count + traversed.size())) > 0) {

                depth += children.size() - 1;
            }


            if (result == null) result = new ArrayList<Node>();
            for (Node child : children) {

                if (result.contains(child) == false) {
                    result.add(child);
                    traversed.add(child);
                }
            }


        }



        return result;


    }



    /*
     * Add an edge to the graph, true if the Edge was added, false if from Node already has this Edge
     */
    public boolean add(Node from, Node to) {
        from = add(from);
        to = add(to);  
        return addEdge(from, to);
    }


    private Node add(Node person) {
        if (!nodes.contains(person)) {
            nodes.add(person);
        }
        return nodes.get(nodes.indexOf(person));
    }


    private boolean addEdge(Node from, Node to) {
        if (from.findEdge(to) != null)
            return false;
        else {
            Edge e = new Edge(from, to);
            from.addEdge(e);
            to.addEdge(e);
            return true;
        }
    }



   



    public static void main(String[] args) {

        DirGraph graph = new DirGraph();

        graph.add(new Node("person1"), new Node("person3"));
        graph.add(new Node("person1"), new Node("person4"));
        graph.add(new Node("person2"), new Node("person1"));
        graph.add(new Node("person2"), new Node("person5"));
        graph.add(new Node("person2"), new Node("person7"));
        graph.add(new Node("person5"), new Node("person4"));
        graph.add(new Node("person3"), new Node("person7"));
        graph.add(new Node("person3"), new Node("person5"));
        graph.add(new Node("person3"), new Node("person1"));
        graph.add(new Node("person3"), new Node("person9"));
        graph.add(new Node("person4"), new Node("person8"));
        graph.add(new Node("person4"), new Node("person6"));
        graph.add(new Node("person4"), new Node("person2"));
        graph.add(new Node("person5"), new Node("person7"));
        graph.add(new Node("person5"), new Node("person1"));
        graph.add(new Node("person5"), new Node("person6"));
        graph.add(new Node("person6"), new Node("person9"));
        graph.add(new Node("person6"), new Node("person3"));
        graph.add(new Node("person6"), new Node("person8"));
        graph.add(new Node("person7"), new Node("person1"));
        graph.add(new Node("person7"), new Node("person5"));
        graph.add(new Node("person7"), new Node("person8"));
        graph.add(new Node("person8"), new Node("person9"));
        graph.add(new Node("person8"), new Node("person10"));
        graph.add(new Node("person8"), new Node("person7"));
        graph.add(new Node("person9"), new Node("person7"));
        graph.add(new Node("person9"), new Node("person5"));
        graph.add(new Node("person9"), new Node("person6"));
        graph.add(new Node("person10"), new Node("person7"));
        graph.add(new Node("person10"), new Node("person1"));
        graph.add(new Node("person10"), new Node("person4"));
        graph.add(new Node("person10"), new Node("person3"));


        // checking whether or not two people are "connected" and what "level"
        System.out.println("\r\n " + "\r\n "
                + "graph.connected(new Node(\"person8\"), new Node(\"person9\")) ouput is " + "\r\n ");
        System.out.println(graph.connected(new Node("person8"), new Node("person9")));

        System.out.println("\r\n " + "\r\n "
                + "graph.connected(new Node(\"person7\"), new Node(\"person3\")) ouput is " + "\r\n ");
        System.out.println(graph.connected(new Node("person7"), new Node("person3")));

        System.out.println("\r\n " + "\r\n "
                + "graph.connected(new Node(\"person1\"), new Node(\"person4\")) ouput is " + "\r\n ");
        System.out.println(graph.connected(new Node("person1"), new Node("person4")));

        System.out.println("\r\n " + "\r\n "
                + "graph.connected(new Node(\"person5\"), new Node(\"person7\")) ouput is " + "\r\n ");
        System.out.println(graph.connected(new Node("person5"), new Node("person7")));

        System.out.println("\r\n " + "\r\n "
                + "graph.connected(new Node(\"person4\"), new Node(\"person10\")) ouput is " + "\r\n ");
        System.out.println(graph.connected(new Node("person4"), new Node("person10")));


        /*
         * to Get the people in the graph as "keys" that map to a list of all associated, connections up to a certain
         * depth.
         */
        System.out.println("\r\n " + "\r\n " + "findConnections(new Node('person10'), 2) ouput is " + "\r\n ");
        Map<Node, List<Node>> result = graph.findConnections(new Node("person10"), 2);
        for (Entry<Node, List<Node>> entry : result.entrySet()) {
            System.out.println("Parent is" + entry.getKey());
            System.out.println("Children are" + entry.getValue());
        }

        System.out.println("\r\n " + "\r\n " + "findConnections(new Node('person5'), 3) ouput is " + "\r\n ");

        result = graph.findConnections(new Node("person5"), 3);
        for (Entry<Node, List<Node>> entry : result.entrySet()) {
            System.out.println("Parent is" + entry.getKey());
            System.out.println("Children are" + entry.getValue());
        }


    }



}
