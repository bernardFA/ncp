package domain;


import com.google.common.collect.Sets;

public class EndPoint extends BaseEntity {

    private String name;
    private AnsibleSet ansibles = new AnsibleSet();

    public EndPoint(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void listenTo(Ansible... ansiblesToListen) {
        ansibles.addAll(Sets.newHashSet(ansiblesToListen));
        for(Ansible ansible : ansiblesToListen)
            ansible.addSubscriber(this);
    }

    public void stopListeningTo(Ansible... ansiblesToStopListening) {
        ansibles.removeAll(Sets.newHashSet(ansiblesToStopListening));
        for (Ansible ansible : ansiblesToStopListening)
            ansible.removeSubscriber(this);
    }

    public void onMessage(Message message) {
          System.out.println(name + " : " + message);
    }

    public MessageSet pullMessages(int numberOfMessages) {
        return ansibles.fetHM(numberOfMessages);
    }
}
