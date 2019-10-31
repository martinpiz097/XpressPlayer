#!/bin/bash
mvn dependency:resolve
mvn dependency:sources
mvn dependency:resolve -Dclassifier=javado
