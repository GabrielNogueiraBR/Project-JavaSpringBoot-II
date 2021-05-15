// package br.facens.projectjavaspringboot.dto;

// import java.time.LocalDate;
// import java.time.LocalTime;

// import br.facens.projectjavaspringboot.entities.Event;

// public class EventDTO {
    
//     private Long id;
//     private String name;
//     private String description;
//     private LocalDate startDate;
//     private LocalDate endDate;
//     private LocalTime startTime;
//     private LocalTime endTime;
//     private String emailContact;
//     private Long amountFreeTickets;
//     private Long amountPayedTickets;
//     private Long freeTicketsSelled;
//     private Long payedTicketsSelled;
//     private Double priceTicket;
    
//     public EventDTO() {
//     }

    



//     public EventDTO(Long id, String name, String description, LocalDate startDate, LocalDate endDate,
//             LocalTime startTime, LocalTime endTime, String emailContact, Long amountFreeTickets,
//             Long amountPayedTickets, Long freeTicketsSelled, Long payedTicketsSelled, Double priceTicket) {
//         this.id = id;
//         this.name = name;
//         this.description = description;
//         this.startDate = startDate;
//         this.endDate = endDate;
//         this.startTime = startTime;
//         this.endTime = endTime;
//         this.emailContact = emailContact;
//         this.amountFreeTickets = amountFreeTickets;
//         this.amountPayedTickets = amountPayedTickets;
//         this.freeTicketsSelled = freeTicketsSelled;
//         this.payedTicketsSelled = payedTicketsSelled;
//         this.priceTicket = priceTicket;
//     }

//     public EventDTO(Event event) {
//         this.id = event.getId();
//         this.name = event.getName();
//         this.description = event.getDescription();
//         this.startDate = event.getStartDate();
//         this.endDate = event.getEndDate();
//         this.startTime = event.getStartTime();
//         this.endTime = event.getEndTime();
//         this.emailContact = event.getEmailContact();
//         this.amountFreeTickets = event.getAmountFreeTickets();
//         this.amountPayedTickets = event.getAmountPayedTickets();
//         this.freeTicketsSelled = 0L;
//         this.payedTicketsSelled = 0L;
//         this.priceTicket = event.getPriceTicket();
//     }

    
// }
