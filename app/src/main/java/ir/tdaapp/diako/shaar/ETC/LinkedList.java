package ir.tdaapp.diako.shaar.ETC;

/**
 * Created by Diako on 6/2/2019.
 */

public class LinkedList {

    public static class My_Linked {

        static Node First;
        static int Count=0;
        static int Exit=2;

        public static void SetExit(int ex){
            Exit=ex;
        }

        public static int GetExit(){
            return Exit;
        }

        public static void AddLast(String Val) {
            if (First == null) {
                Node n = new Node(Val);
                First = n;
                First.setNext(null);
                First.setPrev(null);
            } else {
                Node n = new Node(Val);
                Node nxt = First;
                while (nxt.getNext() != null) {
                    nxt = nxt.getNext();
                }
                nxt.setNext(n);
                n.setPrev(nxt);
            }
            Count++;
        }

        public static String RemoveLast() {
            if (IsEmpty()) {
                return "Empty";
            }
            Node nxt = First;
            while (nxt.getNext() != null) {
                nxt = nxt.getNext();
            }
            if (Count==1){
                First=null;
                Count--;
                return "Fragment_Home";
            }
            Node n = nxt.getPrev();
            n.setNext(null);
            nxt.setPrev(null);
            Count--;
            return n.getVal();
        }
        public static boolean IsEmpty(){
            return Count == 0;
        }
    }

}
