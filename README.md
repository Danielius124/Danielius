!!!!!!!INSTRUCTION!!!!
1. Clone my project from GitHub or just open it directly in your IDE with GitHub link.
2. My program has basic CRUD operations and some more(getAllBuildings, getBuilding(by Id), addBuilding, updateBuilding, deleteBuildingById, taxByOwnedProperties, findByStreetAndType)
2.1 to reach a endpoint getAllBuilding you need to start the program, and write in to your browser http://localhost:8080/api/buildings/ and you will get all building stored in database.
2.2 if you want to get building by Id for example use http://localhost:8080/api/buildings/10001 and you get information about single building.
2.3 to create new building you should use Post method in postman or etc. http://localhost:8080/api/buildings to create new building you should
send POST method with body. Choose raw and JSON format.
2.4 if you want to update your building to almost the same like in previous tip but use PUT method instead of POST method, and into method body,
write ID of building which you want to UPDATE!!
2.5 if you want to delete building for example use http://localhost:8080/api/buildings/10001 and the building will be succesfully deleted.
2.6 Tax calculator, to use tax calculator you should use link: http://localhost:8080/api/tax/{ownerName}, this endpoint calculate the total 
early taxes for your owner property, {ownerName} you can write Danielius or just copy http://localhost:8080/api/tax/Danielius and you get tax calculator result
also you can try other names.
2.7 Last endpoint is like search, you can use it in this link http://localhost:8080/api/buildingsOnStreet?size=50&street=pavilni&type=apartment
so what it does? This endpoint sorts three best building in street by property type, then it sort for the closest size of building you give, and lastly
it sort 3 best building by value ASC.

WHAT I USED!!

1. Spring Boot
2. JUnit tests
3. Custom Validation
4. Custom Exception handlers
5. Spring DATA JPA
6. Hibernate
7. H2 in memory database