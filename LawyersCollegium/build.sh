#!/usr/bin/env bash

set -e
set -o xtrace

BASEDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

source ~/.nvm/nvm.sh

nvm use 7.5.0

cd ${BASEDIR}/admin-ui;
ng build --delete-output-path --output-path ${BASEDIR}/src/main/resources/static;