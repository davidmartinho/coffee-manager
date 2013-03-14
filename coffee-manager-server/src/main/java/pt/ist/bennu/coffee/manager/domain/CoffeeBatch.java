package pt.ist.bennu.coffee.manager.domain;

import java.math.BigDecimal;

import org.joda.time.DateTime;

public class CoffeeBatch extends CoffeeBatch_Base {

    public CoffeeBatch() {
        setCoffeeManager(CoffeeManager.getInstance());
        setCreationTimestamp(new DateTime());
    }

    public CoffeeBatch(CoffeeOrder... coffeeOrders) {
        this();
        for (CoffeeOrder order : coffeeOrders) {
            addCoffeeOrder(order);
        }
        setCoffeeManager(CoffeeManager.getInstance());
    }

    @Override
    public BigDecimal getShippingCharges() {
        if (super.getShippingCharges() != null) {
            return super.getShippingCharges();
        } else {
            int totalCount = getTotalCount();
            if (totalCount > 200) {
                return BigDecimal.ZERO;
            } else {
                return new BigDecimal(3.00);
            }
        }
    }

    public int getTotalCount() {
        int totalCount = 0;
        for (CoffeeOrder coffeeOrder : getCoffeeOrderSet()) {
            totalCount += coffeeOrder.getCount();
        }
        return totalCount;
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = new BigDecimal(0.0);
        for (CoffeeOrder coffeeOrder : getCoffeeOrderSet()) {
            total.add(coffeeOrder.getTotal());
        }
        return total;
    }

    public void delete() {
        getCoffeeOrder().clear();
        deleteDomainObject();
    }

    public void send() {
        setSentTimestamp(new DateTime());
    }

    public void receive() {
        setReceivedTimestamp(new DateTime());
    }

    public boolean isSent() {
        return getSentTimestamp() != null;
    }
}
