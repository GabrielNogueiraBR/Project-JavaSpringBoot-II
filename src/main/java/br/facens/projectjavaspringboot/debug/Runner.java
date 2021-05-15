package br.facens.projectjavaspringboot.debug;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.type.SetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.facens.projectjavaspringboot.entities.Admin;
import br.facens.projectjavaspringboot.entities.Attend;
import br.facens.projectjavaspringboot.entities.Event;
import br.facens.projectjavaspringboot.entities.Place;
import br.facens.projectjavaspringboot.entities.Ticket;
import br.facens.projectjavaspringboot.entities.TicketType;
import br.facens.projectjavaspringboot.repositories.AdminRepository;
import br.facens.projectjavaspringboot.repositories.AttendRepository;
import br.facens.projectjavaspringboot.repositories.EventRepository;
import br.facens.projectjavaspringboot.repositories.PlaceRepository;
import br.facens.projectjavaspringboot.repositories.TicketRepository;

@Service
public class Runner implements CommandLineRunner {
    
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AttendRepository attendRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        
        System.out.println("*************** Inicio do Runner! ************");
        System.out.println("*************** Inicio do Transctional ************");

        createAdmins();
        createEvents();
        createAttendees();
        createPlaces();
        associationEventAndPlace();
        associationAttendeesAndTicketsAndEvent();
        updateEvent();


