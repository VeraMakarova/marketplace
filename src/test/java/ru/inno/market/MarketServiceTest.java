package ru.inno.market;

import org.junit.jupiter.api.*;
import ru.inno.market.core.MarketService;
import ru.inno.market.model.*;
import ru.inno.market.model.Order;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarketServiceTest {

    private Client Jack;
    private Item someLaptope;
    private Item someSmartphone;
    private MarketService marketService;

    @BeforeEach
    public void setUp() {
        marketService = new MarketService();
        Jack = new Client(8, "Jack");
        someLaptope = new Item(30, "NewLaptop", Category.LAPTOPS, 100.00); // создаем новый товар
        someSmartphone = new Item(18, "Samsung Galaxy", Category.SMARTPHONES, 100.00); // создаем новый товар

    }


    @Test
    @DisplayName("При создании нового заказа ID заказа увеличивается на 1")
    @Tag("СозданиеЗаказа")
    public void createOrderForTest1() throws IOException {
        int orderID = marketService.createOrderFor(Jack);
        int orderID2 = marketService.createOrderFor(Jack);
        assertEquals(1, orderID2 - orderID);
    }

    @Test
    @DisplayName("При добавлении товара в заказ в нем оказывается именно этот товар")
    @Tag("ДобавлениеТовара")
    public void addItemToOrderTest1() throws IOException {
        int orderID = marketService.createOrderFor(Jack);
        marketService.addItemToOrder(someLaptope, orderID);
        assertTrue(marketService.getOrderInfo(orderID).getItems().containsKey(someLaptope)); // сравниваем

    }

    @Test
    @DisplayName("Применение корректной скидки корректно уменьшает сумму заказа")
    @Tag("Скидка")
    public void applyDiscountForOrderTest1() throws IOException {
        int orderID = marketService.createOrderFor(Jack);
        marketService.addItemToOrder(someLaptope, orderID);
        double x = marketService.applyDiscountForOrder(orderID, PromoCodes.HAPPY_NEW_YEAR);
        double totalPriceAfter = marketService.getOrderInfo(0).getTotalPrice();
        assertEquals(x, totalPriceAfter);

    }

    @Test
    @DisplayName("Два товара, две скидки. Учитывается только первая скидка на весь заказ")
    @Tag("скидка")
    public void applyDiscountForOrderTest3() throws IOException {

        int orderID = marketService.createOrderFor(Jack);
        marketService.addItemToOrder(someLaptope, orderID);
        marketService.addItemToOrder(someSmartphone, orderID);
        double totalPriceFirstDiscount = marketService.applyDiscountForOrder(orderID, PromoCodes.HAPPY_HOUR);
        double totalPriceSecondDiscount = marketService.applyDiscountForOrder(orderID, PromoCodes.FIRST_ORDER);
        assertEquals(totalPriceFirstDiscount, totalPriceSecondDiscount);

    }

    @Test
    @DisplayName("Цена товара в заказе равна сумме заказа")
    @Tag("info")
    public void getOrderInfoTest1() throws IOException {

        int orderID = marketService.createOrderFor(Jack);
        marketService.addItemToOrder(someLaptope, orderID);
        assertEquals(someLaptope.getPrice(), marketService.getOrderInfo(orderID).getTotalPrice());

    }

    @Test
    @DisplayName("В заказе указано правильное количество товаров")
    @Tag("info")
    public void getOrderInfoTest2() throws IOException {

        int orderID = marketService.createOrderFor(Jack);
        marketService.addItemToOrder(someLaptope, orderID);
        marketService.addItemToOrder(someLaptope, orderID);
        marketService.addItemToOrder(someLaptope, orderID);
        assertEquals(3, marketService.getOrderInfo(orderID).getCart().get(someLaptope));

    }

}
