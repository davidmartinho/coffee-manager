package pt.ist.bennu.coffee.manager.rest;

import pt.ist.bennu.bennu.core.rest.BennuRestResource;
import pt.ist.bennu.coffee.manager.domain.CoffeeManager;

public abstract class CoffeeManagerAbstractResource extends BennuRestResource {

    protected CoffeeManager getCoffeeManager() {
        return CoffeeManager.getInstance();
    }

}
