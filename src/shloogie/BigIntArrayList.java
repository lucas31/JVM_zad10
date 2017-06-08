package shloogie;

import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class BigIntArrayList implements List<Integer> {

	private BigInteger[] array;
	private int counter = 0;
	private int iterator = 0;
	
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
	public boolean add(Integer e) {
		if(array == null) return false;
		else {
			array[counter] = BigInteger.ZERO;
			array[counter] = array[counter].add(BigInteger.valueOf(e));
			counter++;
			return true;
		}
	}

	@Override
	public void add(int index, Integer element) {
		if(index >= array.length) throw new ArrayIndexOutOfBoundsException(index);
		else {
			array[index] = BigInteger.ZERO;
			array[index] = array[index].add(BigInteger.valueOf(element));
		}		
	}

	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		return this.addAll(counter, c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Integer> c) {
		if(c.size() > index + this.array.length) this.Reinitialize(index + (c.size() * 2));
		
		for(int i=0; i<c.size(); i++) {
			this.array[index + i] = this.array[index + i].add(BigInteger.valueOf(c.iterator().next()));
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
			if(array[i] == o) return true;
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
	public Integer get(int index) {
		if(array[index].compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) >= 0) throw new ArithmeticException();
		else return array[index].intValue();
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
	public Iterator<Integer> iterator() {		
		return new Iterator<Integer>() {
			
			@Override
			public Integer next() {
				if (this.hasNext()) {
					return array[++iterator].intValue();
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
		
		for(int i=0; i<this.array.length; i++) {
			if (this.array[i].equals(o)) index = i;
		}
		
		if (index == -1) throw new NullPointerException();
		else return index;
	}

	@Override
	public ListIterator<Integer> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<Integer> listIterator(int index) {
		return new ListIterator<Integer>() {
			
			private boolean nextOrPreviousCalled = false;
			private boolean addOrRemoveCalledSinceLastNoP = false;
			private int newListIterator = index;

			@Override
			public boolean hasNext() {
				return newListIterator + 1 < array.length;
			}

			@Override
			public Integer next() {
				if (this.hasNext()) return array[++newListIterator].intValue();
				else throw new NoSuchElementException();
			}

			@Override
			public boolean hasPrevious() {
				return newListIterator - 1 >= 0;
			}

			@Override
			public Integer previous() {
				if (this.hasPrevious()) return array[--newListIterator].intValue();
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
				}
				else throw new IllegalStateException();
				
			}

			@Override
			public void set(Integer e) {
				if (nextOrPreviousCalled && !addOrRemoveCalledSinceLastNoP) {
					BigIntArrayList.this.set(newListIterator, e);
				}
				else throw new IllegalStateException();			
			}

			@Override
			public void add(Integer e) {
				if (nextOrPreviousCalled && !addOrRemoveCalledSinceLastNoP) {
					BigIntArrayList.this.add(newListIterator, e);
					newListIterator++;
					addOrRemoveCalledSinceLastNoP = true;
				}
				else throw new IllegalStateException();			
			}
			
		};
	}

	@Override
	public boolean remove(Object o) {
		if(!this.contains(o)) return false;
		
		for(int i=0; i<this.array.length; i++) {
			if (this.array[i].equals(o)) {
				this.remove(i);
				i--;
			}
		}
		
		return true;
	}

	@Override
	public Integer remove(int index) {
		BigInteger val = BigInteger.ZERO;
		
		for(int j=index; j<this.array.length; j++) { 
			val = this.array[j];
			this.array[j] = this.array[j+1];
		}
		return val.intValue();
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
		for(int i=0; i<this.array.length; i++) {
			if(!c.contains(this.array[i])) {
				this.remove(i);
				i--;
				changed = true;
			}			
		}
		return changed;
	}

	@Override
	public Integer set(int index, Integer element) {
		if(index >= this.array.length) throw new IndexOutOfBoundsException();
		else {
			this.array[index] = BigInteger.ZERO;
			this.array[index].add(BigInteger.valueOf(element));			
		}
		return this.array[index].intValue();
	}

	@Override
	public int size() {
		return this.array.length;
	}

	@Override
	public List<Integer> subList(int fromIndex, int toIndex) {
		if(fromIndex > this.array.length || toIndex > this.array.length) throw new IndexOutOfBoundsException();
		else if (fromIndex < toIndex) throw new InvalidParameterException(); 
		
		List<Integer> list = new ArrayList<Integer>();
		
		for(int i=fromIndex; i<toIndex; i++) {
			list.add(this.array[i].intValue());
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
