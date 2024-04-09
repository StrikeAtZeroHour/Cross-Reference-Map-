
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Segment{
    

    
    public static String[] tokenizer(String javaStmt) {
        String DELIMITER = "\"(?:\\\\\"|[^\"])*?\"|[\\s.,;:+*/|!=><@?#%&(){}\\-\\^\\[\\]\\&&]+";
        String[] tokens = javaStmt.split(DELIMITER); 
        return tokens;
    }
    public static String [] reservedRemover(String [] tokens) {
        String  []  reserved= {"abstract", "default", "package", "synchronized", "this", "const", "assert", "double", "do",
        "if", "private","throws", "throw", "goto", "boolean", "implements", "protected"
        , "true", "break", "else", "import", "public", "transient", "false", "byte",
        "enum", "instanceof", "return", "try", "null", "case", "extends", "interface", "int", "short",
        "void", "catch", "finally", "final", "static", "volatile", "char",
        "long", "strictfp", "while", "class", "float", "native", "super", "continue",
        "for", "new", "switch"};
        String tokens_no_reserved="";
        for(int x=0;x<tokens.length;x++){
            int matched=0;
            for(int y=0;y<reserved.length;y++){//delete the reserved word in tokens
                if (tokens[x].equals(reserved[y])){
                    
                    matched++;
                    break;
                }
                for (int i = 0; i < tokens[x].length(); i++) {//delete the integer in tokens
                    char c = tokens[x].charAt(i);
                    if (c < '0' || c > '9') {
                        break; // Non-numeric character found, it is not a integer

                    }
                    matched++;
                }
            }
            if (matched==0) {
                tokens_no_reserved=tokens_no_reserved+tokens[x]+" ";
            }
        }
        
        return tokens_no_reserved.split(" ");
    }
    
    public static void main(String[] args){
        try{
            String path="Dsample.java";
            File f=new File(path);
            System.out.println("----------------------------------");
            System.out.println("Program created by StirkeAtZeroHour.\n");
            System.out.println("SOURCE FILE:"+path);
            Scanner scan=new Scanner(f);

            Comparator EC=new StringComparator();
            LinkedList ElementContainer = new LinkedList(EC);
            int line=1;
            int count=0;//Total indentifier
            while (scan.hasNextLine()) {
                String nextLine=scan.nextLine();//get the String of next Line 
                String [] tokens=tokenizer(nextLine);//delete the special characters
                String [] tokens_no_reserved=reservedRemover(tokens);//delete the integer and the empty String
                

                for(int x=0;x<tokens_no_reserved.length;x++){//get the token from the array
                    if(tokens_no_reserved[x]!=""){
                        if (ElementContainer.isEmpty()){ 
                            ElementContainer.insertInOrder(tokens_no_reserved[x]);
                            ElementContainer.getHead().child.insertInOrder(Integer.toString(line));//insert ListNode  in the childLinkedList of the head-ListNode.
                            count++;
                        }else {
                            ListNode current=ElementContainer.getHead();
                            while (current!=null) {
                                if (tokens_no_reserved[x].equals(current.data)) {//ListNode is  already created for that token
                                    current.child.insertInOrder(Integer.toString(line));//add line record for the token
                                    count++;
                                    break;
                                }
                                if (current.next==null) {//if it is the final ListNode, it can be confirmed that this is a NEW token 
                                    ElementContainer.insertInOrder(tokens_no_reserved[x]);
                                    ListNode currentx=ElementContainer.getHead();
                                    while (currentx!=null) {//find out the ListNode of the new token from the updated LinkedList, add line record for the token
                                        if (tokens_no_reserved[x].equals(currentx.data)) {
                                            currentx.child.insertInOrder(Integer.toString(line));
                                            count++;
                                            break;
                                        }
                                        currentx=currentx.next;
                                    }
                                    
                                    break;
                                }
                              
                                
                                current=current.next;
                            }
                        }
                        
                    } 

                    
                } 
                
                System.out.println(String.format("%04d", line)+" | "+nextLine);
                line+=1;
            }

            
            ListNode current=ElementContainer.getHead();
            System.out.println();
            System.out.println("Cross Map Reference :-");
            while (current!=null) {
                System.out.println(current.data+": "+current.child.toString());
                current=current.next;
            }
            System.out.println("Total indentifier extracted:"+count);
            scan.close();
            
            
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch(EmptyListException e){
            System.out.println(e.getMessage());
        }
        }
        
        
        
    
}