package shloogie;

import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class BigIntArrayList implements List<BigInteger> {

	private BigInteger[] array;
	private int counter = 0;
	private int iterator = 0;
	
	public BigIntArrayList() {
		this.Initialize();
	}
	
	public BigIntArrayList(int capacity) {
		this.Initialize(capacity);
	}
	
	private void Initialize() { this.Initialize(10); };
	private void Reinitialize(boolean clean) {
		if(clean) this.Initialize();
		else this.Reinitialize(array.length);
	}
	private void Reinitialize(int newCap) {
		BigInteger[] temp = array;
		array = new BigInteger[newCap];
		counter = 0;
		
		for(int i=0; i<temp.length; i++) {
			array[i] = temp[i];
			counter++;
		}
	}
	public void Initialize(int capacity) { this.array = new BigInteger[capacity]; };
	
	@Override
	public boolean add(BigInteger e) {
		if(array == null) return false;
		else {
			if(counter + 1 > array.length) this.Reinitialize(array.length + 10);
			
			array[counter] = e;
			counter++;
			return true;
		}
	}

	@Override
	public void add(int index, BigInteger element) {
		if(index >= array.length) throw new ArrayIndexOutOfBoundsException(index);
		else {
			if(counter + 1 > array.length) this.Reinitialize(array.length + 10);
			
			for(int i=counter+1; i>index; i--) array[i] = array[i-1];			
			array[index] = element;
						
			counter++;
		}		
	}
	
	@Override
	public boolean addAll(Collection<? extends BigInteger> c) {
		return this.addAll(counter, c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends BigInteger> c) {
		if(c.size() > index + this.array.length) this.Reinitialize(index + (c.size() * 2));

		int i=index;
		for(BigInteger element : c) {
			this.add(i, element);			
			i++;
		}
		
		counter = index + c.size();
		return true;
	}

	@Override
	public void clear() {
		this.Reinitialize(true);
	}

	@Override
	public boolean contains(Object o) {
		for(int i=0; i<array.length; i++) {
			if(array[i] != null && array[i].equals(o)) return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for(Object o : c) {
			if (!this.contains(o)) return false;
		}		
		return true;
	}

	@Override
	public BigInteger get(int index) {
		return array[index];
	}

	@Override
	public int indexOf(Object o) {
		if(!this.contains(o)) throw new NullPointerException();
		else {
			for(int i=0; i<this.array.length; i++) {
				if (this.array[i].equals(o)) return i;
			}
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return this.counter == 0;
	}

	@Override
	public Iterator<BigInteger> iterator() {		
		return new Iterator<BigInteger>() {
			
			@Override
			public BigInteger next() {
				if (this.hasNext()) {
					return array[++iterator];
				}
				else throw new NoSuchElementException();
			}
			
			@Override
			public boolean hasNext() {
				return iterator + 1 < array.length;
			}
		};
	}

	@Override
	public int lastIndexOf(Object o) {
		int index=-1;
		
		for(int i=0; i<counter; i++) {
			if (this.array[i].equals(o)) index = i;
		}
		
		if (index == -1) throw new NullPointerException();
		else return index;
	}

	@Override
	public ListIterator<BigInteger> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<BigInteger> listIterator(int index) {
		return new ListIterator<BigInteger>() {
			
			private boolean nextOrPreviousCalled = false;
			private boolean addOrRemoveCalledSinceLastNoP = false;
			private int newListIterator = index;

			@Override
			public boolean hasNext() {
				return newListIterator + 1 < array.length;
			}

			@Override
			public BigInteger next() {
				if (this.hasNext()) {
					nextOrPreviousCalled = true;
					addOrRemoveCalledSinceLastNoP = false;
					return array[++newListIterator];				
				}
				else throw new NoSuchElementException();
			}

			@Override
			public boolean hasPrevious() {
				return newListIterator - 1 >= 0;
			}

			@Override
			public BigInteger previous() {
				if (this.hasPrevious())  {
					nextOrPreviousCalled = true;
					addOrRemoveCalledSinceLastNoP = false;
					return array[--newListIterator];
				}
				else throw new NoSuchElementException();
			}

			@Override
			public int nextIndex() {
				return newListIterator + 1;
			}

			@Override
			public int previousIndex() {
				return newListIterator - 1;
			}


			@Override
			public void remove() {
				if (nextOrPreviousCalled && !addOrRemoveCalledSinceLastNoP) {
					BigIntArrayList.this.remove(newListIterator);
					addOrRemoveCalledSinceLastNoP = true;
					nextOrPreviousCalled = false;
				}
				else throw new IllegalStateException();
				
			}

			@Override
			public void set(BigInteger e) {
				if (nextOrPreviousCalled && !addOrRemoveCalledSinceLastNoP) {
					BigIntArrayList.this.set(newListIterator, e);
				}
				else throw new IllegalStateException();			
			}

			@Override
			public void add(BigInteger e) {
				if (nextOrPreviousCalled && !addOrRemoveCalledSinceLastNoP) {
					BigIntArrayList.this.add(newListIterator, e);
					newListIterator++;
					addOrRemoveCalledSinceLastNoP = true;
					nextOrPreviousCalled = false;
				}
				else throw new IllegalStateException();			
			}
			
		};
	}

	@Override
	public boolean remove(Object o) {
		if(!this.contains(o)) return false;
		
		for(int i=0; i<this.counter; i++) {
			if (this.array[i].equals(o)) {
				this.remove(i);
				i--;
			}
		}
		
		return true;
	}

	@Override
	public BigInteger remove(int index) {
		BigInteger val = BigInteger.ZERO;
		
		for(int j=index; j<this.counter; j++) { 
			val = this.array[j];
			this.array[j] = this.array[j+1];
		}
		
		this.array[counter - 1] = null;
		counter--;
		return val;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = true;
		
		for(Object o : c) {
			result = result && this.remove(o); 
		}
		
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean changed = false;
		for(int i=0; i<this.counter; i++) {
			if(!c.contains(this.array[i])) {
				this.remove(i);
				i--;
				changed = true;
			}			
		}
		return changed;
	}

	@Override
	public BigInteger set(int index, BigInteger element) {
		if(index >= this.array.length) throw new IndexOutOfBoundsException();
		else {
			this.array[index] = element;			
		}
		return this.array[index];
	}

	@Override
	public int size() {
		return this.counter;
	}

	@Override
	public List<BigInteger> subList(int fromIndex, int toIndex) {
		if(fromIndex > this.counter || toIndex > this.counter) throw new IndexOutOfBoundsException();
		else if (fromIndex > toIndex) throw new InvalidParameterException(); 
		
		List<BigInteger> list = new ArrayList<BigInteger>();
		
		for(int i=fromIndex; i<toIndex; i++) {
			list.add(this.array[i]);
		}
		
		return list;
	}

	@Override
	public Object[] toArray() {
		Object[] retArray;
		retArray = this.array;
		
		return retArray;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		Object[] retArray;
		retArray = this.array;
		
		return (T[]) retArray;
	}

	
}
