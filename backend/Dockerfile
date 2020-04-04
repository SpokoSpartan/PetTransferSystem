FROM hirokimatsumoto/alpine-openjdk-11
RUN apk --no-cache add curl
COPY target/pet-transfer-system-api-0.0.1-SNAPSHOT.jar pet-transfer-system-api.jar
CMD java ${JAVA_OPTS} -Xms256m -Xmx256m -jar pet-transfer-system-api.jar
