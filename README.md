# :computer: Project Java Spring Boot 
Criação de uma API, com a utilização do Spring Framework (Spring Boot) e do banco de dados PostgreSQL, para o gerenciamento e criação de eventos via endpoints CRUD. O projeto contou com a utilização da implementação do JPA do Java para fazer a persistência dos dados no banco de dados escolhidos, além de utilizar DTOs para realizar o acesso seguro aos dados do sistema.
 - Listagem paginada
 - Arquitetura em camadas com utilização de DTO
 - Pesquisa com filtros
 - Pesquisa por ID
 - Cadastro com validação (JAVAX)
 - Consultas
 - Alterações via endpoint PUT
 - Exclusão


## :zap: Participantes
- Gabriel Augusto Nogueira


## :package: Deploy no Heroku
Foi realizado o deploy do projeto no site Heroku.
 - Link: https://application-poo-ii-springboot.herokuapp.com




## 🚀 Project Java Spring Boot - Endpoints
Para acessar todas as requisições do projeto você pode instalar o Postman e baixar a minha coleção de requisições [acessando aqui](https://github.com/GabrielNogueiraBR/Project-JavaSpringBoot-II/tree/main/documents/Postman%20Collection) ou indo até a pasta `/documents/Postman Collection/`. Após feito o download do arquivo, basta importar as configurações e realizar as requisições para minha aplicação (seja local ou no heroku).

### <b>GET<b>
- /events
    ```
        https://application-poo-ii-springboot.herokuapp.com/events
    ```
    Filtros disponíveis:
    ```
    - Integer page,
    - Integer linesPerPage,
    - String direction,
    - String orderBy,
    - String name,
    - String description,
    - LocalDate startDate
    ```

### <b>POST<b>
- /events
    ```
        https://application-poo-ii-springboot.herokuapp.com/events
    ```
    Body raw(json)
    ```
    {
        "name" : "Festa Junina",
        "description" : "Festa que ocorre no mês de Junho",
        "startDate" : "2021-05-21",
        "endDate" : "2021-07-10",
        "startTime" : "19:30",
        "endTime" : "23:30",
        "emailContact" : "eventosvotorantim@gmail.com",
        "amountFreeTickets": 10,
        "amountPayedTickets": 50,
        "priceTicket": 24.5,
        "idAdmin": 1
    }
    ```
 
 - /admins
    ```
        https://application-poo-ii-springboot.herokuapp.com/admins
    ```
    Body raw(json)
    ```
    {
        "name": "Cláudio Vinicius Murilo Figueiredo",
        "email": "claudiovinicius@pibnet.com.br",
        "phoneNumber": "(81) 98805-7808"
    }
    ```
 
 
 - /attendees
    ```
        https://application-poo-ii-springboot.herokuapp.com/attendees
    ```
    Body raw(json)
    ```
    {
        "name": "Tomás Miguel Carvalho",
        "email": "ttomasmiguelcarvalho@aspadvocacia.com",
        "balance": 3.5
    }
    ```
 
 
 - /places
    ```
        https://application-poo-ii-springboot.herokuapp.com/places
    ```
    Body raw(json)
    ```
    {
        "name": "Petrópolis",
        "address": "Rua Estado do Paraná"
    }
    ```


 - /events/{id}/tickets
    ```
        https://application-poo-ii-springboot.herokuapp.com/events/1/tickets
    ```
    Body raw(json)
    ```
    {
        "idAttend": 4,
        "type": "PAYED"
    }
    ```

### <b>DELETE<b>

 - /events/{id}/tickets
    ```
        https://application-poo-ii-springboot.herokuapp.com/events/1/tickets
    ```
    Body raw(json)
    ```
    {
        "idAttend": 4,
        "type": "PAYED"
    }
    ```
