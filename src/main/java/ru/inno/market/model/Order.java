package ru.inno.market.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order {
    private int id;
    private Map<Item, Integer> cart;
    private Client client;

    private double totalPrice;
    private boolean discountApplied;

    public Order(int id, Client client) {
        this.id = id;
        this.client = client;
        cart = new HashMap<>();
        totalPrice = 0;
        discountApplied = false;
    }

    public Map<Item, Integer> getItems() {
        return cart;
    }

    public int getId() {
        return id;
    }

    public void addItem(Item item) { //добавляет один товар в список заказа
        int counter = cart.getOrDefault(item, 0);//сколько таких товаров уже в заказе
        cart.put(item, ++counter); //обновляем мапу списка товаров в заказе, количество увеличивается
        totalPrice += item.getPrice();//цена заказа увеличивается на цену товара
    }


    public void applyDiscount(double discount) {
     if (discount>=0 && discount<=1) {
         if (!discountApplied) {
             totalPrice *= (1 - discount);
             discountApplied = true;
         }
     }
     else {
         System.out.println("Некорректное значение скидки");
     }
    }

    public Client getClient() {
        return client;
    }

    public Map<Item, Integer> getCart() {
        return cart;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isDiscountApplied() {
        return discountApplied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return getId() == order.getId() && Double.compare(order.getTotalPrice(), getTotalPrice()) == 0 && isDiscountApplied() == order.isDiscountApplied() && Objects.equals(getCart(), order.getCart()) && Objects.equals(getClient(), order.getClient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCart(), getClient(), getTotalPrice(), isDiscountApplied());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cart=" + cart +
                ", client=" + client +
                ", totalPrice=" + totalPrice +
                ", discountApplied=" + discountApplied +
                '}';
    }
}

