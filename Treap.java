package CS284;

import java.util.Random;
import java.util.Stack;

/*
 * Name: Ayush Misra
 * Pledge: "I pledge my honor that I have abided by the Stevens Honor System." AM
 * Course: CS284E Data Structures
 * A treap is a binary search tree (BST) which additionally maintains heap priorities. An
 *	example is given in Figure 1. A node consists of
 *	• 	A key k (given by the letter in the example),
 *	• 	A random heap priority p (given by the number in the example). The heap priority p
 *		is assigned at random upon insertion of a node. It should be unique in the treap.
 *	• 	A pointer to the left child and to the right child node.
 * This code implements a BST while maintaining heap priorities. 
 */


//Class Treap which uses the comparable method so that we can compare tree nodes more efficiently.
public class Treap<E extends Comparable<E>> {
	//The inner Node class that makes up the BST.
	private static class Node<E> {
		//Data Fields
		public E data;
		public int priority;
		public Node<E> left;
		public Node<E> right;
		
		//Constructor
		public Node(E data, int priority) {
			if (data == null) 
				throw new IllegalArgumentException("Data cannot be null");
			this.data = data;
			this.priority = priority;
			this.left = null;
			this.right = null;
		}
		
		//Methods
		/*
		 * Performs a right rotation returning a reference to the root 
		 * of the result.  
		 */
		Node<E> rotateRight(){
			Node<E> temp = this.left;
			Node<E> temp2 = this.left.right;
			
			temp.right = this;		
			this.left = temp2;
			return temp;
		}
		/*
		 * Performs a left rotation returning a reference to the root 
		 * of the result. 
		 */
		Node<E> rotateLeft(){
			Node<E> temp = this.right.left;
			Node<E> temp2 = this.right;
			
			temp2.left = this;			
			this.right = temp;			
			return temp2;
		}
		
		
	}
	
	//Data Field of Treap Class
	private Random priorityGenerator;
	private Node<E> root;
	
	//Constructors
	public Treap() {
		priorityGenerator = new Random();
		this.root = null;
	}
	
	public Treap(long seed) {
		priorityGenerator = new Random(seed);
		this.root = null;
	}
	
	
	
	
	//Methods
	/*
	 * This method returns true, if a node with the key was successfully 
	 * added to the treap. If a node is already containing the given 
	 * key, the method returns false and does not modify the treap.
	 */
	public boolean add(E key) {
		return add(key, this.priorityGenerator.nextInt(1000)); //Just some random Integer
	}
	
	
	/*
	 * This method will add a new node to the treap. However, if a node 
	 * with the same key exists, we cannot add a duplicate, and return false.
	 * If not, we can add the node and return true.
	 */
	public boolean add(E key, int priority) {
		Stack<Node<E>> nodeStack = new Stack<Node<E>>(); //We want a stack to remember the path we took.
		if (this.root == null) { //If there is no tree to begin with, we can make a new root with this data.
			this.root = new Node<E>(key, priority);
			return true;
		} 
		if (find(key)) { //If a node with the key already exists, we cannot at it.
			return false;
		}
	
		boolean finished = false; //Make a continuous loop until we reach the spot we are trying to add at
		Node<E> currentNode = this.root; //We will start at the root and traverse our way down.
		
		while (!finished) {
			if (currentNode == null) { //We have successfully found the spot we will add the node at.
				break;
			}
			if(key.compareTo(currentNode.data) > 0) {
				//data is greater than current, continue right of node
				nodeStack.push(currentNode);
				currentNode = currentNode.right;
			}
			else {
				//data is less than current, continue left of node
				nodeStack.push(currentNode);
				currentNode = currentNode.left;
			}
			
		}
		
		Node<E> parentNode = nodeStack.peek(); //This would be the parent node we are going to add.
		Node<E> newNode = new Node<E>(key, priority); //This is the node to be added.
		
		
		if (key.compareTo(parentNode.data) < 0) {
			parentNode.left = newNode;
		}
		else {
			parentNode.right = newNode;
		}
		
		reheap(nodeStack, newNode);
		return true;
		
	}
	
