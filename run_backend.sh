cd backend 
./mvnw clean test package
docker build -t zszpg-backend .
docker run --rm --name zszpg-backend -p 8080:8080 zszpg-backend
