#!/usr/bin/env bash


if [ $# -eq 0 ]
  then
    echo "No arguments supplied"
    exit 1
fi

BASEDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

set -e
set -o xtrace

java -jar ${BASEDIR}/target/mdb_access-1.0-SNAPSHOT-spring-boot.jar $1