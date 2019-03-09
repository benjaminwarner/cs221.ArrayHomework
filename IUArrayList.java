import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Array-based implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported. 
 * 
 * @author 
 *
 * @param <T> type to store
 */
public class IUArrayList<T> implements IndexedUnsortedList<T> {
	private static final int DEFAULT_CAPACITY = 100;
	private static final int NOT_FOUND = -1;
	
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
	
	/** Double the capacity of array */
	private void expandCapacityIfFull() {
		if (rear == array.length)
			array = Arrays.copyOf(array, array.length*2);
	}
	
	/** Shift all elements in the array forward one space */
	private void shiftElementsForwardOneSpace(int stoppingPoint) {
		for (int i = rear; i > stoppingPoint; --i)
			array[i] = array[i-1];
	}
	
	/** Shift all elements in the array backward one space */
	private void shiftElementsBackwardsOneSpace(int startingPoint) {
		for (int i = startingPoint; i < rear - 1; ++i)
			array[i] = array[i+1];
	}
	
	private void checkIndex(int index) {
		if (index >= rear || index < 0)
			throw new IndexOutOfBoundsException();
	}

	@Override
	public void addToFront(T element) {
		expandCapacityIfFull();
		shiftElementsForwardOneSpace(0);
		array[0] = element;
		++modCount;
		++rear;
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
		expandCapacityIfFull();
		shiftElementsForwardOneSpace(index+1);
		array[index+1] = element;
		++modCount;
		++rear;
	}

	@Override
	public void add(int index, T element) {
		expandCapacityIfFull();
		shiftElementsForwardOneSpace(index);
		array[index] = element;
		++modCount;
		++rear;
	}

	@Override
	public T removeFirst() {
		T element = array[0];
		shiftElementsBackwardOneSpace(0);
		array[rear] = null;
		++modCount;
		--rear;
		return element;
	}

	@Override
	public T removeLast() {
		T element = array[rear-1];
		++modCount;
		--rear;
		array[rear] = null;
		return element;
	}

	@Override
	public T remove(T element) {
		int index = indexOf(element);
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
		throw new NoSuchElementException();
	}

	@Override
	public T first() {
		return array[0];
	}

	@Override
	public T last() {
		return array[rear-1];
	}

	@Override
	public boolean contains(T target) {
		try {
			indexOf(target);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@Override
	public boolean isEmpty() {
		return rear == 0;
	}

	@Override
	public int size() {
		return rear;
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
		
		public ALIterator() {
			nextIndex = 0;
			iterModCount = modCount;
		}

		@Override
		public boolean hasNext() {
			// TODO 
			return false;
		}

		@Override
		public T next() {
			// TODO 
			return null;
		}
		
		@Override
		public void remove() {
			// TODO
			
		}
	}
}