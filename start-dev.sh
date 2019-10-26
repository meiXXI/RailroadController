#!/bin/bash
set -e

version=dev-$(($(date +%s%N)/1000000))
timestamp=$(date +"%Y-%m-%d %T")

echo ""

if [[ -z $1 || $1 != "latest" ]]
then
    echo "Build Docker image..."

    # build docker file
    docker build \
        --no-cache \
        -t railroad-controller:$version \
        -t railroad-controller:latest \
        .
else
    echo "Use latest Docker image..."
    version=latest

    # inspect image
    docker image inspect railroad-controller:$version
fi

# execute
docker run \
    -p 8080:8080 \
    railroad-controller:$version