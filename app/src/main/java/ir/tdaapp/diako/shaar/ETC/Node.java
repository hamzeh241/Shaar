package ir.tdaapp.diako.shaar.ETC;

/**
 * Created by Diako on 6/2/2019.
 */

public class Node {

    public Node(String Val)
    {
        setVal(Val);
    }
    private String Val;
    private Node Next;
    private Node Prev;

    public String getVal() {
        return Val;
    }

    public void setVal(String val) {
        Val = val;
    }

    public Node getNext() {
        return Next;
    }

    public void setNext(Node next) {
        Next = next;
    }

    public Node getPrev() {
        return Prev;
    }

    public void setPrev(Node prev) {
        Prev = prev;
    }
}
