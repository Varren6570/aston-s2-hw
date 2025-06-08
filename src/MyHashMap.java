import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Реализация хэш-карты (HashMap), основанная на массиве LinkedList'ов.
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public class MyHashMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16; //Начальная ёмкость таблицы по умолчанию
    private static final float LOAD_FACTOR = 0.75f; //Коэффициент загрузки таблицы

    private int tableCapacity = DEFAULT_INITIAL_CAPACITY; //Текущая ёмкость таблицы
    private int size = 0; //Количество пар ключ-значение в карте
    private int threshold = (int) (tableCapacity * LOAD_FACTOR); //Порог, при превышении которого происходит увеличение ёмкости
    private boolean resizeInProgress; //Флаг, указывающий на то, что в данный момент выполняется операция расширения таблицы

    private LinkedList<Node<K, V>>[] table; //Хэш-таблица, реализованная как массив списков

    public MyHashMap() {
        table = new LinkedList[tableCapacity];
    } //Конструктор без параметров. Создаёт пустую хэш-карту

    public int size() {
        return size;
    }

    /**
     * Расширяет таблицу вдвое и перераспределяет все элементы по новой структуре.
     */
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

    /**
     * Добавляет или обновляет значение по заданному ключу.
     *
     * @param key   ключ
     * @param value значение, которое необходимо связать с ключом
     */
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

    /**
     * Возвращает значение, связанное с указанным ключом, либо {@code null}, если ключ не найден.
     *
     * @param key ключ, значение по которому требуется получить
     * @return значение, связанное с ключом, или {@code null}, если ключ отсутствует
     */
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

    /**
     * Удаляет пару ключ-значение по заданному ключу.
     *
     * @param key ключ, по которому требуется удалить элемент
     */
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

    /**
     * Возвращает индекс в массиве по хэшу ключа.
     *
     * @param key ключ
     */
    public int getIndex(K key) {
        int hash = computeHash(key);
        if (hash < 0) hash = -hash;
        return hash % table.length;
    }

    /**
     * Возвращает хэш для указанного ключа.
     *
     * @param key ключ
     */
    private int computeHash(K key) {
        return (key == null) ? 0 : key.hashCode();
    }

    /**
     * Возвращает строковое представление текущего состояния таблицы.
     *
     */
    @Override
    public String toString() {
        return "MyHashMap{" + "table=" + Arrays.toString(table) + '}';
    }
}
