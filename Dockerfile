FROM amazoncorretto:17
#COPY build/libs/*.jar app.jar
RUN yum install tar -y && yum install gzip -y && curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.3/install.sh | bash && . ~/.nvm/nvm.sh && nvm install 16 && npm install @angular/cli
#↑ npm とangular cliのインストールまでやった。　Todo:　frontのディレクトリをマウントして、そこに移動、'npm install'と'ng build'を実行するようにしたい
WORKDIR /app
ENTRYPOINT ["bash", "entrypoint.sh"]
#ENTRYPOINT ["java", "-jar", "/app.jar"]