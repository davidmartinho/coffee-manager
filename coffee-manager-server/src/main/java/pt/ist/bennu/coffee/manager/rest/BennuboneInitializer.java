package pt.ist.bennu.coffee.manager.rest;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import pt.ist.bennu.bennu.core.rest.serializer.JsonAwareResource;
import pt.ist.bennu.coffee.manager.adapter.CoffeeBatchAdapter;
import pt.ist.bennu.coffee.manager.adapter.CoffeeItemAdapter;
import pt.ist.bennu.coffee.manager.adapter.CoffeeOrderAdapter;
import pt.ist.bennu.coffee.manager.adapter.CoffeeOrderEntryViewer;
import pt.ist.bennu.coffee.manager.adapter.UserViewer;
import pt.ist.bennu.coffee.manager.domain.CoffeeBatch;
import pt.ist.bennu.coffee.manager.domain.CoffeeItem;
import pt.ist.bennu.coffee.manager.domain.CoffeeOrder;
import pt.ist.bennu.coffee.manager.domain.CoffeeOrderEntry;
import pt.ist.bennu.core.domain.User;

@WebListener
public class BennuboneInitializer implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        JsonAwareResource.setDefault(CoffeeBatch.class, CoffeeBatchAdapter.class);
        JsonAwareResource.setDefault(CoffeeItem.class, CoffeeItemAdapter.class);
        JsonAwareResource.setDefault(CoffeeOrder.class, CoffeeOrderAdapter.class);
        JsonAwareResource.setDefault(CoffeeOrderEntry.class, CoffeeOrderEntryViewer.class);
        JsonAwareResource.setDefault(User.class, UserViewer.class);
    }

}
