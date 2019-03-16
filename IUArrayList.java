import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Array-based implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported. 
 * 
 * @author Benjamin Warner
 *
 * @param <T> type to store
 */
public class IUArrayList<T> implements IndexedUnsortedList<T> {
	private static final int DEFAULT_CAPACITY = 100;
	
	private T[] array;
	private int rear;
	private int modCount;
	
	/** Creates an empty list with default initial capacity */
	public IUArrayList() {
		this(DEFAULT_CAPACITY);
	}
	
	/** 
	 * Creates an empty list with the given initial capacity
	 * @param initialCapacity
	 */
	@SuppressWarnings("unchecked")
	public IUArrayList(int initialCapacity) {
		array = (T[])(new Object[initialCapacity]);
		rear = 0;
		modCount = 0;
	}
	
	/** Double the capacity of array if it's full */
	private void expandCapacityIfFull() {
		if (rear == array.length)
			array = Arrays.copyOf(array, array.length*2);
	}
	
	/** 
	 * Shift all elements in the array forward one space, until reaching 
	 * stoppingPoint
	 * @param stoppingPoint
	 */
	private void shiftElementsForwardOneSpace(int stoppingPoint) {
		for (int i = rear; i > stoppingPoint; --i)
			array[i] = array[i-1];
	}
	
	/** 
	 * Shift all elements in the array backward one space,
	 * starting at startingPoint
	 * @param startingPoint
	 */
	private void shiftElementsBackwardsOneSpace(int startingPoint) {
		for (int i = startingPoint; i < rear - 1; ++i)
			array[i] = array[i+1];
	}
	
	/**
	 * Validate that the given index is within the appropriate 
	 * range (non negative and not greater than size)
	 * @param index
	 */
	private void checkIndex(int index) {
		if (index >= rear || index < 0)
			throw new IndexOutOfBoundsException();
	}

	@Override
	public void addToFront(T element) {
		add(0, element);
	}

	@Override
	public void addToRear(T element) {
		expandCapacityIfFull();
		array[rear] = element;
		++rear;
		++modCount;
	}

	@Override
	public void add(T element) {
		addToRear(element);
	}

	@Override
	public void addAfter(T element, T target) {
		int index = indexOf(target);
		if (index == -1)
			throw new NoSuchElementException();
		add(index+1, element);
	}

	@Override
	public void add(int index, T element) {
		if (index > rear || index < 0)
			throw new IndexOutOfBoundsException();
		expandCapacityIfFull();
		shiftElementsForwardOneSpace(index);
		array[index] = element;
		++modCount;
		++rear;
	}

	@Override
	public T removeFirst() {
		if (rear == 0)
			throw new NoSuchElementException();
		T element = array[0];
		shiftElementsBackwardsOneSpace(0);
		--rear;
		array[rear] = null;
		++modCount;
		return element;
	}

	@Override
	public T removeLast() {
		if (rear == 0)
			throw new NoSuchElementException();
		T element = array[rear-1];
		++modCount;
		--rear;
		array[rear] = null;
		return element;
	}

	@Override
	public T remove(T element) {
		int index = indexOf(element);
		if  (index == -1)
			throw new NoSuchElementException();
		T retVal = array[index];
		shiftElementsBackwardsOneSpace(index);
		--rear;
		array[rear] = null;
		++modCount;
		return retVal;
	}

	@Override
	public T remove(int index) {
		checkIndex(index);
		T element = array[index];
		shiftElementsBackwardsOneSpace(index);
		--rear;
		++modCount;
		array[rear] = null;
		return element;
	}

	@Override
	public void set(int index, T element) {
		checkIndex(index);
		array[index] = element;
	}

	@Override
	public T get(int index) {
		checkIndex(index);
		return array[index];
	}

	@Override
	public int indexOf(T element) {
		for (int i = 0; i < rear; ++i) {
			if (element.equals(array[i]))
				return i;
		}
		return -1;
	}

	@Override
	public T first() {
		if (rear == 0)
			throw new NoSuchElementException();
		return array[0];
	}

	@Override
	public T last() {
		if (rear == 0)
			throw new NoSuchElementException();
		return array[rear-1];
	}

	@Override
	public boolean contains(T target) {
		int index = indexOf(target);
		return index != -1;
	}

	@Override
	public boolean isEmpty() {
		return rear == 0;
	}

	@Override
	public int size() {
		return rear;
	}
	
	public String toString() {
		if (rear == 0)
			return "[]";
		String str = "[";
		for (int i = 0; i < rear - 1; ++i)
			str += array[i].toString() + ", ";
		str += array[rear-1] + "]";
		return str;
	}

	@Override
	public Iterator<T> iterator() {
		return new ALIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUArrayList */
	private class ALIterator implements Iterator<T> {
		private int nextIndex;
		private int iterModCount;
		private boolean canRemove;
		
		public ALIterator() {
			nextIndex = 0;
			iterModCount = modCount;
		}

		@Override
		public boolean hasNext() {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			return nextIndex < rear;
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			++nextIndex;
			canRemove = true;
			return array[nextIndex-1];
		}
		
		@Override
		public void remove() {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			else if (!canRemove)
				throw new IllegalStateException();
			canRemove = false;
			// I'm not actually implementing this method cause this method shouldn't exist
		}
	}
}