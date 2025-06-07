

public class MyHashMapDemo {
    public static void main(String[] args) {
        // Создание HashMap
        MyHashMap<String, Integer> studentScores = new MyHashMap<>();

        // Добавление пар ключ-значение
        studentScores.put("Alice", 85);
        studentScores.put("Bob", 92);
        studentScores.put("Charlie", 78);

        System.out.println(studentScores);

        // Получение значения по ключу
        System.out.println(studentScores.get("Bob"));

        // Удаление элемента
        studentScores.remove("Charlie");
        System.out.println(studentScores);

        // Размер карты
        System.out.println("size = " + studentScores.size());

        //Проверка расширяемости и разрешения коллизий
        studentScores.put("Charlie", 718);
        studentScores.put("1Charlie", 728);
        studentScores.put("2Charlie", 738);
        studentScores.put("3Charlie", 748);
        studentScores.put("4Charlie", 758);
        studentScores.put("5Charlie", 768);
        studentScores.put("6Charlie", 778);
        studentScores.put("7Charlie", 788);
        studentScores.put("8Charlie", 798);
        studentScores.put("9Charlie", 708);
        studentScores.put("10Charlie", 7128);

        System.out.println(studentScores);

    }
}

