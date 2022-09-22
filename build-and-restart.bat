docker build -f Dockerfile -t event_register_image:ER .
docker stop event_register_image:ER
docker rm event_register_container
docker run -d -p 8080:8080 --name=event_register_container event_register_image:ER