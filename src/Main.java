//Добавлен класс Main, содержащий метод main для запуска приложения.
public class Main {
    public static void main(String[] args) {
        //Создан объект userRepository типа UserRepositoryImpl для работы с данными пользователей.
        UserRepository userRepository = new UserRepositoryImpl();
        //Создан объект userView типа UserViewImpl для отображения данных пользователей.
        UserView userView = new UserViewImpl();
        //Создан объект userPresenter типа UserPresenter, который объединяет представление и хранилище данных пользователей.
        UserPresenter userPresenter = new UserPresenter(userView, userRepository);
    }
}
