public class MyArrayList<E> {
    //instance variables
    private Object[] list;
    private int size;
    private int capacity;

    // makes an empty list that starts with room for ten things
    public MyArrayList() {
        capacity = 10;
        size = 0;
        list = new Object[capacity];
    }

    // adds the element to the very end of the list
    public boolean add(E element) {
        //increase array capacity
        if (size == capacity) {
            increaseCapacity();
        }

        //end of list
        list[size] = element;
        size++;

        return true;
    }

    //adds the element at a given index
    public void add(int index, E element) {
        //bounds check, index can equal size because adding there just means the very end
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index " + index + " is not in a list of size " + size);
        }

        //increase array capacity 
        if (size == capacity) {
            increaseCapacity();
        }

        //shift everything to the right
        for (int i = size; i > index; i--) {
            list[i] = list[i - 1];
        }

        list[index] = element; //add 
        size++;
    }

    //gives back the element sitting at that location
    @SuppressWarnings("unchecked")
    public E get(int index) {
        check(index);
        return (E) list[index];
    }

    //takes out the element at that location and returns whatever was removed
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        check(index);

        E gone = (E) list[index];

        //slide everything after the removal one spot to the left
        for (int i = index; i < size - 1; i++) {
            list[i] = list[i + 1];
        }

        //clear the extra last element
        list[size - 1] = null;
        size--;

        return gone; //removed element
    }

    //remove the first instance of a given element
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(list[i])) {
                remove(i);
                return true;
            }
        }

        return false;
    }

    //replaces element with given
    public void set(int index, E element) {
        check(index);

        list[index] = element;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    //get the location of the first element equal to given
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(list[i])) {
                return i;
            }
        }

        return -1; //not in list
    }

    //true when the list has something equal to the object passed in
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    //swap
    public void swap(int first, int second) {
        check(first);
        check(second);

        //swap
        Object temp = list[first];
        list[first] = list[second];
        list[second] = temp;
    }

    //empty list
    public void clear() {
        for (int i = 0; i < size; i++) {
            list[i] = null;
        }

        size = 0;
    }

    //holds every element in the list
    public String toString() {
        String result = "[";

        for (int i = 0; i < size; i++) {
            result = result + list[i];

            if (i < size - 1) {
                result = result + ", ";
            }
        }

        return result + "]";
    }

    //double array
    private void increaseCapacity() {
        capacity *= 2;

        Object[] newList = new Object[capacity];

        //copy elements
        for (int i = 0; i < size; i++) {
            newList[i] = list[i];
        }

        list = newList;
    }

    //bound check
    private void check(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index " + index + " is not in a list of size " + size);
        }
    }
}
