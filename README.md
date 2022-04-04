<img src="https://gap.surati.io/img/logo.png" width="64px" height="64px"/>

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/gap-enterprise/gtp-base)](http://www.rultor.com/p/gap-enterprise/gtp-base)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![Javadoc](http://www.javadoc.io/badge/io.surati.gap/gtp-base.svg)](http://www.javadoc.io/doc/io.surati.gap/gtp-base)
[![License](https://img.shields.io/badge/License-Surati-important.svg)](https://github.com/gap-enterprise/gtp-base/blob/master/LICENSE.txt)
[![codecov](https://codecov.io/gh/gap-enterprise/gtp-base/branch/master/graph/badge.svg)](https://codecov.io/gh/gap-enterprise/gtp-base)
[![Hits-of-Code](https://hitsofcode.com/github/gap-enterprise/gtp-base)](https://hitsofcode.com/view/github/gap-enterprise/gtp-base)
[![Maven Central](https://img.shields.io/maven-central/v/io.surati.gap/gtp-base.svg)](https://maven-badges.herokuapp.com/maven-central/io.surati.gap/gtp-base)
[![PDD status](http://www.0pdd.com/svg?name=gap-enterprise/gtp-base)](http://www.0pdd.com/p?name=gap-enterprise/gtp-base)

General Treasury Payment (GTP)

It is the base payment for General Treasury.

# How to compile the project

Just do this:
```shell
mvn clean install -X
```

# How to generate `jOOQ` classes
Firstly, you must create a PostgreSQL database (`db_gap` by example) and host it at a PostgreSQL instance (reachable locally by example at port `5070`).

Then, run this command:
```shell
mvn clean compile -PjooqGen -Ddb.driver=org.postgresql.Driver -Ddb.url=jdbc:postgresql://127.0.0.1:5070/db_gap -Ddb.user=gap -Ddb.password=admin
```