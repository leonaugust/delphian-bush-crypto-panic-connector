#!/usr/bin/env bash
export CLASSPATH="$(find target/ -type f -name '*.jar'| grep '\-package' | tr '\n' ':')"
    docker build . -t delphianbush/crypto-panic-connector:1.0
    docker run --net=host --rm -t \
           -v $(pwd)/offsets:/crypto-panic-connector/offsets \
           delphianbush/crypto-panic-connector:1.0
