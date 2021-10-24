cd weather
mvn clean install
docker run -dit -p 8080:8080 --name=weather sanilbagzai/weather:1.0

cd ../weather-ui
docker run -dit -p 80:80 --link weather:weather sanilbagzai/weather-ui:1.0
