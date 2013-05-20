package domain;


import java.util.HashSet;
import java.util.Iterator;

public class EndPointSet extends HashSet<EndPoint> {

    public EndPointSet union(EndPointSet endPointSet) {
        for (EndPoint endPoint : endPointSet)
            add(endPoint);
        return this;
    }

    public String print() {
        String res = "[";
        for (Iterator<EndPoint> it = this.iterator() ; it.hasNext(); ) {
            EndPoint endPoint = it.next();
            res += endPoint.getName();
            if (it.hasNext())
                res += ",";
        }
        return res + "]";
    }
}