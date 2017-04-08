lab03 must be include cystom oracle jdbc library into maven local repository for runing.
Issue following command:

mvn install:install-file -Dfile=\oracle\ojdbc6-11.2.0.jar -DgroupId=com.oracle
-DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar

Use sql\*.sql for creating tables