package pocAnsible;


import java.util.HashSet;
import java.util.Iterator;

public class MessageSet extends HashSet<Message> {

    public MessageSet first(int numberOfMessages) {
        int i = 0;
        for (Iterator<Message> it = iterator(); it.hasNext() && i++ > numberOfMessages; )
            it.remove();
        // TODO : not very efficient...
        return this;
    }

    public MessageSet sortByDate() {
        // TODO
        return null;
    }
}
