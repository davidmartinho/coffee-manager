package pt.ist.bennu.coffee.manager.domain;

import java.math.BigDecimal;

import pt.ist.bennu.service.Service;

public class CoffeeOrder extends CoffeeOrder_Base {

    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal(0.0);
        for (CoffeeOrderEntry coffeeOrderEntry : getEntrySet()) {
            total = total.add(coffeeOrderEntry.getTotal());
        }
        return total;
    }

    public int getCount() {
        int totalCount = 0;
        for (CoffeeOrderEntry entry : getEntrySet()) {
            totalCount += entry.getQuantity() * entry.getItem().getNumUnits();
        }
        return totalCount;
    }

    public CoffeeOrderEntry getEntry(CoffeeItem item) {
        for (CoffeeOrderEntry entry : getEntry()) {
            if (entry.getItem().equals(item)) {
                return entry;
            }
        }
        return null;
    }

    @Service
    public void addEntry(CoffeeItem item, int quantity) {
        final CoffeeOrderEntry entry = getEntry(item);
        setEntry(item, entry != null ? entry.getQuantity() + quantity : quantity);
    }

    private void setEntry(CoffeeItem item, int quantity) {
        final CoffeeOrderEntry entry = getEntry(item);
        if (entry != null) {
            entry.setQuantity(quantity);
        } else {
            addEntry(new CoffeeOrderEntry(item, quantity));
        }
    }

    public void delete() {
        setUser(null);
        setCoffeeBatch(null);
        getEntry().clear();
        deleteDomainObject();
    }

    public Boolean isBatched() {
        return hasCoffeeBatch();
    }

    public boolean isSent() {
        return hasCoffeeBatch() && getCoffeeBatch().isSent();
    }

    public int getNumBoxes() {
        int numBoxes = 0;
        for (CoffeeOrderEntry entry : getEntrySet()) {
            numBoxes += entry.getQuantity();
        }
        return numBoxes;
    }

}
