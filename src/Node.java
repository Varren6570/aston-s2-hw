import java.util.Objects;

/**
 * Узел, представляющий пару ключ-значение внутри хэш-таблицы.
 * Используется в реализации {@code MyHashMap}.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class Node<K, V> {
    final int hash; //Предварительно вычисленный хэш ключа
        final K key;
        V value;

    /**
     * Создаёт новый узел с указанными ключом и значением.
     * Хэш вычисляется на момент создания и сохраняется для ускорения операций.
     *
     * @param key   ключ
     * @param value значение
     */
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

    /**
     * Сравнивает два узла по ключу.
     *
     * @return {@code true}, если ключи равны; {@code false} в противном случае
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node<?, ?> other)) return false;
        return Objects.equals(this.key, other.key);
    }

    /**
     * Вычисляет хэш-код на основе ключа.
     *
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    /**
     * Возвращает строковое представление узла в формате "key=value".
     *
     */
    @Override
    public final String toString() {
        return key + "=" + value;
    }
}
