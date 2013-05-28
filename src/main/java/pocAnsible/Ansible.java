package pocAnsible;


public class Ansible extends BaseEntity {

    private AWareHouse aWareHouse;

    private String name;
    private Ansible parent;
    private AnsibleSet sons = new AnsibleSet();

    private EndPointSet endPoints = new EndPointSet();

    static final Ansible Null;
    static {
        Null = new Ansible();
        Null.id = nullId;
    }

    Ansible() {}

    Ansible(AWareHouse aWareHouse, Ansible parent, String name) {
        this.aWareHouse = aWareHouse;
        this.name = name;
        this.parent = parent;
        if (parent.isNotNull())
            parent.sons.add(this);
    }

    @Override
    public String toString() {
        return (parent.isNull() ? "" : parent.toString() + ".") + name;
    }

    public Ansible createA(String name) {
        if (fetRoot().fetHA(name).isNotNull())
            throw new IllegalArgumentException("An ansible with name " + name + " already exist");
        return new Ansible(aWareHouse, this, name);
    }

    public void sendMessage(final Message message) {
        System.out.println("sending to @" + name + " : " + message);
        // sent datetime
        message.dateStamp();
        // keepTheMessage
        aWareHouse.keepTheMessage(message);
        // spreadTheMessage
        for (EndPoint endPoint : fetHE())
            endPoint.onMessage(message);
    }

    public void addSubscriber(EndPoint endPoint) {
        endPoints.add(endPoint);
    }

    public void removeSubscriber(EndPoint endPoint) {
        endPoints.remove(endPoint);
    }

    //**********************************************************
    // getters and functors
    //**********************************************************

    public String getName() {
        return name;
    }

    public boolean isNull() {
        return id == nullId;
    }

    public boolean isNotNull() {
        return id != nullId;
    }

    Ansible fetRoot() {
        return parent.isNull() ? this : parent.fetRoot();
    }

    AnsibleSet fetA() {
        return sons;
    }

    Ansible fetHA(String name) {
        return this.name.equals(name) ? this : sons.fetHA(name);
    }

    EndPointSet fetHE() {
        return new EndPointSet(endPoints).union(sons.fetHE());
    }

    MessageSet fetM(int numberOfMessages) {
        return aWareHouse.retrieveLastM(this, numberOfMessages);
    }

    //**********************************************************

    public String printHA() {
        return print("");
    }

    String print(String indent) {
        return new StringBuilder().append(indent).append(name).append(endPoints.print()).append('\n')
                .append(sons.print(indent + " "))
                .toString();
    }
}
