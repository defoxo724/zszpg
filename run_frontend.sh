cd frontend
docker build -t zszpg-frontend .
docker run --rm --name zszpg-frontend -p 3000:5173 zszpg-frontend 