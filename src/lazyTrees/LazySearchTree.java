package lazyTrees;

import java.util.*;
/**
 * BST with lazy deletion to keep track of deleted as well as non deleted nodes
 * @author swati
 * @param <E> bounded generic object
 */
public class LazySearchTree<E extends Comparable< ? super E > >
implements Cloneable
{
	//  reflect the number of undeleted nodes
	protected int mSize;
	protected LazySTNode mRoot;
	
	// tracks the number of hard nodes in it, i.e., both deleted and undeleted
	private int mSizeHard;
	
    // tracks deleted nodes
	private int numDeletedNodes;

	public LazySearchTree() 
	{ 
		clear(); 
	}

	public boolean empty() 
	{ 
		return (mSize == 0); 
	}

	public int size() 
	{ 
		return mSize; 
	}

	public void clear() 
	{ 
		mSize = 0; 
		mRoot = null; 
		numDeletedNodes = 0;
		this.mSizeHard = 0;
	}

	public int showHeight() 
	{ 
		return findHeight(mRoot, -1); 
	}

	public E findMin() 
	{
		if (mRoot == null)
			throw new NoSuchElementException();

		return findMin(mRoot).data;
	}

	public E findMax() 
	{
		if (mRoot == null)
			throw new NoSuchElementException();
		return findMax(mRoot).data;
	}

	public E find( E x )
	{
		LazySTNode resultNode;
		resultNode = find(mRoot, x);
		
		if (resultNode == null)
			throw new NoSuchElementException();
		
		return resultNode.data;
	}

	public boolean contains(E x)  
	{ return find(mRoot, x) != null; }

	public boolean insert( E x )
	{
		int oldSize = mSize;
		mRoot = insert(mRoot, x);
		return (mSize != oldSize);
	}

	public boolean remove( E x )
	{
		int oldSize = mSize;
		remove(mRoot, x);
		return (mSize != oldSize);
	}

	// traverse both deleted and undeleted nodes
	public < F extends Traverser<? super E > > 
	void traverseHard(F func)
	{
		traverseHard(func, mRoot);
	}

	public < F extends Traverser<? super E > > 
	void traverseSoft(F func)
	{
		traverseSoft(func, mRoot);
	}

	public Object clone() throws CloneNotSupportedException
	{
		LazySearchTree<E> newObject = (LazySearchTree<E>)super.clone();
		newObject.clear();  // can't point to other's data
		newObject.mRoot = cloneSubtree(mRoot);
		newObject.mSize = mSize;
		return newObject;
	}

	// finds the minimum 
	// private helper methods ----------------------------------------
	protected LazySTNode findMin( LazySTNode root ) 
	{
		if (root == null)
			return null;
				 
		LazySTNode tempNode= findMin(root.lftChild);
		 
		   if (tempNode != null) return tempNode;
		 
		   if (!root.deleted) return root;
		   
		  // the left most node might not necessarily be the smallest node in the tree  
		   // so checks the right subtree if a root node was marked deleted and nothing could be found in the left subtree
		   return findMin(root.rtChild);						
	}

	protected LazySTNode findMax( LazySTNode root ) 
	{
		if (root == null)
			return null;
		
		LazySTNode tempNode= findMax(root.rtChild);
		 
		   if (tempNode != null) return tempNode;
		 
		   if (!root.deleted) return root;
		 
		   return findMax(root.lftChild);
	}

	protected LazySTNode insert( LazySTNode root, E x )
	{
		int compareResult;  // avoid multiple calls to compareTo()

		if (root == null)
		{
			mSize++;
			mSizeHard++;
			return new LazySTNode(x, null, null);
		}

		compareResult = x.compareTo(root.data); 

		if (compareResult == 0 && root.deleted == true)		
		{
			root.deleted = false;		
			numDeletedNodes--;
			mSize++;
			mSizeHard = mSize + numDeletedNodes;
		}
		
		if ( compareResult < 0 )
			root.lftChild = insert(root.lftChild, x);
		
		else if ( compareResult > 0 )
			root.rtChild = insert(root.rtChild, x);

		return root;
	}


	protected void remove( LazySTNode root, E x  )
	{
		int compareResult;  // avoid multiple calls to compareTo()

		if (root == null)
			throw new NoSuchElementException();

		compareResult = x.compareTo(root.data);

		// checks if the found node is deleted and marks it undeleted
		if (compareResult == 0 && !root.deleted) 
		{
			root.deleted = true;			
			mSize--;
			numDeletedNodes++;
			mSizeHard = mSize + numDeletedNodes;
		} 
		
		else if ( compareResult < 0 )
			remove(root.lftChild, x);
		
		else if ( compareResult > 0 )
			remove(root.rtChild, x);
	}

	// traverses the undeleted nodes
	protected <F extends Traverser<? super E>> 
	void traverseSoft(F func, LazySTNode treeNode)
	{
		if(treeNode == null)
			return;

		traverseSoft(func, treeNode.lftChild);	

		if(!treeNode.deleted)
		{
			func.visit(treeNode.data);				
		}

		traverseSoft(func, treeNode.rtChild);			
	}

	protected <F extends Traverser<? super E>> 
	void traverseHard(F func, LazySTNode treeNode)
	{
		if (treeNode == null)
			return;

		traverseHard(func, treeNode.lftChild);
		func.visit(treeNode.data);
		traverseHard(func, treeNode.rtChild);
	}

	protected LazySTNode find( LazySTNode root, E x )
	{
		int compareResult;  // avoid multiple calls to compareTo()

		if (root == null)
			return null;

		compareResult = x.compareTo(root.data); 
		
        // if found node is marked deleted return null
		if(compareResult == 0 && root.deleted)			
			return null;			

		if (compareResult < 0)
			return find(root.lftChild, x);
		
		if (compareResult > 0)
			return find(root.rtChild, x);

		return root;   // found
	}

	protected LazySTNode cloneSubtree(LazySTNode root)
	{
		LazySTNode newNode;
		if (root == null)
			return null;

		// does not set myRoot which must be done by caller
		newNode = new LazySTNode
				(
						root.data, 
						cloneSubtree(root.lftChild), 
						cloneSubtree(root.rtChild)
						);
		return newNode;
	}

	protected int findHeight( LazySTNode treeNode, int height ) 
	{
		int leftHeight, rightHeight;
		if (treeNode == null)
			return height;
		height++;
		leftHeight = findHeight(treeNode.lftChild, height);
		rightHeight = findHeight(treeNode.rtChild, height);
		return (leftHeight > rightHeight)? leftHeight : rightHeight;
	}

	public int getmSize() {
		return mSize;
	}

	public void setmSize(int mSize) {
		this.mSize = mSize;
	}

	public int sizeHard()
	{
		return this.mSizeHard;
	}

	private class LazySTNode
	{
		// use public access so the tree or other classes can access members 
		public LazySTNode lftChild, rtChild;
		public E data;
		public LazySTNode myRoot;  // needed to test for certain error
		boolean deleted;

		public LazySTNode( E d, LazySTNode lft, LazySTNode rt )
		{
			lftChild = lft; 
			rtChild = rt;
			data = d;
			deleted = false;
		}

		public LazySTNode()
		{
			this(null, null, null);
			deleted = false;
		}
	}
}