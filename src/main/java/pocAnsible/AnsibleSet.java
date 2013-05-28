package pocAnsible;

import java.util.HashSet;

public class AnsibleSet extends HashSet<Ansible> {

    static final AnsibleSet Null = new AnsibleSet();

    EndPointSet fetHE() {
        EndPointSet res = new EndPointSet();
        for (Ansible ansible : this)
            res.union(ansible.fetHE());
        return res;
    }

    String print(String indent) {
        StringBuilder res = new StringBuilder();
        for (Ansible ansible : this)
            res.append(ansible.print(indent));
        return res.toString();
    }

    public Ansible fetHA(String name) {
        for (Ansible ansible : this) {
            Ansible res = ansible.fetHA(name);
            if (res.isNotNull())
                return res;
        }
        return Ansible.Null;
    }

    public MessageSet fetHM(int numberOfMessages) {
        MessageSet res = new MessageSet();
        for (Ansible ansible : this)
            res.addAll(ansible.fetM(numberOfMessages));
        return res.sortByDate().first(numberOfMessages);
    }
}
