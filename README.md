[![Build Status](https://travis-ci.org/yoosiba/fhe.svg?branch=master)](https://travis-ci.org/yoosiba/fhe)

# fat headless eclipse

Example of headless eclipse application packaged as single executable jar.

Assuming build passed there will be `cli.jar` you can run on the command line:
```sh
java -jar cli.jar arg1 2
Debug options:
    file:/home/travis/build/yoosiba/fhe/com.github.yoosiba.fhe.parent/com.github.yoosiba.fhe.check/true not found
13:48:54:190 start install bundles
13:48:54:191 INSTALL bundle com.github.yoosiba.fhe.application_1.0.0.jar
13:48:54:237 INSTALL bundle com.github.yoosiba.fhe.core_1.0.0.jar
13:48:54:266 INSTALL bundle com.github.yoosiba.fhe.extension_1.0.0.jar
13:48:54:294 INSTALL bundle org.eclipse.core.contenttype_3.6.0.v20170207-1037.jar
13:48:54:322 INSTALL bundle org.eclipse.core.jobs_3.9.0.v20170322-0013.jar
13:48:54:343 INSTALL bundle org.eclipse.core.runtime_3.13.0.v20170207-1030.jar
13:48:54:348 INSTALL bundle org.eclipse.equinox.app_1.3.400.v20150715-1528.jar
13:48:54:379 INSTALL bundle org.eclipse.equinox.common_3.9.0.v20170207-1454.jar
13:48:54:388 INSTALL bundle org.eclipse.equinox.preferences_3.7.0.v20170126-2132.jar
13:48:54:438 INSTALL bundle org.eclipse.equinox.registry_3.7.0.v20170222-1344.jar
13:48:54:479 SKIP INSTALL (already installed) bundle org.eclipse.osgi_3.12.0.v20170512-1932.jar
13:48:54:480 finish install bundles
13:48:54:480 load bundle and invoke its method
13:48:55:246 app.Activator :: start : begin
13:48:55:262 app.Application :: main : begin
13:48:55:263 app.Application :: doMain : begin
13:48:55:265 core.Greeter :: greet : start
13:48:55:268 Hi, my name is Application.main with args [arg1, 2]
13:48:55:269 core.Greeter :: greet : end
13:48:55:566 BTW, Platform.isRunning() : true
13:48:55:567 Let's exmine extensions
13:48:55:580 Collecting data
13:48:55:581 Collecting data from org.eclipse.core.internal.registry.ConfigurationElementHandle@9
13:48:55:595 ExtensionActivator :: start : begin
13:48:55:601 Extensions data: com.github.yoosiba.fhe.extension.SomeExtension
13:48:55:602 app.Application :: doMain : end
13:48:55:602 app.Application :: main : end
13:48:55:602 invocation finished
13:48:55:645 ExtensionActivator :: stop : begin
13:48:55:648 app.Activator :: stop : begin
13:48:55:872 finally
```

## overview
This repo presents example how to build headless eclipse application. While there are few way to do this,
this example focuses on the following:
 * whole app packaged into single executable jar
 * no native binaries, e.g. `cli.dmg` or `cli.exe`
 * uses `.product` definition for defining app
 * no duplication of the product bundles between product file and launcher `pom.xml`

### design
We have ordinary `eclipse-bundles`
 * `core`
 * `application` that depends on `core`
 * `extension` that registers itself in the `application` extension point
Additionally we have `prod` bundle of type `eclipse-repository` which defines our headless product,
 i.e. it contains the `.product` with the following:
```xml
   <plugins>
      <plugin id="com.github.yoosiba.fhe.application"/>
      <plugin id="com.github.yoosiba.fhe.core"/>
      <plugin id="com.github.yoosiba.fhe.extension"/>
      <plugin id="org.eclipse.core.contenttype"/>
      <plugin id="org.eclipse.core.jobs"/>
      <plugin id="org.eclipse.core.runtime"/>
      <plugin id="org.eclipse.equinox.app"/>
      <plugin id="org.eclipse.equinox.common"/>
      <plugin id="org.eclipse.equinox.preferences"/>
      <plugin id="org.eclipse.equinox.registry"/>
      <plugin id="org.eclipse.osgi"/>
   </plugins>
```

That is our headless application, defined by the product. You can launch it using product definition.

Besides normal `tycho` build for `prod` we use `maven-assembly-plugin` to create jar with all the jars of the product.

Next we define project `cli`that depends previously defined `prod.jar`. In is a wrapper / launcher for our product
that contains all product jars. Additionally it contains launcher code that will:
 * start eclipse / equinox platform using `org.eclipse.core.runtime.adaptor.EclipseStarter`
 * load / install / start all our product bundles
 * load concrete classes from eclipse bundles and invoke them, specifically our 'main' class from `app`
 * after execution launcher will shutdown the platform and exit.
When building the `cli.jar` we ensure to make it is executable and self contained.


Just for tests example contains `check` project with  test that will call the `cli.jar` through the command line
and assert if command line call works fine.

#### implementation (aka. shortcuts, hacks and duct tape)
* lots of copying and packaging-repackaging jars
* requires `mvn install` to pass extra jars between modules
* m2e has issues with some lifecycle mapping 
* stale configuration may be in `user.home`/.eclipse/446492912/configuration
* uses custom class loader copied from `org.eclipse.jdt.ui`

##### based on
 * custom class loader trick I saw in [Lorenzo Bettini/eclipse-fatjar-example](https://github.com/LorenzoBettini/eclipse-fatjar-example/tree/master/org.example.standalone)
 * custom jar loader taken from [Eclipse JDT jar in jar loader ](https://github.com/eclipse/eclipse.jdt.ui/tree/master/org.eclipse.jdt.ui/jar%20in%20jar%20loader/org/eclipse/jdt/internal/jarinjarloader) 
 * tons of code search [GH code search](https://github.com/search?l=Java&o=desc&q=EclipseStarter.main&s=indexed&type=Code) 
 

###### License
 EPL 1.0 just because JDT
