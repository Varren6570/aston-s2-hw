import java.util.Objects;

public class Node<K, V> {
    final int hash;
    final K key;
    V value;

    public Node(K key, V value) {
        this.hash = (key == null) ? 0 : key.hashCode();
        this.value = value;
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node<?, ?> other)) return false;
        return Objects.equals(this.key, other.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    @Override
    public final String toString() {
        return key + "=" + value;
    }
}
