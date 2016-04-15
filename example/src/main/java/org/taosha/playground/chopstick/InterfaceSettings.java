package org.taosha.playground.chopstick;

import chopstick.Name;

@Name("interface")
interface InterfaceSettings {
    @Name("language") String language();

    @Name("font-size") int fontSize();

    @Name("font-size") void fontSize(int fontSize);
}