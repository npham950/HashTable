/**
 * Created by nypham on 6/5/17.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HashTable<K, V> {
    private ArrayList <HashNode<K,V>> bucketArray;
    private int size;
    private int numofBucket;

    public HashTable() {
        this.bucketArray = new ArrayList<>();
        this.size = 0;
        this.numofBucket = 10;

        // create an empty chain
        for (int index = 0; index < this.numofBucket; index++) {
            this.bucketArray.add(null);
        }
    }

    public int getSize() {
        return this.size;
    }

    private int hashFunction(K key) {
        int hashCode = key.hashCode();
        int index = Math.abs(hashCode % this.numofBucket);
        return index;
    }

    public V remove(K key) {
        int index = hashFunction(key);
        HashNode<K, V> head = this.bucketArray.get(index);
        HashNode<K, V> prev = null;
        while (head != null) {
            if (head.getKey().equals(key)){
                break;
            }
            prev = head;
            head = head.getNext();
        }
        if (head == null) {
            return null;
        }
        this.size--;
        if (prev != null) {
            prev.setNext(head.getNext());
        }else {
            bucketArray.set(index, head.getNext());
        }
        return head.getValue();
    }

    public V get(K key) {
        int index = hashFunction(key);
        HashNode<K, V> head = this.bucketArray.get(index);
        while (head != null) {
            if (head.getKey().equals(key)) {
                return head.getValue();
            }
            head = head.getNext();
        }
        return null;
    }

    public void add(K key, V value) {
        this.size++;
        int index = hashFunction(key);
        HashNode<K, V> head = bucketArray.get(index);
        while (head != null) {
            if (head.getKey().equals(key)){
                head.setValue(value);
                return;
            }
            head = head.getNext();
        }
        head = bucketArray.get(index);
        HashNode <K, V> newNode = new HashNode<>(key, value);
        newNode.setNext(head);
        head = newNode;
        bucketArray.set(index, head);
        if ((1.0*this.size)/this.numofBucket >= 0.75){
            reHash();
        }
    }

    private void reHash() {
        int i = 0;
        ArrayList <HashNode<K, V>> newBucket = new ArrayList<>();
        this.numofBucket = 2 * this.numofBucket;
        for (int index = 0; index < this.numofBucket; index++) {
            newBucket.add(null);
        }
        for (HashNode<K, V> headNode : this.bucketArray) {
            newBucket.set(i, headNode);
            i++;
        }
        this.bucketArray = newBucket;
    }

    public String toString() {
        String s = "";
        HashNode <K, V> head;
        for (int i = 0; i < this.numofBucket; i++) {
            head = this.bucketArray.get(i);
            while (head != null) {
                s += head.getKey() + ": " + head.getValue() + "; ";
                head = head.getNext();
            }
            s += " || ";
        }
        return s;
    }

    public static void main(String[] args) {
        HashTable<String, Integer> map = new HashTable<>();
        map.add("zero", 0);
        map.add("one", 1);
        map.add("two", 2);
        map.add("three", 3);
        map.add("four", 4);
        map.add("five", 5);
        map.add("six", 6);
        map.add("seven", 7);
        map.add("kjhkjhkjgg", 0);
        System.out.println(map.getSize());
        System.out.println(map.toString());
    }

}
