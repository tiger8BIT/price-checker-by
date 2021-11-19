FROM maven:3.8.3-jdk-11

ARG TIME_ZONE
ARG CHROMEDRIVER_DOWNLOAD_LINK
ARG CHROME_DOWNLOAD_LINK

COPY . /usr/src/myapp
WORKDIR /usr/src/myapp

RUN wget -O chromedriver.zip $CHROMEDRIVER_DOWNLOAD_LINK
RUN unzip chromedriver.zip
RUN rm chromedriver.zip

RUN wget -O chrome.deb $CHROME_DOWNLOAD_LINK
RUN apt-get update
RUN yes | apt install ./chrome.deb
RUN rm chrome.deb

RUN ln -snf /usr/share/zoneinfo/$TIME_ZONE /etc/localtime && echo $TIME_ZONE > /etc/timezone

EXPOSE 8080 8080

CMD mvn spring-boot:run