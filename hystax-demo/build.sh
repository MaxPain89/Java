#!/usr/bin/env bash

set -e
set -o xtrace

BASEDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

source ~/.nvm/nvm.sh

nvm use 7.5.0

cd ${BASEDIR}/ui;
ng build --delete-output-path --output-path ${BASEDIR}/src/main/webapp/static;
cd ${BASEDIR};
mvn clean verify;
