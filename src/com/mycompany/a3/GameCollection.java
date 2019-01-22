package com.mycompany.a3;

import java.util.Vector;

public class GameCollection implements ICollection
{
	private Vector<GameObject> collection;
	
	public GameCollection()
	{
		collection = new Vector<GameObject>();
	}
	
	public void add(GameObject object)
	{
		collection.addElement(object);
	}
	
	public void remove(int location)
	{
		collection.removeElementAt(location);
	}
	
	public void clear()
	{
		collection.removeAllElements();
	}
	
	public IIterator getIterator()
	{
		return new GameCollectionIterator();
	}
	
	
	
	private class GameCollectionIterator implements IIterator
	{
		private int currElementIndex;
		
		public GameCollectionIterator() 
		{
			currElementIndex = -1;
		}
		
		public boolean hasNext()
		{
			if (collection.size() <= 0) return false;
			if (currElementIndex == collection.size() - 1 )
			return false;
			return true;

		}
		
		public Object getNext()
		{
			currElementIndex ++ ;
			return(collection.elementAt(currElementIndex));
		}
		
		public int getIndex()
		{
			return currElementIndex;
		}
		
		public void setIndex(int loc)
		{
			currElementIndex = loc;
		}
	}
	
}
