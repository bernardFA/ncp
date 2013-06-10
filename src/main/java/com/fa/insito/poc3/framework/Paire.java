package com.fa.insito.poc3.framework;

public class Paire<L, R> {

    private final L left;
    private final R right;

    public Paire(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        if (left == null || right == null) {
            return 0;
        }
        return left.hashCode() ^ right.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Paire)) return false;
        Paire pairo = (Paire) o;
        return this.left.equals(pairo.getLeft()) &&
                this.right.equals(pairo.getRight());
    }
    
    @Override
    public String toString() {
        return "L : " + left + " R : " + right;
    }

}

