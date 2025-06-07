import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

public class MyHashMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private int tableCapacity = DEFAULT_INITIAL_CAPACITY;
    private int size = 0;
    private boolean resizeInProgress;
    private LinkedList<Node<K, V>>[] table;
    private int threshold = (int) (tableCapacity * LOAD_FACTOR);

    public MyHashMap() {
        table = new LinkedList[tableCapacity];
    }

    public int size() {
        return size;
    }

    public void resize() {
        resizeInProgress = true;
        tableCapacity = tableCapacity * 2;
        LinkedList<Node<K, V>>[] oldTable = table;
        LinkedList<Node<K, V>>[] newTable = new LinkedList[tableCapacity];
        table = newTable;

        for (int i = 0; i < oldTable.length; i++) {
            LinkedList<Node<K, V>> bucket = oldTable[i];
            if (bucket == null) {
                continue;
            }

            for (Node<K, V> node : bucket) {
                put(node.getKey(), node.getValue());
            }
        }
        resizeInProgress = false;
        threshold = (int) (tableCapacity * LOAD_FACTOR);
    }

    public void put(K key, V value) {
        int hash = computeHash(key);
        int index = getIndex(key);

        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        LinkedList<Node<K, V>> bucket = table[index];

        for (Node<K, V> node : bucket) {
            if (node.hash != hash)
                continue;

            if (Objects.equals(node.getKey(), key)) {
                node.setValue(value);
                return;
            }
        }

        bucket.add(new Node<>(key, value));

        if (!resizeInProgress) {
            size++;
        }

        if (!resizeInProgress && size > threshold) {
            resize();
        }
    }

    public V get(K key) {
        int hash = computeHash(key);
        int index = getIndex(key);

        LinkedList<Node<K, V>> bucket = table[index];
        if (bucket == null) {
            return null;
        }

        for (Node<K, V> node : bucket) {
            if (node.hash != hash) continue;

            if (Objects.equals(node.getKey(), key)) {

                return node.getValue();
            }
        }
        return null;
    }

    public void remove(K key) {
        int hash = computeHash(key);
        int index = getIndex(key);

        LinkedList<Node<K, V>> bucket = table[index];
        if (bucket == null) {
            return;
        }

        Iterator<Node<K, V>> iterator = bucket.iterator();

        while (iterator.hasNext()) {
            Node<K, V> node = iterator.next();

            if (node.hash != hash) continue;

            if (Objects.equals(node.getKey(), key)) {
                iterator.remove();
                size--;
                if (bucket.isEmpty()) {
                    table[index] = null;
                }
                return;
            }
        }
    }

    public int getIndex(K key) {
        int hash = computeHash(key);
        if (hash < 0) hash = -hash;
        return hash % table.length;
    }

    private int computeHash(K key) {
        return (key == null) ? 0 : key.hashCode();
    }

    @Override
    public String toString() {
        return "MyHashMap{" + "table=" + Arrays.toString(table) + '}';
    }
}
