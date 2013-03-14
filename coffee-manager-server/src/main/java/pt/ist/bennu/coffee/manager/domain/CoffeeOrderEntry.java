package pt.ist.bennu.coffee.manager.domain;

import java.math.BigDecimal;

public class CoffeeOrderEntry extends CoffeeOrderEntry_Base {

    public CoffeeOrderEntry(CoffeeItem item, int quantity) {
        setItem(item);
        setQuantity(quantity);
    }

    public void delete() {
        setCoffeeOrder(null);
        setItem(null);
        deleteDomainObject();
    }

    public BigDecimal getTotal() {
        return getItem().getUnitPrice().multiply(new BigDecimal(getItem().getNumUnits())).multiply(new BigDecimal(getQuantity()));
    }

}
