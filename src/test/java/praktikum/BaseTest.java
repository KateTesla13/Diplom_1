package praktikum;

import org.junit.After;
import org.junit.Before;

public class BaseTest {

    @Before
    public void setUp() {
        // Здесь будут общие настройки для всех тестов
        System.out.println("🌱 Настройка перед тестом");
    }

    @After
    public void tearDown() {
        // Здесь будет общая очистка после каждого теста
        System.out.println("🧹 Очистка после теста");
    }
}
