package pt.ist.bennu.coffee.manager.domain;

import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.service.Service;

public class Person extends Person_Base {

    public Person(String name, String email, User user) {
        setName(name);
        setName(name);
        setEmail(email);
        setUser(user);
    }

    public String getAvatar() {
        // String hash = Hashing.md5().hashString(getUser().getEmail(), Charset.forName("UTF-8")).toString();
        // return "http://www.gravatar.com/avatar/" + hash;
        return String
                .format("https://fenix.ist.utl.pt/publico/retrievePersonalPhoto.do?method=retrieveByUUID&uuid=%s&contentContextPath_PATH=/homepage",
                        getUser().getUsername());
    }

    @Service
    public static Person newInstance(String name, String email, User user) {
        return new Person(name, email, user);
    }
}
