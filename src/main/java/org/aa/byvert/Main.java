package org.aa.byvert;

import java.util.logging.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        Application application = new Application();

        log.info(application.findLemma("ночка").toString());

        log.info(application.findLemma("дочка").toString());
    }
}