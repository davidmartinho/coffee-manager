package pt.ist.bennu.coffee.manager.domain;

import java.math.BigDecimal;

import javax.servlet.http.HttpSession;

import pt.ist.bennu.core.domain.Bennu;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.security.Authenticate;
import pt.ist.bennu.service.Service;

public class CoffeeManager extends CoffeeManager_Base {

    public CoffeeManager() {
        setBennu(Bennu.getInstance());
    }

    @Service
    private static void createCoffeeManager() {
        new CoffeeManager();
    }

    public static CoffeeManager getInstance() {
        if (!Bennu.getInstance().hasCoffeeManager()) {
            createCoffeeManager();
        }
        return Bennu.getInstance().getCoffeeManager();
    }

    public CoffeeItem getCoffeeItem(final String name) {
        for (final CoffeeItem item : getCoffeeItemSet()) {
            if (item.getName().equalsIgnoreCase(name.trim())) {
                return item;
            }
        }
        return null;
    }

    public boolean hasCoffeeItem(final String name) {
        return getCoffeeItem(name) != null;
    }

    @Service
    public Boolean initDB() {
        if (!hasAnyCoffeeItem()) {
            CoffeeItem ristretto =
                    new CoffeeItem("Ristretto", new BigDecimal("0.375"), "http://nesclub.nespresso.com/img/pictures/00746.gif");
            CoffeeItem arpeggio =
                    new CoffeeItem("Arpeggio", new BigDecimal("0.375"), "http://nesclub.nespresso.com/img/pictures/00673.gif");
            CoffeeItem roma =
                    new CoffeeItem("Roma", new BigDecimal("0.375"), "http://nesclub.nespresso.com/img/pictures/00747.gif");
            new CoffeeItem("Decaffeinato Intenso", new BigDecimal("0.375"), "http://nesclub.nespresso.com/img/pictures/00707.gif");
            CoffeeItem livanto =
                    new CoffeeItem("Livanto", new BigDecimal("0.375"), "http://nesclub.nespresso.com/img/pictures/00712.gif");
            new CoffeeItem("Capriccio", new BigDecimal("0.375"), "http://nesclub.nespresso.com/img/pictures/00674.gif");
            new CoffeeItem("Volluto", new BigDecimal("0.375"), "http://nesclub.nespresso.com/img/pictures/00751.gif");
            new CoffeeItem("Cosi", new BigDecimal("0.375"), "http://nesclub.nespresso.com/img/pictures/00676.gif");
            new CoffeeItem("Decaffeinato", new BigDecimal("0.375"), "http://nesclub.nespresso.com/img/pictures/00706.gif");
            new CoffeeItem("Fortissio Lungo", new BigDecimal("0.395"), "http://nesclub.nespresso.com/img/pictures/02455.jpg");
            new CoffeeItem("Vivalto", new BigDecimal("0.395"), "http://nesclub.nespresso.com/img/pictures/00750.gif");
            new CoffeeItem("Decaffeinato Lungo", new BigDecimal("0.395"), "http://nesclub.nespresso.com/img/pictures/00708.gif");
            new CoffeeItem("Finezzo", new BigDecimal("0.395"), "http://nesclub.nespresso.com/img/pictures/00709.gif");
            new CoffeeItem("Kit descalcificação", new BigDecimal("10"), 2, "http://nesclub.nespresso.com/img/pictures/01881.jpg");
            new CoffeeItem("Dhjana", new BigDecimal("0.39"), "http://nesclub.nespresso.com/img/pictures/05441.jpg");
            new CoffeeItem("Variations Chocolate Negro", new BigDecimal("0.42"),
                    "http://nesclub.nespresso.com/img/pictures/05836.jpg");
            new CoffeeItem("Rosabaya from Colombia", new BigDecimal("0.415"),
                    "http://nesclub.nespresso.com/img/pictures/02460.jpg");
            new CoffeeItem("Indriya from India", new BigDecimal("0.415"), "http://nesclub.nespresso.com/img/pictures/02466.jpg");
            new CoffeeItem("Dulso do Brasil", new BigDecimal("0.415"), "http://nesclub.nespresso.com/img/pictures/02463.jpg");
            new CoffeeItem("Variation Caramelo", new BigDecimal("0.38"), "http://nesclub.nespresso.com/img/pictures/04155.jpg");
            new CoffeeItem("Variations Flor de Baunilha", new BigDecimal("0.42"),
                    "http://nesclub.nespresso.com/img/pictures/05837.jpg");
            new CoffeeItem("Naora Limited Edition", new BigDecimal("0.425"),
                    "http://nesclub.nespresso.com/img/pictures/06621.jpg");
            new CoffeeItem("Variations Cereja", new BigDecimal("0.42"), "http://nesclub.nespresso.com/img/pictures/05836.jpg");

            return true;
        }
        return false;
    }

    public User login(HttpSession session, String username) {
        return Authenticate.login(session, username, null, false);
    }
}
