package shloogie;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.junit.Test;

public class BigIntArrayListTest {
		
	String bigInt = "8921365014761823756128376172346123975124769813485712083719246908127893456172936509817203";
	
	@Test
	public void testAddAndGet() {
		BigIntArrayList list = new BigIntArrayList();
		
		list.add(new BigInteger(bigInt));
		
		assertEquals(new BigInteger(bigInt), list.get(0));
	}
	
	@Test
	public void testAddAtIndex() {
		BigIntArrayList list = new BigIntArrayList();
		
		list.add(4, new BigInteger(bigInt));
		
		assertEquals(new BigInteger(bigInt), list.get(4));		
	}

	@Test
	public void testAddAllAndSize() {
		BigIntArrayList list = new BigIntArrayList();
		
		List<BigInteger> collection = new ArrayList();
		for(int i=0; i<23; i++) collection.add(new BigInteger(bigInt));
		
		list.addAll(collection);
		
		assertEquals(23, list.size());
	}
	
	@Test
	public void testAddAllAtIndex() {
		BigIntArrayList list = new BigIntArrayList();
		
		List<BigInteger> collection = new ArrayList();
		for(int i=0; i<3; i++) collection.add(new BigInteger("12" + i + "366"));
		
		list.addAll(5, collection);
		
		assertEquals(new BigInteger("122366"), list.get(7));		
	}
	
	@Test
	public void testContains() {
		BigIntArrayList list = new BigIntArrayList();
		
		for(int i=12; i<56; i++) list.add(new BigInteger("12" + i + "56"));
		
		assertTrue(list.contains(new BigInteger("123456")));	
	}

	@Test
	public void testContainsAll() {
		BigIntArrayList list = new BigIntArrayList();
		
		for(int i=12; i<56; i++) list.add(new BigInteger("12" + i + "56"));
		
		BigIntArrayList collection = new BigIntArrayList();
		
		for(int j=20; j<29; j++) collection.add(new BigInteger("1" + j + "456"));
			
		assertFalse(list.containsAll(collection));	
	}

	@Test
	public void testIndexOf() {
		BigIntArrayList list = new BigIntArrayList();
		
		for(int i=12; i<56; i++) list.add(new BigInteger("12" + i + "56"));
		
		assertEquals(22, list.indexOf(new BigInteger("123456")));		
	}
	
	@Test
	public void testIsEmpty() {
		BigIntArrayList list = new BigIntArrayList();
		
		assertTrue(list.isEmpty());
	}
	
	@Test
	public void testIteratorNextAndHasNext() {
		BigIntArrayList list = new BigIntArrayList();
		
		for(int i=55; i<57; i++) list.add(new BigInteger(i + ""));
		
		assertEquals(new BigInteger("56"), list.iterator().next());		
		assertTrue(list.iterator().hasNext());
	}
	
	@Test
	public void testLastIndexOf() {
		BigIntArrayList list = new BigIntArrayList();
		
		for(int i=0; i<5; i++) list.add(new BigInteger("1533332"));
		
		assertEquals(4, list.lastIndexOf(new BigInteger("1533332")));
	}
	
	@Test
	public void testListIteratorNextAndHasNext() {
		BigIntArrayList list = new BigIntArrayList();

		for(int i=0; i<5; i++) list.add(new BigInteger(i + ""));

		ListIterator<BigInteger> iter = list.listIterator();
		
		assertEquals(new BigInteger("1"), iter.next());
		assertTrue(iter.hasNext());
	}
	
	@Test
	public void testListIteratorPreviousAndHasPrevious() {
		BigIntArrayList list = new BigIntArrayList();

		for(int i=0; i<5; i++) list.add(new BigInteger(i + ""));

		ListIterator<BigInteger> iter = list.listIterator(4);
		
		assertEquals(new BigInteger("3"), iter.previous());
		assertTrue(iter.hasPrevious());	
	}
	
	@Test
	public void testListIteratorNextIndex() {
		BigIntArrayList list = new BigIntArrayList();

		for(int i=0; i<5; i++) list.add(new BigInteger(i + ""));

		assertEquals(4, list.listIterator(3).nextIndex());	
	}
	
	@Test
	public void testListIteratorPreviousIndex() {
		BigIntArrayList list = new BigIntArrayList();

		for(int i=0; i<5; i++) list.add(new BigInteger(i + ""));

		assertEquals(2, list.listIterator(3).previousIndex());
	}
	
	@Test
	public void testListIteratorRemoveAndRemoveAt() {
		BigIntArrayList list = new BigIntArrayList();

		for(int i=0; i<5; i++) list.add(new BigInteger(i + ""));

		ListIterator<BigInteger> iter = list.listIterator();
		iter.next();
		iter.remove();
		
		assertEquals(4, list.size());
	}

	@Test
	public void testListIteratorSetIncludingNormalSet() {
		BigIntArrayList list = new BigIntArrayList();

		for(int i=0; i<5; i++) list.add(new BigInteger(i + ""));

		ListIterator<BigInteger> iter = list.listIterator();
		iter.next();
		iter.next();
		iter.set(new BigInteger("6814512351234"));
		
		assertEquals(new BigInteger("6814512351234"), list.get(2));
	}
	
	@Test
	public void testListIteratorAdd(){
		BigIntArrayList list = new BigIntArrayList();

		for(int i=0; i<5; i++) list.add(new BigInteger(i + ""));

		ListIterator<BigInteger> iter = list.listIterator();
		iter.next();
		iter.add(new BigInteger("5214613461324512"));
		
		assertEquals(6, list.size());
	}
	
	@Test
	public void testRemove() {
		BigIntArrayList list = new BigIntArrayList();

		for(int i=0; i<5; i++) list.add(new BigInteger(i + ""));

		assertTrue(list.remove(new BigInteger("3")));
	}
	
	@Test
	public void testRemoveAll() {
		BigIntArrayList list = new BigIntArrayList();
		List<BigInteger> collection = new ArrayList();
		
		for(int i=0; i<25; i++) list.add(new BigInteger(i + ""));
		for(int j=3; j<14; j++) collection.add(new BigInteger(j + ""));
		
		assertTrue(list.removeAll(collection));
	}
	
	@Test
	public void testRetainAll() {
		BigIntArrayList list = new BigIntArrayList();
		List<BigInteger> collection = new ArrayList();
		
		for(int i=0; i<25; i++) list.add(new BigInteger(i + ""));
		for(int j=3; j<14; j++) collection.add(new BigInteger(j + ""));
		
		assertTrue(list.retainAll(collection));
	}

	@Test
	public void testSublist() {
		BigIntArrayList list = new BigIntArrayList();
		
		for(int i=0; i<25; i++) list.add(new BigInteger(i + ""));
		
		assertEquals(9, list.subList(6, 15).size());
	}
}