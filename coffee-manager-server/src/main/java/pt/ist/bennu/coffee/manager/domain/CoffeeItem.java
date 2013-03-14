package pt.ist.bennu.coffee.manager.domain;

import java.math.BigDecimal;

import pt.ist.bennu.service.Service;
import pt.ist.fenixframework.FFDomainException;

public class CoffeeItem extends CoffeeItem_Base {

    public static class DuplicateCoffeeItemException extends FFDomainException {

        private static final long serialVersionUID = 1L;

        public DuplicateCoffeeItemException(String message) {
            super(message);
        }

    }

    public CoffeeItem(String name, BigDecimal unitPrice) {
        this(name, unitPrice, 10);
    }

    public CoffeeItem(String name, BigDecimal unitPrice, String imageUrl) {
        this(name, unitPrice, 10, imageUrl);
    }

    public CoffeeItem(String name, BigDecimal unitPrice, int numUnits) {
        this(name, unitPrice, numUnits, null);
    }

    public CoffeeItem(String name, BigDecimal unitPrice, int numUnits, String imageUrl) {
        setName(name);
        setUnitPrice(unitPrice);
        setNumUnits(numUnits);
        setImageUrl(imageUrl);
        setAvailable(true);
        setCoffeeManager(CoffeeManager.getInstance());
    }

    public CoffeeItem update(String name, BigDecimal unitPrice, int numUnits, String imageUrl) {
        setCoffeeManager(null);
        CoffeeItem newVersion = new CoffeeItem(name, unitPrice, numUnits, imageUrl);
        newVersion.setPreviousVersion(this);
        return newVersion;
    }

    @Override
    public void setName(String name) {
        if (CoffeeManager.getInstance().hasCoffeeItem(name)) {
            throw new DuplicateCoffeeItemException(String.format("An item with the name %s already exists.", name));
        }
        super.setName(name.trim());
    }

    public BigDecimal calculateTotalPrice() {
        return getUnitPrice().multiply(new BigDecimal(getNumUnits()));
    }

    @Service
    public void delete() {
        setCoffeeManager(null);
        deleteDomainObject();
    }

}
