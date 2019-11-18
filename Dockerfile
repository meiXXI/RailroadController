
# build client
FROM trion/ng-cli as client-builder

COPY ["src/main/client", "/app"]
RUN ls -l

RUN npm install
RUN npx ng version
RUN npx ng build --prod=true --outputPath=/app/static --optimization=true


# build java project
FROM openjdk:11-jdk-slim as java-builder

RUN mkdir -p /work/src \
    && mkdir -p /work/gradle \
    && mkdir -p /work/.git

RUN apt-get update && apt-get install -y git \
    && rm -rf /var/lib/apt/lists/*

COPY [".git", "/work/.git"]
COPY ["src", "/work/src"]
COPY ["gradle", "/work/gradle"]
COPY ["build.gradle", "settings.gradle", "gradlew", "/work/"]

RUN rm -rf /work/src/main/resources/static
COPY --from=client-builder /app/static /work/src/main/resources/static

WORKDIR /work

RUN ./gradlew build --no-daemon


# build final image
FROM openjdk:11-jre-slim

ENV TTY="/opt/ttyV0"
ENV IP_BASE_STATION="192.168.42.240:2001"

RUN apt-get update && apt-get install -y socat \
    && rm -rf /var/lib/apt/lists/*

COPY --from=java-builder ["/work/build/libs/*.jar", "/opt/RailwayController.jar"]

EXPOSE 8080

WORKDIR /opt
 
ENTRYPOINT socat pty,link=$TTY,nonblock,raw,echo=0,ignoreof,waitslave tcp:$IP_BASE_STATION & exec java -Xmx4g -jar /opt/RailwayController.jar