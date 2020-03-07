FROM hirokimatsumoto/alpine-openjdk-11
RUN apk --no-cache add curl
COPY target/pet-transfer-system-auth*.jar pet-transfer-system-auth.jar
CMD java ${JAVA_OPTS} -jar pet-transfer-system-auth.jar
