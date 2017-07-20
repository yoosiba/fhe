[![Build Status](https://travis-ci.org/yoosiba/fhe.svg?branch=master)](https://travis-ci.org/yoosiba/fhe)

# fat headless eclipse

Example of headless eclipse application packaged as single executable jar.

Assuming it is properly `cli.jar` you can run it on the command line:
```sh
java -jar cli.jar arg1 2
17:01:57:784 start install bundles
17:01:57:829 Skip install of already running bundle org.eclipse.osgi_3.12.0.v20170512-1932.jar
17:01:57:829 finish install bundles
17:01:57:829 laod bundle and invoke its method
17:01:58:008 app.Application :: main : begin
17:01:58:009 app.Application :: doMain : begin
17:01:58:010 core.Greeter :: greet : start
17:01:58:010 Hi, my name is Application.main with args [arg1, 2]
17:01:58:010 core.Greeter :: greet : end
17:01:58:146 BTW, Platform.isRunning() : true
17:01:58:146 app.Application :: doMain : end
17:01:58:146 app.Application :: main : end
17:01:58:146 invocation finised
17:01:58:222 finally
```

## overview
This repo presents example how to build headless eclipse application. While there are few way to do this,
this example focuses on the following:
 * whole app packaged into single executable jar
 * no native binaries, e.g. `cli.dmg` or `cli.exe`
 * uses `.product` definition for defining app
 * no duplication of the product bundles between product file and launcher `pom.xml`

### design
We have two ordinary `eclipse-bundles`
 * `core`
 * `app` that depends on `core`
Additionally we have `prod` bundle of type `eclipse-repository` which defines our headless product,
 i.e. it contains the `.product` with the following:
```xml
   <plugins>
      <plugin id="app"/>
      <plugin id="core"/>
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