	/*
	 * This is a crucial method, not a part of the assignment, but 
	 * is needed to maintain heap priorities. In essence, the 
	 * maintainHeap method ensures that after inserting a 
	 * new node, the tree remains a valid Treap by performing rotations 
	 * to maintain the heap property, where nodes with higher priorities 
	 * are positioned higher in the tree. This process is crucial for 
	 * self-balancing the tree and preventing it from becoming skewed 
	 * or unbalanced.
	 */
	public void reheap(Stack<Node<E>> stack, Node<E> newNode) {
		if (stack.isEmpty()) {
			return;
		}
		while (stack.peek().priority < newNode.priority) {
			if (stack.peek().left == newNode) {
				Node<E> parentNode = stack.peek();
				if (stack.size() == 1) {
					this.root = parentNode.rotateRight();
					break;
				} else {
					stack.pop();
					if (stack.peek().left == parentNode) {
						stack.peek().left = parentNode.rotateRight();
						
					}
					else {
						stack.peek().right = parentNode.rotateRight();
					}
					
				}
			}
			else{
				Node<E> parentNode = stack.peek();
				if (stack.size() == 1) {
					this.root = parentNode.rotateLeft();
					break;
				} else {
					stack.pop();
					if (stack.peek().left == parentNode) {
						stack.peek().left = parentNode.rotateLeft();
						
					} else {
						stack.peek().right = parentNode.rotateLeft();
					}
				}
			}
		}
		return;
	}
	
	
	
	
	/*
	 * Deletes a node with specified key from the BST Heap.
	 */
	boolean delete(E key) {
		if(!find(key)) {
			return false;
		}
		
		Node<E> parentNode = null;
		Node<E> parentTEMP = null;
		Node<E> currentNode = root;
		boolean left = false;
			
		while(currentNode.data != key) {
			if(find(currentNode.left, key)) {
				parentNode = currentNode;
				currentNode = currentNode.left;
				left = true;
			}
			else {
				parentNode = currentNode;
				currentNode = currentNode.right;
				left = false;
			}
		}
		
		if (currentNode == root) {
			if (currentNode.left == null && currentNode.right == null) {
				
			}
			 else if (currentNode.left==null) {
				parentTEMP = currentNode.right;
	            root = currentNode.rotateLeft();
	            parentNode = parentTEMP;
	            left = true;
	        } 
			 else if (currentNode.right==null) {
	            parentTEMP = currentNode.left;
	            root = currentNode.rotateRight();
	            parentNode = parentTEMP;
	            left = false;
	        } 
			 else {
	            if (currentNode.left.priority<currentNode.right.priority) {
	                parentTEMP = currentNode.right;
	                root = currentNode.rotateLeft();
	                parentNode = parentTEMP;
	                left = true;
	            } else {
	                parentTEMP = currentNode.left;
	                root = currentNode.rotateRight();
	                parentNode = parentTEMP;
	                left = false;
	            }
	        }
		}
		
		while (!(currentNode.left == null && currentNode.right == null)) {
			
			if (currentNode.left == null) {
				parentTEMP = currentNode.right;
				if (left) {
					parentNode.left = currentNode.rotateLeft();
				}
				else {
					parentNode.right = currentNode.rotateLeft();
				}
					parentNode = parentTEMP;
				left = true;
			}
			else if (currentNode.right == null) {
				parentTEMP = currentNode.left;
				if (left) {
					parentNode.left = currentNode.rotateRight();
				}
				else {
					parentNode.right = currentNode.rotateRight();
				}
				parentNode = parentTEMP;
				left = false;
			}
			else {
				if (currentNode.left.priority < currentNode.right.priority) {
					parentTEMP = currentNode.right;
					if (left) {
						parentNode.left = currentNode.rotateLeft();
					}
					else {
						parentNode.right = currentNode.rotateLeft();
					}
					parentNode = parentTEMP;
					left = true;
				}
				else {
					parentTEMP = currentNode.left;
					if (left) {
						parentNode.left = currentNode.rotateRight();
					}
					else {
						parentNode.right = currentNode.rotateRight();
					}
					parentNode = parentTEMP;
					left = false;
				}
			}
		}
		currentNode = null;
		
		if (parentNode != null && left) {
			parentNode.left = null;
		}
		else if (parentNode != null){
			parentNode.right = null;
		}
		else {
			this.root = null;
		}
		return true;
	}
	
	
	/*
	 * This method will return true if there exists a node in 
	 * the BST with the same key. False otherwise.
	 */
	boolean find(E key) {
		return find(this.root, key);
	}
	
	/*
	 * This method will return true if there exists a 
	 * node in the BST with the same key starting from a 
	 * specified root. This uses and OR method with recursion.
	 * False otherwise.
	 */
	private boolean find(Node<E> root, E key) {
		if (root == null) {
			return false;
		}
		if (root.data == key) {
			return true;
		}
		else {
			return (find(root.left, key) || find(root.right, key));
		}
	}
	
	public String toString() {
		return toString(root, 0);
	}
	
	public String toString(Node<E> current, int level) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < level; i++) {
			sb.append(" ");
		}
		if (current == null) {
			sb.append("null\n");
		} else {
			sb.append("(key=" + current.data + ", priority=" + current.priority + ")\n");
			sb.append(toString(current.left, level+1));
			sb.append(toString(current.right,level+1));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Treap<Integer> testTree = new Treap<>();
	    testTree.add(4, 19);
	    testTree.add(2, 31);
	    testTree.add(6, 70);
	    testTree.add(1, 84);
	    testTree.add(3, 12);
	    testTree.add(5, 83);
	    testTree.add(7, 26);
	    
	    System.out.println("Treap after adding elements:");
	    System.out.println(testTree.toString());
	}
	
}