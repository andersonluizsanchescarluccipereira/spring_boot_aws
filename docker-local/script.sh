curl -X POST http://localhost:8080/pessoas \
  -H "Content-Type: application/json" \
  -d '{
    "id": "1",
    "nome": "João da Silva",
    "idade": 30
  }'
#---------
curl http://localhost:8080/pessoas/1

curl http://localhost:8080/pessoas

curl -X DELETE http://localhost:8080/pessoas/1


