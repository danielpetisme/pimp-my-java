Pimp My Java
============

Set of experiments on how to use invokedynamic.


Getting Started
---------------

You need [Maven](http://maven.apache.org/) to download the Internet.

Install the project's dependencies by running the following command:

    mvn install


Wait... wait... wait...


Ok, build success. Now copy the dependencies to the `target/` directory:

    mvn dependency:copy-dependencies

You should be able to run the examples that way:

    java -cp target/pimpmyjava-1.0.0-SNAPSHOT.jar:target/dependency/asm-4.1.jar lavajug.yaal.Yaal
