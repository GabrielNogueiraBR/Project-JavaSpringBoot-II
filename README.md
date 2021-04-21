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


## :package: Deploy no Heroku
Foi realizado o deploy do projeto no site Heroku.
 - Link: https://application-poo-ii-springboot.herokuapp.com

## 🚀 Project Java Spring Boot - Endpoints

### <b>GET<b>
- /events
    ```
        https://application-poo-ii-springboot.herokuapp.com/events
    ```
- /events/id
    ```
        https://application-poo-ii-springboot.herokuapp.com/events/1
    ```
### <b>POST<b>
- /events
    ```
        https://application-poo-ii-springboot.herokuapp.com/events
    ```
    Body raw(json)
    ```
    {
        "name" : "Deus Chamma",
        "description" : "Grupo de oração Jovem",
        "place" : "Igreja São José, Votorantim-SP",
        "startDate" : "2021-05-25",
        "endDate" : "2021-05-25",
        "startTime" : "19:30",
        "endTime" : "21:30",
        "emailContact" : "deuschamma06@gmail.com"
    }    
    ```