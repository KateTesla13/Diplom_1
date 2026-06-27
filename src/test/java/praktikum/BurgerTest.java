package praktikum;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun bunMock;

    @Mock
    private Ingredient ingredientMock1;

    @Mock
    private Ingredient ingredientMock2;

    @Mock
    private Ingredient ingredientMock3;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
    }

    @Test
    public void setBunsShouldSetBunCorrectly() {
        burger.setBuns(bunMock);
        assertEquals(bunMock, burger.bun);
    }

    @Test
    public void addIngredientShouldAddToList() {
        burger.addIngredient(ingredientMock1);
        assertEquals(1, burger.ingredients.size());
        assertEquals(ingredientMock1, burger.ingredients.get(0));
    }

    @Test
    public void addMultipleIngredientsShouldAddAll() {
        burger.addIngredient(ingredientMock1);
        burger.addIngredient(ingredientMock2);
        burger.addIngredient(ingredientMock3);
        assertEquals(3, burger.ingredients.size());
    }

    @Test
    public void removeIngredientShouldRemoveByIndex() {
        burger.addIngredient(ingredientMock1);
        burger.addIngredient(ingredientMock2);
        burger.addIngredient(ingredientMock3);
        burger.removeIngredient(1);
        assertEquals(2, burger.ingredients.size());
        assertEquals(ingredientMock1, burger.ingredients.get(0));
        assertEquals(ingredientMock3, burger.ingredients.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeIngredientWithInvalidIndexShouldThrowException() {
        burger.removeIngredient(999);
    }

    @Test
    public void moveIngredientShouldMoveToNewPosition() {
        when(ingredientMock1.getName()).thenReturn("соус");
        when(ingredientMock2.getName()).thenReturn("котлета");
        when(ingredientMock3.getName()).thenReturn("сосиска");

        burger.addIngredient(ingredientMock1);
        burger.addIngredient(ingredientMock2);
        burger.addIngredient(ingredientMock3);
        burger.moveIngredient(0, 2);
        assertEquals(ingredientMock2, burger.ingredients.get(0));
        assertEquals(ingredientMock3, burger.ingredients.get(1));
        assertEquals(ingredientMock1, burger.ingredients.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void moveIngredientWithInvalidIndexShouldThrowException() {
        burger.addIngredient(ingredientMock1);
        burger.moveIngredient(999, 0);
    }

    @Test(expected = NullPointerException.class)
    public void getPriceWithoutBunShouldThrowNullPointerException() {
        burger.getPrice();
    }

    @Test
    @Parameters(source = BurgerTestData.class, method = "getPriceData")
    @TestCaseName("[{index}] Цена бургера: булка={0}, соус={1}, котлета={2}, сосиска={3} → {4}")
    public void getPriceShouldCalculateCorrectlyWithDifferentValues(
            int bunPrice,
            int ingredientPrice1,
            int ingredientPrice2,
            int ingredientPrice3,
            int expected) {

        System.out.println("💰 Цена бургера: булка=" + bunPrice + ", соус=" + ingredientPrice1 +
                ", котлета=" + ingredientPrice2 + ", сосиска=" + ingredientPrice3 + " → " + expected);

        when(bunMock.getPrice()).thenReturn((float) bunPrice);
        when(ingredientMock1.getPrice()).thenReturn((float) ingredientPrice1);
        when(ingredientMock2.getPrice()).thenReturn((float) ingredientPrice2);
        when(ingredientMock3.getPrice()).thenReturn((float) ingredientPrice3);

        burger.setBuns(bunMock);
        burger.addIngredient(ingredientMock1);
        burger.addIngredient(ingredientMock2);
        burger.addIngredient(ingredientMock3);

        float actual = burger.getPrice();
        assertEquals(expected, actual, 0.001);
    }

    @Test
    @Parameters(source = BurgerTestData.class, method = "getPriceWithoutIngredientsData")
    @TestCaseName("[{index}] Цена без ингредиентов: булка={0} → {1}")
    public void getPriceWithNoIngredientsShouldReturnBunPriceOnly(
            int bunPrice,
            int expected) {

        System.out.println("🍞 Цена без ингредиентов: булка=" + bunPrice + " → " + expected);

        when(bunMock.getPrice()).thenReturn((float) bunPrice);
        burger.setBuns(bunMock);

        float actual = burger.getPrice();
        assertEquals(expected, actual, 0.001);
    }

    @Test
    @Parameters(source = BurgerTestData.class, method = "getRemoveIngredientData")
    @TestCaseName("[{index}] Удаление ингредиента: индекс {0} → размер {1}")
    public void removeIngredientShouldDecreaseSize(int removeIndex, int expectedSize) {
        System.out.println("🗑️ Удаление ингредиента: индекс " + removeIndex + " → размер " + expectedSize);

        burger.addIngredient(ingredientMock1);
        burger.addIngredient(ingredientMock2);
        burger.addIngredient(ingredientMock3);

        burger.removeIngredient(removeIndex);
        assertEquals(expectedSize, burger.ingredients.size());
    }

    @Test
    @Parameters(source = BurgerTestData.class, method = "getMoveIngredientData")
    @TestCaseName("[{index}] Перемещение: с {0} на {1} → первый '{2}'")
    public void moveIngredientShouldChangeOrder(
            int fromIndex,
            int toIndex,
            String expectedFirstName) {

        System.out.println("🔄 Перемещение: с " + fromIndex + " на " + toIndex + " → первый '" + expectedFirstName + "'");

        when(ingredientMock1.getName()).thenReturn("соус");
        when(ingredientMock2.getName()).thenReturn("котлета");
        when(ingredientMock3.getName()).thenReturn("сосиска");

        burger.addIngredient(ingredientMock1);
        burger.addIngredient(ingredientMock2);
        burger.addIngredient(ingredientMock3);

        burger.moveIngredient(fromIndex, toIndex);
        assertEquals(expectedFirstName, burger.ingredients.get(0).getName());
    }

    @Test
    @Parameters(source = BurgerTestData.class, method = "getReceiptData")
    @TestCaseName("[{index}] Чек: булка '{0}', ингредиент '{1}'")
    public void getReceiptShouldContainCorrectNames(
            String bunName,
            String ingredientName,
            String ingredientType) {

        System.out.println("🧾 Чек: булка '" + bunName + "', ингредиент '" + ingredientName + "'");

        when(bunMock.getName()).thenReturn(bunName);
        when(bunMock.getPrice()).thenReturn(100f);

        if (ingredientType.equals("SAUCE")) {
            when(ingredientMock1.getType()).thenReturn(IngredientType.SAUCE);
        } else {
            when(ingredientMock1.getType()).thenReturn(IngredientType.FILLING);
        }
        when(ingredientMock1.getName()).thenReturn(ingredientName);
        when(ingredientMock1.getPrice()).thenReturn(100f);

        burger.setBuns(bunMock);
        burger.addIngredient(ingredientMock1);

        String receipt = burger.getReceipt();
        assertTrue(receipt.contains(bunName));
        assertTrue(receipt.contains(ingredientName));
    }
}