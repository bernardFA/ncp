package domain;


public class AWareHouse {

    public Ansible createA(String name) {
        return new Ansible(this, Ansible.Null, name);
    }

    public void keepTheMessage(Message message) {
        // TODO
    }

    //**********************************************************
    // getters and functors
    //**********************************************************

    AnsibleSet fetA() {
        // TODO
        return AnsibleSet.Null;
    }

    Ansible fetA(String name) {
        // TODO
        return Ansible.Null;
    }

    public MessageSet retrieveLastM(Ansible ansible, int numberOfMessages) {
        // TODO
        return null;
    }
}
