package com.fa.insito.poc3.framework;

import java.util.List;

/**
 * Simple interface pour gérer une structure arborescente
 */
public interface Node<T> extends Comparable<Node>{

    /**
     * @return <code>true</code> if it's the root of the true, otherwise, false
     */
    boolean isRoot();

    /**
     * @return the parent of the node, or <code>null</code> if root of tree.
     */
    Node getParent();

    /**
     * Returns a list of the node's children.
     *
     * @return a list
     */
    List<Node> getChildren();

}