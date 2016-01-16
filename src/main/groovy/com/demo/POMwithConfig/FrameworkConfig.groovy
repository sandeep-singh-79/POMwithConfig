package com.demo.POMwithConfig

/**
 * Created by SANDEEP on 1/16/2016.
 */
class FrameworkConfig {

    static def getConfig() {
        try {
            return new ConfigSlurper().
                    parse(new File("src/test/resources/Config.groovy")
                            .toURI().toURL())
        } catch (MalformedURLException e) {
            println("unable to locate Config file.")
            e.getCause()
            e.printStackTrace()
            return null;
        }
    }
}
