package com.fa.insito.poc3.framework;

import static com.financeactive.fxent.framework.Walker.ProceedOption.CONTINUE;

/**
 *
 */
public class ChainedEvent<T extends ChainedEvent>
            implements Traversable<T>{

    T previous;

    T next;

    public void append(T next){
        if (this.next != null){
            this.next.previous = next;
        }
        next.previous = this;
        this.next = next;
    }

    public void prepend(T previous){
        if (this.previous != null){
            this.previous.next = previous;
        }
        previous.next = this;
        this.previous = previous;
    }

    public boolean isLast(){
        return next == null;
    }

    public boolean isFirst(){
        return previous == null;
    }

    public T previous() {
        return previous;
    }

    public T next() {
        return next;
    }

    public T last(){
        if (isLast()) return (T)this;
        return (T) next().last();
    }

    public T first(){
        if (isFirst()) return (T) this;
        return (T) previous().first();
    }

    public int getIndex(){
        int idx = 0;
        ChainedEvent current = first();
        while (!current.equals(this)){
            idx += 1;
            current = current.next();
        }
        return idx;
    }

    public boolean isBefore(ChainedEvent event){
        ChainedEvent current = this;
        while (!current.isLast()){
            if (current.next().equals(event)){
                return true;
            }
        }
        return false;
    }

    public boolean isAfter(ChainedEvent event){
        ChainedEvent current = this;
        while (!current.isFirst()){
            if (current.previous().equals(event)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void traverser(Walker<T> walker) {
        T current = (T) this;
        while (current != null){
            Walker.ProceedOption go = walker.walkThrough(current);
            current = go == CONTINUE ? (T) current.next() : null;
        }
    }
}
