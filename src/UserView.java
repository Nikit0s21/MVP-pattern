import java.util.List;
// Добавлен интерфейс UserView
public interface UserView {
    // Добавлен метод showUserList в интерфейс UserView
    void showUserList(List<User> userList);
    // Добавлен метод showErrorMessage в интерфейс UserView
    void showErrorMessage(String errorMessage);
    // Добавлен метод showSuccessMessage в интерфейс UserView
    void showSuccessMessage(String message);
}
