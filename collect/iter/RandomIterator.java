package team137.collect.iter;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomIterator<E> implements Iterator<E> {

  protected final Random rand;

  private E[] array;
  private int upperBound;

  protected RandomIterator(Random rand, E[] array) {
    this.rand = rand;
    this.array = array;
    this.upperBound = array.length;
  }

  private void swap(int i, int j) {
    E t = array[i];
    array[i] = array[j];
    array[j] = t;
  }

  @Override
  public boolean hasNext() {
    return upperBound > 0;
  }

  @Override
  public E next() {
    if(hasNext()) {
      int i = rand.nextInt(upperBound);
      swap(i, upperBound - 1);
      return array[--upperBound];
    }
    throw new NoSuchElementException();
  }

  @SuppressWarnings("unchecked")
  public static <E> RandomIterator<E> newInstance(Random rand, Collection<E> collection) {
    return new RandomIterator<>(rand, (E[]) collection.toArray());
  }

  public static <E> RandomIterator<E> newInstance(Random rand, E[] array) {
    return new RandomIterator<>(rand, array);
  }
}
