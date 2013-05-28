package pocAnsible;

public class Test {

    public static void main (String args[]) {
        Ansible a1 = new AWareHouse().createA("a1").createA("a2").createA("a3").fetRoot();

        EndPoint e1 = new EndPoint("e1");
        e1.listenTo(a1, a1.fetHA("a3"));

        EndPoint e2 = new EndPoint("e2");
        e2.listenTo(a1.fetHA("a3"));

        System.out.println(a1.printHA());

        a1.sendMessage(new Message("Bonjour Ansible A1"));
        a1.sendMessage(new Message("Bonjour Ansible A1"));
        a1.sendMessage(new Message("Bonjour Ansible A1"));

    }
}
