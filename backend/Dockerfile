FROM hirokimatsumoto/alpine-openjdk-11
RUN apk --no-cache add curl
COPY target/pet-transfer-system-api*.jar pet-transfer-system-api.jar
CMD java ${JAVA_OPTS} -jar pet-transfer-system-api.jar
