###Instructions
Start server type: wf enter
Stop server: ctrl c

Hatch Tamagotchis 		POST http://localhost:8080/api/tamagotchis
Get All Tamagotchis 		GET http://localhost:8080/api/tamagotchis
Get Tamagotchi by id 		GET http://localhost:8080/api/tamagotchis/id

Feed 					PUT http://localhost:8080/api/tamagotchis/id/feed
Play 					PUT http://localhost:8080/api/tamagotchis/id/play
Sleep 					PUT http://localhost:8080/api/tamagotchis/id/sleep
Wake 					PUT http://localhost:8080/api/tamagotchis/id/wake
Clean 					PUT http://localhost:8080/api/tamagotchis/id/clean
Medicine 				PUT http://localhost:8080/api/tamagotchis/id/medicine
Release					DELETE http://localhost:8080/api/tamagotchis/id

Pagination(default limit=10 offset=0) GET http://localhost:8080/api/tamagotchis?offset=0&limit=10
Filtering: GET http://localhost:8080/api/tamagotchis?character=Mametchi
Sort:(sortBy, default order = asc): energy, happiness, health, hunger, name, needsCleaning, status
GET http://localhost:8080/api/tamagotchis?sortBy=happiness&order=desc