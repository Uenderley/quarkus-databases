docker run --name postgres-user -e "POSTGRES_PASSWORD=postgres" -p 5433:5432 -d postgres

docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_PASSWORD=root -e MYSQL_USER=root -e MYSQL_DATABASE=spring_batch -p 3306:3306 -d mysql

docker run --name redis --memory-swappiness=0 -p 6379:6379 -d redis:5.0.6


#Criar banco no MySQL
CREATE TABLE usuario (
    id int NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);


