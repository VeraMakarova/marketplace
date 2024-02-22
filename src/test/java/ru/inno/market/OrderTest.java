package ru.inno.market;

import org.junit.jupiter.api.*;
import ru.inno.market.model.Category;
import ru.inno.market.model.Client;
import ru.inno.market.model.Item;
import ru.inno.market.model.Order;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {


    private Client Marta;
    private Item someSmartphone;
    private Order MartaOrder;

    @BeforeEach
    public void setUp() {
        Marta = new Client(1, "Marta");
        someSmartphone = new Item(18, "Samsung Galaxy", Category.SMARTPHONES, 100.00); // создаем новый товар
        MartaOrder = new Order(1, Marta); // Создаем заказ тестового юзера Марты
    }

    @AfterEach
    public void tearDown() {
        MartaOrder = new Order(1, Marta);//перезаписываем заказ после теста - меняем тот, что был на пустой заказ
    }

    @Test
    @DisplayName("Добавление товара в заказ увеличивает количество товаров на 1")
    @Tag("ДобавлениеТовара")
    public void addItemTest() throws IOException {
        Map MartaCart = MartaOrder.getCart();//1.  мапа - список покупок Марты <товар, количество>
        int sizeBefore = MartaCart.size();//2. проверяем размер мапы
        MartaOrder.addItem(someSmartphone);//3. добавляем товар в заказ
        int sizeAfter = MartaCart.size();//4. проверяем размер мапы после заказа
        assertEquals(1, sizeAfter - sizeBefore); //5. сравниваем
        //6. Удаляем товар из мапы - вынесено в tearDown
    }

    @Test
    @DisplayName("Добавление товара в заказ увеличивает сумму заказа на стоимость товара")
    @Tag("ДобавлениеТовара")
    public void addItemTest2() throws IOException {
        Map MartaCart = MartaOrder.getCart();//1.  мапа - список покупок Марты <товар, количество>
        double totalPriceBefore = MartaOrder.getTotalPrice(); //2. проверяем стоимость заказа
        MartaOrder.addItem(someSmartphone);//3. добавляем товар в заказ
        double totalPriceAfter = MartaOrder.getTotalPrice(); //4. проверяем стоимость заказа после добавления товара
        assertEquals(someSmartphone.getPrice(), totalPriceAfter - totalPriceBefore); //5. сравниваем
        //6. Удаляем товар из мапы - вынесено в tearDown
    }


    @Test
    @DisplayName("Добавление нескольких товаров в заказ, корректное увеличение суммы заказа")
    @Tag("ДобавлениеТовара")
    public void addItemTest3() throws IOException {
        Map MartaCart = MartaOrder.getCart();//1.  мапа - список покупок Марты <товар, количество>
        double totalPriceBefore = MartaOrder.getTotalPrice(); //2. проверяем стоимость заказа
        MartaOrder.addItem(someSmartphone);//3. добавляем товар в заказ
        MartaOrder.addItem(someSmartphone);//
        MartaOrder.addItem(someSmartphone);//
        double totalPriceAfter = MartaOrder.getTotalPrice(); //4. проверяем стоимость заказа после добавления товаров
        assertEquals(someSmartphone.getPrice()*3, totalPriceAfter - totalPriceBefore); //5. сравниваем
        //6. Удаляем товар из мапы - вынесено в tearDown
    }


    @Test
    @DisplayName("Добавление нескольких разных товаров в заказ, корректное увеличение суммы заказа")
    @Tag("ДобавлениеТовара")
    public void addItemTest4() throws IOException {
        Item someOtherItem = new Item(67, "MacBook M3", Category.LAPTOPS, 200000); // создаем новый товар

        Map MartaCart = MartaOrder.getCart();//1.  мапа - список покупок Марты <товар, количество>
        double totalPriceBefore = MartaOrder.getTotalPrice(); //2. проверяем стоимость заказа
        MartaOrder.addItem(someSmartphone);//3. добавляем товар в заказ
        MartaOrder.addItem(someOtherItem);//
        double totalPriceAfter = MartaOrder.getTotalPrice(); //4. проверяем стоимость заказа после добавления товаров
        assertEquals(someSmartphone.getPrice()+someOtherItem.getPrice(), totalPriceAfter - totalPriceBefore); //5. сравниваем
        //6. Удаляем товар из мапы - вынесено в tearDown
    }


    @Test
    @DisplayName("Применение корректной скидки отображается в заказе")
    @Tag("Скидка")
    public void applyDiscountTest1() throws IOException {
        MartaOrder.applyDiscount(10);
        assertTrue(MartaOrder.isDiscountApplied());
    }

    @Test
    @DisplayName("Применение корректной скидки корректно уменьшает сумму заказа")
    @Tag("Скидка")
    public void applyDiscountTest2() throws IOException {
        double discount = 10;
        MartaOrder.addItem(someSmartphone);//3. добавляем товар в заказ
        double totalPriceBefore = MartaOrder.getTotalPrice(); //2. проверяем стоимость заказа
        MartaOrder.applyDiscount(discount);
        double totalPriceAfter = MartaOrder.getTotalPrice(); //2. проверяем стоимость заказа
        assertEquals(discount, (totalPriceBefore - totalPriceAfter));
    }

    @Test
    @DisplayName("Применение корректной скидки несколько раз. Учитывается только первая скидка")
    @Tag("Скидка")
    public void applyDiscountTest3() throws IOException {
        double discount = 10;
        double discount2 = 20;
        MartaOrder.addItem(someSmartphone);
        double totalPriceBefore = MartaOrder.getTotalPrice();
        MartaOrder.applyDiscount(discount);
        MartaOrder.applyDiscount(discount2);
        double totalPriceAfter = MartaOrder.getTotalPrice();
        assertEquals(discount, (totalPriceBefore - totalPriceAfter));
    }

    @Test
    @DisplayName("Применение некорректной скидки невозможно")
    @Tag("Скидка")
    public void applyDiscountTest4() throws IOException {
        double discount = -10;
        MartaOrder.addItem(someSmartphone);
        double totalPriceBefore = MartaOrder.getTotalPrice();
        MartaOrder.applyDiscount(discount);
        double totalPriceAfter = MartaOrder.getTotalPrice();
        assertFalse(MartaOrder.isDiscountApplied());
    }


}
