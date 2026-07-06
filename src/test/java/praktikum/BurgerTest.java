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
    private Ingredient sauceMock;

    @Mock
    private Ingredient cutletMock;

    @Mock
    private Ingredient sausageMock;

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
    public void addIngredientShouldIncreaseSize() {
        burger.addIngredient(sauceMock);
        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void addIngredientShouldAddCorrectIngredient() {
        burger.addIngredient(sauceMock);
        assertEquals(sauceMock, burger.ingredients.get(0));
    }

    @Test
    public void addMultipleIngredientsShouldAddAll() {
        burger.addIngredient(sauceMock);
        burger.addIngredient(cutletMock);
        burger.addIngredient(sausageMock);
        assertEquals(3, burger.ingredients.size());
    }

    @Test
    public void removeIngredientShouldDecreaseSize() {
        burger.addIngredient(sauceMock);
        burger.addIngredient(cutletMock);
        burger.addIngredient(sausageMock);
        burger.removeIngredient(1);
        assertEquals(2, burger.ingredients.size());
    }

    @Test
    public void removeIngredientShouldKeepFirstElement() {
        burger.addIngredient(sauceMock);
        burger.addIngredient(cutletMock);
        burger.addIngredient(sausageMock);
        burger.removeIngredient(1);
        assertEquals(sauceMock, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientShouldKeepLastElement() {
        burger.addIngredient(sauceMock);
        burger.addIngredient(cutletMock);
        burger.addIngredient(sausageMock);
        burger.removeIngredient(1);
        assertEquals(sausageMock, burger.ingredients.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeIngredientWithInvalidIndexShouldThrowException() {
        burger.removeIngredient(999);
    }

    @Test
    public void moveIngredientShouldSetFirstElementCorrectly() {
        when(sauceMock.getName()).thenReturn("соус");
        when(cutletMock.getName()).thenReturn("котлета");
        when(sausageMock.getName()).thenReturn("сосиска");

        burger.addIngredient(sauceMock);
        burger.addIngredient(cutletMock);
        burger.addIngredient(sausageMock);
        burger.moveIngredient(0, 2);
        assertEquals(cutletMock, burger.ingredients.get(0));
    }

    @Test
    public void moveIngredientShouldSetSecondElementCorrectly() {
        when(sauceMock.getName()).thenReturn("соус");
        when(cutletMock.getName()).thenReturn("котлета");
        when(sausageMock.getName()).thenReturn("сосиска");

        burger.addIngredient(sauceMock);
        burger.addIngredient(cutletMock);
        burger.addIngredient(sausageMock);
        burger.moveIngredient(0, 2);
        assertEquals(sausageMock, burger.ingredients.get(1));
    }

    @Test
    public void moveIngredientShouldSetThirdElementCorrectly() {
        when(sauceMock.getName()).thenReturn("соус");
        when(cutletMock.getName()).thenReturn("котлета");
        when(sausageMock.getName()).thenReturn("сосиска");

        burger.addIngredient(sauceMock);
        burger.addIngredient(cutletMock);
        burger.addIngredient(sausageMock);
        burger.moveIngredient(0, 2);
        assertEquals(sauceMock, burger.ingredients.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void moveIngredientWithInvalidIndexShouldThrowException() {
        burger.addIngredient(sauceMock);
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
            int saucePrice,
            int cutletPrice,
            int sausagePrice,
            int expected) {

        System.out.println("💰 Цена бургера: булка=" + bunPrice + ", соус=" + saucePrice +
                ", котлета=" + cutletPrice + ", сосиска=" + sausagePrice + " → " + expected);

        when(bunMock.getPrice()).thenReturn((float) bunPrice);
        when(sauceMock.getPrice()).thenReturn((float) saucePrice);
        when(cutletMock.getPrice()).thenReturn((float) cutletPrice);
        when(sausageMock.getPrice()).thenReturn((float) sausagePrice);

        burger.setBuns(bunMock);
        burger.addIngredient(sauceMock);
        burger.addIngredient(cutletMock);
        burger.addIngredient(sausageMock);

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
    public void removeIngredientShouldDecreaseSizeParametrized(int removeIndex, int expectedSize) {
        System.out.println("🗑️ Удаление ингредиента: индекс " + removeIndex + " → размер " + expectedSize);

        burger.addIngredient(sauceMock);
        burger.addIngredient(cutletMock);
        burger.addIngredient(sausageMock);

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

        when(sauceMock.getName()).thenReturn("соус");
        when(cutletMock.getName()).thenReturn("котлета");
        when(sausageMock.getName()).thenReturn("сосиска");

        burger.addIngredient(sauceMock);
        burger.addIngredient(cutletMock);
        burger.addIngredient(sausageMock);

        burger.moveIngredient(fromIndex, toIndex);
        assertEquals(expectedFirstName, burger.ingredients.get(0).getName());
    }

    @Test
    @Parameters(source = BurgerTestData.class, method = "getReceiptData")
    @TestCaseName("[{index}] Чек: булка '{0}', ингредиент '{1}'")
    public void getReceiptShouldContainBunName(
            String bunName,
            String ingredientName,
            String ingredientType) {

        System.out.println("🧾 Чек: булка '" + bunName + "', ингредиент '" + ingredientName + "'");

        when(bunMock.getName()).thenReturn(bunName);
        when(bunMock.getPrice()).thenReturn(100f);

        if (ingredientType.equals("SAUCE")) {
            when(sauceMock.getType()).thenReturn(IngredientType.SAUCE);
        } else {
            when(sauceMock.getType()).thenReturn(IngredientType.FILLING);
        }
        when(sauceMock.getName()).thenReturn(ingredientName);
        when(sauceMock.getPrice()).thenReturn(100f);

        burger.setBuns(bunMock);
        burger.addIngredient(sauceMock);

        String receipt = burger.getReceipt();
        assertTrue(receipt.contains(bunName));
    }

    @Test
    @Parameters(source = BurgerTestData.class, method = "getReceiptData")
    @TestCaseName("[{index}] Чек: булка '{0}', ингредиент '{1}'")
    public void getReceiptShouldContainIngredientName(
            String bunName,
            String ingredientName,
            String ingredientType) {

        System.out.println("🧾 Чек: булка '" + bunName + "', ингредиент '" + ingredientName + "'");

        when(bunMock.getName()).thenReturn(bunName);
        when(bunMock.getPrice()).thenReturn(100f);

        if (ingredientType.equals("SAUCE")) {
            when(sauceMock.getType()).thenReturn(IngredientType.SAUCE);
        } else {
            when(sauceMock.getType()).thenReturn(IngredientType.FILLING);
        }
        when(sauceMock.getName()).thenReturn(ingredientName);
        when(sauceMock.getPrice()).thenReturn(100f);

        burger.setBuns(bunMock);
        burger.addIngredient(sauceMock);

        String receipt = burger.getReceipt();
        assertTrue(receipt.contains(ingredientName));
    }
}