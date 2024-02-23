package ru.inno.market;

import org.junit.jupiter.api.*;
import ru.inno.market.core.MarketService;
import ru.inno.market.model.*;
import ru.inno.market.model.Order;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarketServiceTest {

    private Client Jack;
    private Item someLaptope;
    private Order JackOrder;
    private MarketService marketService;

   @BeforeEach
    public void setUp() {
       marketService = new MarketService();
       Jack = new Client(8, "Jack");
//        MartaOrder = new Order(1, Marta); // Создаем заказ тестового юзера Марты
    }

//    @AfterEach
//    public void tearDown() {
//        MartaOrder = new Order(1, Marta);//перезаписываем заказ после теста - меняем тот, что был на пустой заказ
//    }


    @Test
    @DisplayName("При создании нового заказа ID заказа увеличивается на 1")
    @Tag("СозданиеЗаказа")
    public void createOrderForTest1() throws IOException {
        int orderID = marketService.createOrderFor(Jack);
        int orderID2 = marketService.createOrderFor(Jack);
        assertEquals(1, orderID2-orderID); // сравниваем
    }

    @Test
    @DisplayName("При добавлении товара в заказ в нем оказывается именно этот товар")
    @Tag("ДобавлениеТовара")
    public void addItemToOrderTest1() throws IOException {
        int orderID = marketService.createOrderFor(Jack);
        someLaptope = new Item(30, "NewLaptop", Category.LAPTOPS, 100.00); // создаем новый товар
        marketService.addItemToOrder(someLaptope, orderID);
        assertTrue(marketService.getOrderInfo(orderID).getItems().containsKey(someLaptope)); // сравниваем
        //6. Удаляем заказ - вынесено в tearDown?

    }
//___________________________________________
    @Test
    @DisplayName("Применение корректной скидки корректно уменьшает сумму заказа")
    @Tag("Скидка")
    public void applyDiscountForOrderTest1() throws IOException {
        int orderID = marketService.createOrderFor(Jack);
        someLaptope = new Item(30, "NewLaptop", Category.LAPTOPS, 100.00); // создаем новый товар
        marketService.addItemToOrder(someLaptope, orderID);
        double x = marketService.applyDiscountForOrder(orderID, PromoCodes.HAPPY_NEW_YEAR);
        double totalPriceAfter = marketService.getOrderInfo(0).getTotalPrice();
        assertEquals(x, totalPriceAfter);


    }

    @Test
    @DisplayName("Цена товара в заказе равна сумме заказа")
    @Tag("info")
    public void getOrderInfoTest1() throws IOException {

        int orderID = marketService.createOrderFor(Jack);
        someLaptope = new Item(30, "NewLaptop", Category.LAPTOPS, 100.00); // создаем новый товар
        marketService.addItemToOrder(someLaptope, orderID);
        assertEquals(someLaptope.getPrice(), marketService.getOrderInfo(orderID).getTotalPrice());

    }

    @Test
    @DisplayName("В заказе указано правильное количество товаров")
    @Tag("info")
    public void getOrderInfoTest2() throws IOException {

        int orderID = marketService.createOrderFor(Jack);
        someLaptope = new Item(30, "NewLaptop", Category.LAPTOPS, 100.00); // создаем новый товар
        marketService.addItemToOrder(someLaptope, orderID);
        marketService.addItemToOrder(someLaptope, orderID);
        marketService.addItemToOrder(someLaptope, orderID);
        assertEquals(3, marketService.getOrderInfo(orderID).getCart().get(someLaptope));
    }

}