        System.out.println("*************** Fim do Transctional ************");
        System.out.println("*************** Fim do Runner! ************");
        
    }

    public void createAdmins(){
        Admin admin1 = new Admin();

        admin1.setName("Gabriel Nogueira");
        admin1.setEmail("gnogueira");
        admin1.setPhoneNumber("1234");

        adminRepository.save(admin1);

        Admin admin2 = new Admin();
        admin2.setName("Gabriel Augusto");
        admin2.setEmail("gaugusto");
        admin2.setPhoneNumber("5678");

        adminRepository.save(admin2);
    }

    public void createEvents(){
        Admin admin1 = adminRepository.findById(1L).get();

        Event event = new Event();
        event.setName("Festival da Uva");
        event.setDescription("Festival da uva em Votorantim");
        LocalDate startDate = LocalDate.of(2021, 5, 14);
        event.setStartDate(startDate);
        LocalDate endDate = LocalDate.of(2021, 5, 20);
        event.setEndDate(endDate);
        LocalTime startTime = LocalTime.of(20, 00, 00);
        event.setStartTime(startTime);
        LocalTime endTime = LocalTime.of(23,00,00);
        event.setEndTime(endTime);
        event.setEmailContact("festivaluva@gmail.com");
        event.setAmountFreeTickets(10L);
        event.setAmountPayedTickets(120L);
        event.setPriceTicket(20.3);
        event.setAdmin(admin1);

        eventRepository.save(event);

        Admin admin2 = adminRepository.findById(2L).get();

        Event event2 = new Event();
        event2.setName("Grupo de Oração");
        event2.setDescription("Grupo de Oração Jovem");
        LocalDate startDate2 = LocalDate.of(2021, 5, 15);
        event2.setStartDate(startDate2);
        LocalDate endDate2 = LocalDate.of(2021, 5, 15);
        event2.setEndDate(endDate2);
        LocalTime startTime2 = LocalTime.of(19, 00, 00);
        event2.setStartTime(startTime2);
        LocalTime endTime2 = LocalTime.of(20,30,00);
        event2.setEndTime(endTime2);
        event2.setEmailContact("grupo@gmail.com");
        event2.setAmountFreeTickets(60L);
        event2.setAmountPayedTickets(0L);
        event2.setPriceTicket(0.0);
        event2.setAdmin(admin2);

        eventRepository.save(event2);

        Event event3 = new Event();
        event3.setName("Santa Missa");
        event3.setDescription("Santa Missa na paróquia São José");
        LocalDate startDate3 = LocalDate.of(2021, 5, 16);
        event3.setStartDate(startDate3);
        LocalDate endDate3 = LocalDate.of(2021, 5, 16);
        event3.setEndDate(endDate3);
        LocalTime startTime3 = LocalTime.of(8, 00, 00);
        event3.setStartTime(startTime3);
        LocalTime endTime3 = LocalTime.of(9,00,00);
        event3.setEndTime(endTime3);
        event3.setEmailContact("paroquia@gmail.com");
        event3.setAmountFreeTickets(60L);
        event3.setAmountPayedTickets(0L);
        event3.setPriceTicket(0.0);
        event3.setAdmin(admin2);

        eventRepository.save(event3);
    }

    public void createAttendees(){
        Attend attend1 = new Attend();
        attend1.setName("Maria Luisa");
        attend1.setEmail("malu@gmail.com");
        attend1.setBalance(124.45);

        attendRepository.save(attend1);

        Attend attend2 = new Attend();
        attend2.setName("Jose Augusto");
        attend2.setEmail("jaugusto@gmail.com");
        attend2.setBalance(60.4);

        attendRepository.save(attend2);
    }

    public void createPlaces(){
        Place p1 = new Place();

        p1.setName("Uveira de Votorantim");
        p1.setAddress("Rua das Uvas");

        placeRepository.save(p1);

        Place p2 = new Place();
        p2.setName("Igreja São José - Votorantim");
        p2.setAddress("R. Amirtes Luvison, s/n - Chave, Votorantim - SP, 18114-060");

        placeRepository.save(p2);
    }

    public void associationEventAndPlace(){
        // Recuperando o primeiro evento - Uva
        Event e1 = eventRepository.findById(1L).get();

        // Recuperando o segundo evento - Grupo de Oracao
        Event e2 = eventRepository.findById(2L).get();

        // Recuperando o terceiro evento - Santa Missa
        Event e3 = eventRepository.findById(3L).get();

        
        Place p1 = placeRepository.findById(1L).get();
        Place p2 = placeRepository.findById(2L).get();

        e1.addPlace(p1);
        e2.addPlace(p2);
        e3.addPlace(p2);

        eventRepository.save(e1);
        eventRepository.save(e2);
        eventRepository.save(e3);
    }

    public void associationAttendeesAndTicketsAndEvent(){
        Attend a1 = attendRepository.findById(3L).get();
        Attend a2 = attendRepository.findById(4L).get();

        Event e1 = eventRepository.findById(1L).get();
        Event e2 = eventRepository.findById(2L).get();
        Event e3 = eventRepository.findById(3L).get();

        Ticket t1 = new Ticket();
        t1.setAttend(a1);
        t1.setEvent(e1);
        t1.setType(TicketType.PAYED);
        t1.setDate(Instant.now());
        t1.setPrice(e1.getPriceTicket());

        ticketRepository.save(t1);

        Ticket t2 = new Ticket();
        t2.setAttend(a1);
        t2.setEvent(e2);
        t2.setType(TicketType.FREE);
        t2.setDate(Instant.now());
        t2.setPrice(e2.getPriceTicket());

        ticketRepository.save(t2);

        Ticket t3 = new Ticket();
        t3.setAttend(a1);
        t3.setEvent(e3);
        t3.setType(TicketType.FREE);
        t3.setDate(Instant.now());
        t3.setPrice(e3.getPriceTicket());

        ticketRepository.save(t3);

        Ticket t4 = new Ticket();
        t4.setAttend(a2);
        t4.setEvent(e3);
        t4.setType(TicketType.FREE);
        t4.setDate(Instant.now());
        t4.setPrice(e3.getPriceTicket());

        ticketRepository.save(t4);

    }

    public void updateEvent(){
        Event e1 = eventRepository.findById(1L).get();
        e1.setPriceTicket(100.50);

        eventRepository.save(e1);
    }
}
