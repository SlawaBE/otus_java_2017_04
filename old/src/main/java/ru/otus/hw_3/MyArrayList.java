package ru.otus.hw_3;

import java.util.*;

/**
 * Created by stas on 18.04.17.
 */
public class MyArrayList<E> implements List<E> {

    public static final int DEFAULT_INITIAL_SIZE = 10;
    public static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;


    private Object[] array;

    private int size;

    public MyArrayList() {
        array = new Object[DEFAULT_INITIAL_SIZE];
    }

    public MyArrayList(int initialSize) {
        array = new Object[initialSize];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E e) {
        checkArraySize(size + 1);
        array[size] = e;
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        checkArraySize(size + 1);
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        checkArraySize(size + numNew);  // Increments modCount
        System.arraycopy(a, 0, array, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        checkArraySize(size + numNew);  // Increments modCount
        System.arraycopy(a, 0, array, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    @Override
    public int indexOf(Object obj) {
        if (obj == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (obj.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size-1; i >= 0; i--)
                if (array[i]==null)
                    return i;
        } else {
            for (int i = size-1; i >= 0; i--)
                if (o.equals(array[i]))
                    return i;
        }
        return -1;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return (E) array[index];
    }

    @Override
    public void clear() {
        array = new Object[DEFAULT_INITIAL_SIZE];
        size = 0;
    }

    private void rangeCheck(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
    }

    @Override
    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E old = (E) array[index];
        array[index] = element;
        return old;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <E> E[] toArray(E[] a) {
        if (a.length < size)
            return (E[]) Arrays.copyOf(array, size, a.getClass());
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        E old = (E) array[index];
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(array, index+1, array, index,
                    numMoved);
        size--;
        array[size] = null;
        return old;
    }

    @Override
    public boolean remove(Object o) {
        int indexOfRemoved = indexOf(o);
        if (indexOfRemoved >= 0) {
            remove(indexOfRemoved);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean b = false;
        for (Object obj : c) {
            b = b || remove(obj);
        }
        return b;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public E next() {
                if (cursor >= size) {
                    throw new NoSuchElementException();
                }
                return (E) array[cursor++];
            }
        };
    }

    @Override
    public void sort(Comparator<? super E> c) {
        Arrays.sort((E[]) array, 0, size, c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListIterator<E>() {

            int cursor = 0;
            int lastRet = -1;

            @Override
            public boolean hasNext() {
                return cursor < size ;
            }

            @Override
            public E next() {
                if (cursor >= size) {
                    throw new NoSuchElementException();
                }
                lastRet = cursor;
                cursor++;
                return (E) array[lastRet];
            }

            @Override
            public boolean hasPrevious() {
                return cursor != 0;
            }

            @Override
            public E previous() {
                if (cursor < 0) {
                    throw new NoSuchElementException();
                }
                lastRet = cursor;
                cursor--;
                return (E) array[lastRet];
            }

            @Override
            public int nextIndex() {
                return cursor;
            }

            @Override
            public int previousIndex() {
                return cursor - 1;
            }

            @Override
            public void remove() {
                if (lastRet < 0) {
                    throw new IllegalStateException();
                }
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            }

            @Override
            public void set(E e) {
                if (lastRet < 0) {
                    throw new IllegalStateException();
                }
                MyArrayList.this.set(lastRet, e);
            }

            @Override
            public void add(E e) {
                MyArrayList.this.add(cursor, e);
            }
        };
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private void checkArraySize(int size) {
        if (this.size >= array.length) {
            grow(size);
        }
    }

    private void grow(int minSize) {
        int oldSize = array.length;
        int newSize = oldSize + (oldSize >> 1);
        if (newSize - minSize < 0) {
            newSize = minSize;
        }
        if (newSize - MAX_ARRAY_SIZE > 0) {
            if (minSize < 0) {
                throw  new OutOfMemoryError();
            }
            newSize = newSize > MAX_ARRAY_SIZE ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
        }
        array = Arrays.copyOf(array, newSize);
    }



}
