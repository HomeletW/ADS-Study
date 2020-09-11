package ADS.Recursion;

import ADS.ArrayGenerator;
import ADS.Basic.Array;
import com.sun.org.apache.bcel.internal.generic.LADD;

import java.util.*;

public class BinarySearchTree<E extends Comparable<E>>{
	
	private Node root;
	private int  size;
	
	public BinarySearchTree(){
		root = null;
		size = 0;
	}
	
	public int size(){
		return size;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	public void add(E e){
		root = add(root, e);
	}
	
	private Node add(Node node, E e){
		if(node == null){
			size++;
			return new Node(e);
		}
		int res = e.compareTo(node.e);
		if(res < 0) // add to left
			node.left = add(node.left, e);
		else if(res > 0) // add to right
			node.right = add(node.right, e);
		
		return node;
	}
	
	public boolean contains(E e){
		return contains(root, e);
	}
	
	private boolean contains(Node node, E e){
		if(node == null)
			return false;
		int res = e.compareTo(node.e);
		if(res == 0)
			return true;
		else if(res < 0) // on the left tree
			return contains(node.left, e);
		else // on the right tree
			return contains(node.right, e);
	}
	
	
	public void preorderTraverseNR(Action<E> action){
		Deque<NodeDepthPair> stack = new ArrayDeque<>(size);
		stack.push(new NodeDepthPair(root, 0));
		while(! stack.isEmpty()){
			NodeDepthPair ndp = stack.pop();
			Node node = ndp.node;
			int depth = ndp.depth;
			action.perform(node.e, depth);
			if(node.right != null)
				stack.push(new NodeDepthPair(node.right, depth + 1));
			if(node.left != null)
				stack.push(new NodeDepthPair(node.left, depth + 1));
		}
	}
	
	
	public void preorderTraverse(Action<E> action){
		preorderTraverse(root, 0, action);
	}
	
	private void preorderTraverse(Node node, int depth, Action<E> action){
		if(node == null)
			return;
		action.perform(node.e, depth);
		preorderTraverse(node.left, depth + 1, action);
		preorderTraverse(node.right, depth + 1, action);
	}
	
	public void inorderTraverse(Action<E> action){
		inorderTraverse(root, 0, action);
	}
	
	private void inorderTraverse(Node node, int depth, Action<E> action){
		if(node == null)
			return;
		inorderTraverse(node.left, depth + 1, action);
		action.perform(node.e, depth);
		inorderTraverse(node.right, depth + 1, action);
	}
	
	public void postorderTraverse(Action<E> action){
		postorderTraverse(root, 0, action);
	}
	
	private void postorderTraverse(Node node, int depth, Action<E> action){
		if(node == null)
			return;
		postorderTraverse(node.left, depth + 1, action);
		postorderTraverse(node.right, depth + 1, action);
		action.perform(node.e, depth);
	}
	
	public void breathFirstTraversal(Action<E> action){
		Queue<NodeDepthPair> queue = new ArrayDeque<>(size);
		queue.add(new NodeDepthPair(root, 0));
		while(! queue.isEmpty()){
			NodeDepthPair ndp = queue.remove();
			Node node = ndp.node;
			int depth = ndp.depth;
			action.perform(node.e, depth);
			if(node.left != null)
				queue.add(new NodeDepthPair(node.left, depth + 1));
			if(node.right != null)
				queue.add(new NodeDepthPair(node.right, depth + 1));
		}
	}
	
	public E minimum(){
		if(size == 0)
			throw new IllegalArgumentException();
		return minimum(root).e;
	}
	
	private Node minimum(Node node){
		if(node.left == null)
			return node;
		else
			return minimum(node.left);
	}
	
	public E maximum(){
		if(size == 0)
			throw new IllegalArgumentException();
		return maximum(root).e;
	}
	
	private Node maximum(Node node){
		if(node.right == null)
			return node;
		else
			return maximum(node.right);
	}
	
	public E removeMin(){
		E ret = minimum();
		root = removeMin(root);
		return ret;
	}
	
	private Node removeMin(Node node){
		if(node.left == null){
			// we are at the min node now
			// since we want to remove this node, we need to keep the right node
			Node right = node.right;
			node.right = null;
			size--;
			return right;
		}else{
			node.left = removeMin(node.left);
			return node;
		}
	}
	
	public E removeMax(){
		E ret = maximum();
		root = removeMax(root);
		return ret;
	}
	
	private Node removeMax(Node node){
		if(node.right == null){
			Node left = node.left;
			node.left = null;
			size--;
			return left;
		}else{
			node.right = removeMax(node.right);
			return node;
		}
	}
	
	public void remove(E e){
		root = remove(root, e);
	}
	
	private Node remove(Node node, E e){
		if(node == null)
			return null;
		int res = e.compareTo(node.e);
		if(res < 0){
			node.left = remove(node.left, e);
			return node;
		}else if(res > 0){
			node.right = remove(node.right, e);
			return node;
		}else{
			if(node.left == null){
				// if we don't have left, we just mount right
				Node right = node.right;
				node.right = null;
				size--;
				return right;
			}else if(node.right == null){
				// if we don't have right, we just mount left
				Node left = node.left;
				node.left = null;
				size--;
				return left;
			}else{
				// we find the very next item that is the smallest item in the right tree
				Node successor = minimum(node.right);
				// now we mount the left and right to the successor
				// at the same time we delete the successor node from the right tree.
				// notice that we already did an size -- in remove min, therefore we don't need it here.
				successor.right = removeMin(node.right);
				successor.left = node.left;
				node.left = null;
				node.right = null;
				return successor;
			}
		}
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Binary Search Tree - size:").append(size).append('\n');
		breathFirstTraversal((e, depth) -> {
			for(int i = 0; i < depth; i++)
				builder.append("--");
			builder.append(e).append("\n");
		});
		return builder.toString();
	}
	
	public String prettyTree(){
		StringBuilder builder = new StringBuilder();
		builder.append("Binary Search Tree - size:").append(size).append('\n');
		if(root == null)
			return builder.toString();
		char[][] tree = prettyTree(root);
		for(int y = 0; y < tree.length; y++){
			for(int x = 0; x < tree[y].length; x++){
				char c = tree[y][x];
				if(c == '\u0000')
					builder.append(' ');
				else
					builder.append(c);
			}
			builder.append('\n');
		}
		return builder.toString();
	}
	
	private char[][] prettyTree(Node node){
		if(node == null)
			return null;
		// first let left and right draw their tree
		char[][] left = prettyTree(node.left);
		char[][] right = prettyTree(node.right);
		// then draw ours tree
		return mergeTree(left, right, node.e.toString());
	}
	
	private char[][] mergeTree(char[][] left, char[][] right, String e){
		int leftHeight = left != null ? left.length : 0;
		int rightHeight = right != null ? right.length : 0;
		int leftWidth = left != null ? left[0].length + 1 : 3;
		int rightWidth = right != null ? right[0].length : 2;
		int topHeight = 1 + (left == null && right == null ? 0 : 1);
		
		int height = Math.max(leftHeight, rightHeight) + topHeight;
		int width = Math.max(leftWidth + rightWidth, e.length() + 2);
		char[][] canvas = new char[height][width];
		// first copy the left part
		int leftMid = copy(canvas, left, 0, topHeight);
		// then copy the right part
		int rightMid = copy(canvas, right, leftWidth, topHeight);
		// now create the top part
		// fill the string first
		int middle = width / 2;
		int offset = middle - e.length() / 2;
		for(int i = 0; i < e.length(); i++)
			canvas[0][offset + i] = e.charAt(i);
		if(left != null || right != null){
			// draw the relation
			int start = leftMid < 0 ? middle : leftMid;
			int end = rightMid < 0 ? middle : rightMid;
			for(int i = start; i <= end; i++){
				if(i == leftMid){
					canvas[1][i] = '┌';
				}else if(i == middle){
					if(left != null && right != null)
						canvas[1][i] = '┴';
					else if(left != null)
						canvas[1][i] = '┘';
					else
						canvas[1][i] = '└';
				}else if(i == rightMid){
					canvas[1][i] = '┐';
				}else{
					canvas[1][i] = '─';
				}
			}
		}
		// now we have finished!
		return canvas;
	}
	
	// copy and return x coordinate in canvas of the middle part of copy
	private int copy(char[][] canvas, char[][] copy, int xOffset, int yOffset){
		if(copy == null)
			return - 1;
		for(int y = 0; y < copy.length; y++){
			for(int x = 0; x < copy[y].length; x++){
				canvas[y + yOffset][x + xOffset] = copy[y][x];
			}
		}
		return copy[0].length / 2 + xOffset;
	}
	
	public interface Action<E extends Comparable<E>>{
		
		void perform(E e, int depth);
	}
	
	class Node{
		
		E    e;
		Node left;
		Node right;
		
		Node(E e, Node left, Node right){
			this.e = e;
			this.left = left;
			this.right = right;
		}
		
		Node(E e){
			this(e, null, null);
		}
	}
	
	private class NodeDepthPair{
		
		final Node node;
		final int  depth;
		
		NodeDepthPair(Node node, int depth){
			this.node = node;
			this.depth = depth;
		}
	}
	
	public static void main(String[] args){
		int n = 100;
//		Integer[] arr = ArrayGenerator.randomArray(n);
		int[] arr = {4, 2, 8, 1, 3, 6, 10, 5, 7, 9, 11};
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		for(int i : arr)
			tree.add(i);
		System.out.println(tree.prettyTree());
		tree.remove(20);
		System.out.println(tree.maximum());
		System.out.println(tree.prettyTree());
	}
}
