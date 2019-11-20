To ojdbc6:  mvn install:install-file -Dfile=/Users/path/to/ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.4 -Dpackaging=jar

To run: mvn clean test verify -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml -Dcucumber.options="-t @android"