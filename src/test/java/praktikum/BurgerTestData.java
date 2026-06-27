package praktikum;

public class BurgerTestData {

    // Константы для тестов

    public static final String BUN_NAME = "black bun";
    public static final float BUN_PRICE = 100f;
    public static final String SAUCE_NAME = "hot sauce";
    public static final float SAUCE_PRICE = 100f;
    public static final String FILLING_NAME = "cutlet";
    public static final float FILLING_PRICE = 100f;
    public static final String SAUSAGE_NAME = "sausage";
    public static final float SAUSAGE_PRICE = 300f;

    // Данные для параметризации

    public static Object[][] getPriceData() {
        return new Object[][]{
                {100, 100, 100, 300, 700},
                {200, 100, 100, 300, 900},
                {300, 100, 100, 300, 1100}
        };
    }

    public static Object[][] getPriceWithoutIngredientsData() {
        return new Object[][]{
                {100, 200},
                {200, 400},
                {300, 600}
        };
    }

    public static Object[][] getRemoveIngredientData() {
        return new Object[][]{
                {0, 2},
                {1, 2},
                {2, 2}
        };
    }

    public static Object[][] getMoveIngredientData() {
        return new Object[][]{
                {0, 2, "котлета"},
                {1, 0, "котлета"},
                {2, 0, "сосиска"}
        };
    }

    public static Object[][] getReceiptData() {
        return new Object[][]{
                {"чёрная булка", "острый соус", "SAUCE"},
                {"белая булка", "сметана", "SAUCE"},
                {"красная булка", "котлета", "FILLING"}
        };
    }
}