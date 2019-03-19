package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class ArrayLinearList<E> implements LinearListADT<E>{

	protected int front;
	protected int rear;
	protected int maxCapacity;
	protected int size;
	protected E[] list;
	protected long modificationCounter;
	
	@SuppressWarnings("unchecked")
	public ArrayLinearList(int maxCapacity) {
		size = 0;
		modificationCounter = 0;
		this.maxCapacity = maxCapacity;
		list = (E[]) new Object[maxCapacity];
	}
	
	@SuppressWarnings("unchecked")
	public ArrayLinearList() {
		size = 0;
		modificationCounter = 0;
		list = (E[]) new Object[DEFAULT_MAX_CAPACITY];
	}
	
	@Override
	public void ends() {
		System.out.println("Front: " + front + " Rear: " + rear);
	}

	@Override
	public boolean addFirst(E obj) {
		if(isFull()) {
			return false;
		}
        front--;
        if(front == -1){
            front = maxCapacity - 1;
        }
        list[front] = obj;
        size++;
        modificationCounter++;
        return true;
	}

	@Override
	public boolean addLast(E obj) {
		if(isFull()) {
			return false;
		}
		list[rear] = obj;
        size++; 
        modificationCounter++;
        return true;
	}

	@Override
	public E removeFirst() {
		E temp;
		temp = list[front];
		if(isEmpty()) {
			return null;
		}
		else {
			if(front == maxCapacity - 1) {
				list[front] = null;
				front = 0;
				size--;
			}
			else {
				list[front] = null;
				front++;
				size--;
			}
	        modificationCounter++;
			return temp;
		}
	}

	@Override
	public E removeLast() {
		E temp;
		temp = list[rear];
		if(isEmpty()) {
			return null;
		}
		else {
			if(rear == 0) {
				list[rear] = null;
				rear = maxCapacity - 1;
				size--;
			}
			else {
				list[rear] = null;
				rear--;
				size--;
			}
	        modificationCounter++;
			return temp;
		}
	}

	@Override
	public E remove(E obj) {
		int index = 0;
		E temp;
		if(contains(obj)) {
			Iterator<E> i = new IteratorHelper();
			while(i.hasNext()) {
				if(i.next() == obj){
					list[index] = null;
				}
				index++;
			}
			while(index != size()){
				temp = list[index];
				list[index-1] = temp;
				index++;
			}
			modificationCounter++;
			return obj;
		}
		return null;
	}

	@Override
	public E peekFirst() {
		if(isEmpty()) {
			return null;
		}
		return list[front];
	}

	@Override
	public E peekLast() {
		if(isEmpty()) {
			return null;
		}
		return list[rear];
	}

	@Override
	public boolean contains(E obj) {
		Iterator<E> i = new IteratorHelper();
		while(i.hasNext()) {
			if(i.next() == obj) {
				return true;
			}
		}
		return false;
	}

	@Override
	public E find(E obj) {
		Iterator<E> i = new IteratorHelper();
		while(i.hasNext()) {
			if(i.next() == obj) {
				return obj;
			}
		}
		return null;
	}

	@Override
	public void clear() {
		int count = 0;
		Iterator<E> i = new IteratorHelper();
		while(i.hasNext()) {
			list[count] = null;
			count++;
			}
		}

	@Override
	public boolean isEmpty() {
		if(size() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isFull() {
		if(size() == maxCapacity)
			return true;
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<E> iterator(){
		return new IteratorHelper();
	}
	private class IteratorHelper implements Iterator<E>
	{
		int count,index;
		long stateCheck;
		
		public IteratorHelper() {
			index = 0;
			count = 0;
			stateCheck = modificationCounter;
		}
		@Override
		public boolean hasNext() {
			if(stateCheck != modificationCounter) {
				throw new ConcurrentModificationException();
			}
			return count != size();
		}
		
		@Override
		public E next() {
			if(!hasNext())
				throw new NoSuchElementException();
			E tmp = list[(index++) % size()];
			count++;
			return tmp;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
}
