#! /bin/bash
mvn -f ../../pom.xml clean package site changes:announcement-generate -P site,sources