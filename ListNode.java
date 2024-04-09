
public class ListNode {
    public Object data;   
    public ListNode next;
    public LinkedList child;//storage the position record
    public ListNode(Object data) {
        this.data = data;
        this.next = null;
        Comparator EC=new StringComparator();
        this.child= new LinkedList(EC);
        
        
    }

    public ListNode(Object data, ListNode next) {
        this.data = data;
        this.next = next;
        Comparator EC=new StringComparator();
        this.child= new LinkedList(EC);
    }
}

