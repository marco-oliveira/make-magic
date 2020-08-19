# Make Magic API


### Subir a aplicação com docker compose
```mvn clean install ```

```docker-compose up --build --force-recreate ```

Serviços Dockers
* Banco de dados com MySql 5.7.
* Migração e execução de scripts SQL com FlyWay.
* Aplicação Spring Boot Make Magic API

### Endereço para a documentação da api com swagger-ui
http://localhost:8080/api/swagger-ui.html

* Json de exemplo para cadastrar um novo Personagem, com uma requisição POST.
    ```
  {
      "name": "Harry Potter",
      "role": "student",
      "school": "Hogwarts School of Witchcraft and Wizardry",
      "house": "5a05e2b252f721a3cf2ea33f",
      "patronus": "stag"
  }
  ```

