import java.util.List;
// Добавлен класс UserPresenter для обработки пользовательского интерфейса и связи с хранилищем данных
public class UserPresenter {
    private UserView userView;
    private UserRepository userRepository;
    // Создан конструктор класса UserPresenter для инициализации объектов UserView и UserRepository
    public UserPresenter(UserView userView, UserRepository userRepository) {
        this.userView = userView;
        this.userRepository = userRepository;
        userView.showUserList(userRepository.getUserList());
    }
    // Добавлен метод addUser() для добавления нового пользователя в хранилище данных и обновления списка пользователей в пользовательском интерфейсе
    public void addUser(String name, int age, String email) {
        User user = new User(name, age, email);
        userRepository.addUser(user);
        userView.showUserList(userRepository.getUserList());
    }
    // Добавлен метод deleteUser() для удаления пользователя из хранилища данных и обновления списка пользователей в пользовательском интерфейсе
    public void deleteUser(String userName) {
        userRepository.deleteUser(userName);
        userView.showUserList(userRepository.getUserList());
    }
    // Добавлен метод sortUserList() для сортировки списка пользователей с помощью переданной стратегии сортировки и обновления списка пользователей в пользовательском интерфейсе
    public void sortUserList(SortStrategy strategy) {
        List<User> userList = userRepository.sortUserList(strategy);
        userView.showUserList(userList);
    }
    // Добавлен метод searchUserList() для поиска пользователей в списке с помощью переданной стратегии поиска и обновления списка пользователей в пользовательском интерфейсе
    public List<User> searchUserList(SearchStrategy strategy) {
        List<User> userList = userRepository.searchUserList(strategy);
        userView.showUserList(userList);
        return userList;
    }
    // Добавлен метод showUserList() для отображения списка пользователей в пользовательском интерфейсе
    public void showUserList() {
        List<User> userList = userRepository.getUserList();
        userView.showUserList(userList);
    }
    // Добавлен перегруженный метод showUserList() для отображения переданного списка пользователей в пользовательском интерфейсе
    public void showUserList(List<User> userList) {
        userList = userRepository.getUserList();
        userView.showUserList(userList);
    }
}
