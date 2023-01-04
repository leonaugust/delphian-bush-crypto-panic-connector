#!/usr/bin/env bash
export CLASSPATH="$(find target/ -type f -name '*.jar'| grep '\-package' | tr '\n' ':')"
    docker build . -t delphianbush/crypto-panic-connector:0.0.1
    docker run --name news-connector --net=host --rm -t \
           -v $(pwd)/offsets:/crypto-panic-connector/offsets \
           delphianbush/crypto-panic-connector:0.0.1
