FROM gradle:8.8.0-jdk22

WORKDIR /app

COPY /app .

RUN chmod +x gradlew

RUN ./gradlew installDist

CMD ./build/install/app/bin/app