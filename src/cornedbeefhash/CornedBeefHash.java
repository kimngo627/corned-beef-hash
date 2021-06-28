/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cornedbeefhash;

import java.util.Random;

/**
 *
 * @author kimngo
 */
class HBST<Key, Value> {
    
    private class PairNode
    {
        private Key key;
        private Value value;
        private PairNode next;
        public PairNode(Key key, Value value, PairNode next)
        {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    
    private class TreeNode
    {
        private int hash;
        private PairNode pairs;
        private TreeNode left;
        private TreeNode right;
        public TreeNode(int hash, PairNode pairs, TreeNode left, TreeNode right)
        {
            this.hash = hash;
            this.pairs = pairs;
            this.left = left;
            this.right = right;
        }
        
    }
    
    private Random generator;
    private TreeNode root;
    
    
    /*
    Constructor. Initialize a new empty HBST.
    Set generator to an instance of Random.
    Set root to an instance of TreeNode.
    Hint: the hash slot of root must be an int that cannot appear in the 
    hash slot of any other TreeNode.
    */
    public HBST()
    {
        generator = new Random();
        root = new TreeNode(-1, null, null, null);
    }
       
    /*
    Return the Value that is associated with key,
    as described in the previous section. If no Value is associated with key,
    then throw an IllegalArgumentException. Note that key may be null, 
    and the returned Value may also be null. 
    Hint: remember to skip the head node at root.
    */
    public Value get(Key key)
    {
        TreeNode subtree = root;
        int hash = hash(key);
        while (subtree != null)
        {
            if (subtree.hash>hash)
            {
                subtree=subtree.left;
            }
            else if (subtree.hash<hash)
            {
                subtree=subtree.right;
            }
            else
            {
                PairNode current = subtree.pairs;
                while(!isEqual(current.key, key))
                {
                    current = current.next;
                }
                return current.value;
            }
        }
        throw new IllegalArgumentException();   
    }
        
    /*
    Return the height of this HBST. 
    Hint: remember to skip the head node at root.
    */
  public int height()
  {
    return heighting(root.right);
  }

  private int heighting(TreeNode subtree)
  {
    if (subtree==null)
    {
      return 0;
    }
    else
    {
        int left = heighting(subtree.left)+1;
        int right = heighting(subtree.right)+1;
        if(left>right)
        {
            return left;
        }
        else
        {
            return right;
        }
    }
  }
        
        
    /*
    If key is null, then return 0.
    Otherwise, use generator to help compute the hash index of key, 
    as described in the previous section, and return that hash index. 
    Hint: return an int that is guaranteed to be different from the one in 
    the head node root.
    */
    private int hash(Key key)
    {
        if(key==null)
        {
            return 0;
        }
        else
        {
            generator.setSeed(key.hashCode());
            int hash = generator.nextInt();
            return Math.abs(hash);
        }
    }
        
        
    /*
    If the HBST is empty, then return true, otherwise return false. 
    You must do this without using an if, in exactly one line of code. 
    Hint: remember to skip the head node at root.
    */
    public boolean isEmpty()
    {
        return root.right==null;
    }
        
        
    /*
    If left and right are equal, then return true, otherwise return false. 
    You will need this as a ‘‘helper’’ for the methods get and put. 
    Note that left may be null, right may be null, or both may be null. 
    It’s worth 0 points because you’ve already seen code for it in the 
    lectures, and in at least one lab.
    */
    private boolean isEqual(Key left, Key right)
    {
        if (left==null||right==null)
        {
            return left==right;
        }
        else
        {
            return left.equals(right);
        }
    }
        
        
    /*
    Associate key with value, as discussed in the previous section. 
    Note that key may be null, or value may be null, or both may be null. 
    Hint: you must use the head node at root to avoid a special case when 
    adding the first TreeNode to the HBST.
    */
    public void put(Key key, Value value)
    {
        TreeNode subtree = root;
        PairNode add = new PairNode(key, value, null);
        int hash = hash(key);
        while(true)
         {
             if(hash<subtree.hash)
             {
                 if(subtree.left==null)
                 {
                     subtree.left = new TreeNode(hash, add, null, null);
                     return;
                 }
                 else
                 {
                     subtree = subtree.left;
                 }
             }
             else if (hash>subtree.hash)
             {
                 if (subtree.right==null)
                 {
                     subtree.right = new TreeNode(hash, add, null, null);
                     return;
                 }
                 else
                 {
                     subtree = subtree.right;
                 }
             }
             else
             {
                 PairNode current = subtree.pairs;
                 while(current.next != null)
                 {
                     current = current.next;
                 }
                 current.next = add;
                 return;
             }
         }
    }
    
}

//Driver Class
class CornedBeefHash
{ 
  private final static String[] reserved = 
   { "abstract",     "assert",       "boolean",     "break", 
     "byte",         "case",         "catch",       "char", 
     "class",        "const",        "continue",    "default", 
     "do",           "double",       "else",        "extends", 
     "false",        "final",        "finally",     "float", 
     "for",          "goto",         "if",          "implements", 
     "import",       "instanceof",   "int",         "interface", 
     "long",         "native",       "new",         "null", 
     "package",      "private",      "protected",   "public", 
     "return",       "short",        "static",      "super", 
     "switch",       "synchronized", "this",        "throw", 
     "throws",       "transient",    "true",        "try", 
     "var",          "void",         "volatile",    "while" }; 
 
  public static void main(String [] args) 
  { 
    HBST<Integer, Integer> intToInt = new HBST<Integer, Integer>(); 
    for (int key = -10; key <= 10; key += 1) 
    { 
      intToInt.put(key, key * key); 
    } 
    System.out.println(intToInt.height()); 
    for (int key = -10; key <= 10; key += 1) 
    { 
      System.out.format("%3d %3d", key, intToInt.get(key)); 
      System.out.println(); 
    } 
 
    System.out.println(); 
 
    HBST<String, Integer> stringToInt = new HBST<String, Integer>(); 
    for (int index = 0; index < reserved.length; index += 1) 
    { 
      stringToInt.put(reserved[index], index); 
    } 
    System.out.println(stringToInt.height()); 
    for (int index = 0; index < reserved.length; index += 1) 
    { 
      String name = reserved[index]; 
      System.out.format("%02d %s", stringToInt.get(name), name); 
      System.out.println(); 
    } 
  } 
}

/*
Output:
8
-10 100
 -9  81
 -8  64
 -7  49
 -6  36
 -5  25
 -4  16
 -3   9
 -2   4
 -1   1
  0   0
  1   1
  2   4
  3   9
  4  16
  5  25
  6  36
  7  49
  8  64
  9  81
 10 100

11
00 abstract
01 assert
02 boolean
03 break
04 byte
05 case
06 catch
07 char
08 class
09 const
10 continue
11 default
12 do
13 double
14 else
15 extends
16 false
17 final
18 finally
19 float
20 for
21 goto
22 if
23 implements
24 import
25 instanceof
26 int
27 interface
28 long
29 native
30 new
31 null
32 package
33 private
34 protected
35 public
36 return
37 short
38 static
39 super
40 switch
41 synchronized
42 this
43 throw
44 throws
45 transient
46 true
47 try
48 var
49 void
50 volatile
51 while
*/
