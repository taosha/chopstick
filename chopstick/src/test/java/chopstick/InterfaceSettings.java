package chopstick;

/**
 * Created by San on 4/12/16.
 */
@Name("interface")
interface InterfaceSettings {

    @Name("language") Facade<String> language();

    @Name("font-size") IntFacade fontSize();
}
