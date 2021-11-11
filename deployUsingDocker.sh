docker run -dit -p 9006:9006 --name=weather sanilbagzai/weather:3.0
docker run -dit -p 80:80 --link weather:weather sanilbagzai/weather-ui:3.0
