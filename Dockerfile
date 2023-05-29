FROM amazoncorretto:17

WORKDIR /daftgoods

COPY build/libs/daft-goods-service-0.0.1-SNAPSHOT.jar /daftgoods/daft-goods-service-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "/daftgoods/daft-goods-service-0.0.1-SNAPSHOT.jar"]