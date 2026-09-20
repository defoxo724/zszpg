cd backend 
./mvnw clean test package
docker build -t zszpg-backend .
docker run --rm --name zszpg-backend --network host zszpg-backend
