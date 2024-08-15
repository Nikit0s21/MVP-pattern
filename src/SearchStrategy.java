// Добавлен интерфейс для поиска.
public interface SearchStrategy {
    //Интерфейс содержит один метод test, который принимает объект типа User и возвращает логическое значение.
    boolean test(User user);
}
