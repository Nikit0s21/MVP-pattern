//Создание класса User
public class User {
    // Создание приватных полей name, age и email
    private String name;
    private int age;
    private String email;
    // Создание конструктора класса User с параметрами name, age и email
    public User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
    // Создание геттеров для полей name, age и email
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    // Создание метода toString() для отладки и возвращения строкового представления объекта User
    public String toString() {
        return name + ", " + age + ", " + email;
    }
}
