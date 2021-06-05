package br.facens.projectjavaspringboot.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.facens.projectjavaspringboot.dto.EventInsertDTO;
import br.facens.projectjavaspringboot.dto.EventUpdateDTO;

// import br.facens.projectjavaspringboot.dto.EventInsertDTO;

@Entity
@Table(name="TB_EVENT")
public class Event implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String emailContact;
    private Long amountFreeTickets;
    private Long amountPayedTickets;
    private Double priceTicket;

    @ManyToOne()
    @JoinColumn(name="ADMIN_USER_ID")
    private Admin admin;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @ManyToMany()
    @JoinTable(name="TB_EVENT_PLACE",
            joinColumns = @JoinColumn(name="EVENT_ID"),
            inverseJoinColumns = @JoinColumn(name="PLACE_ID")
        )
    private List<Place> places = new ArrayList<>();
    
    public Event() {

    }

    public Event(EventInsertDTO insertDto, Admin admin) {
        this.name = insertDto.getName();
        this.description = insertDto.getDescription();
        this.startDate = insertDto.getStartDate();
        this.endDate = insertDto.getEndDate();
        this.startTime = insertDto.getStartTime();
        this.endTime = insertDto.getEndTime();
        this.emailContact = insertDto.getEmailContact();
        this.amountFreeTickets = insertDto.getAmountFreeTickets();
        this.amountPayedTickets = insertDto.getAmountPayedTickets();
        this.priceTicket = insertDto.getPriceTicket();
        this.admin = admin;
    }
    
    public Event(Long id, String name, String description, LocalDate startDate, LocalDate endDate, LocalTime startTime,
            LocalTime endTime, String emailContact, Long amountFreeTickets, Long amountPayedTickets,
            Double priceTicket) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.emailContact = emailContact;
        this.amountFreeTickets = amountFreeTickets;
        this.amountPayedTickets = amountPayedTickets;
        this.priceTicket = priceTicket;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket){
        if(this.tickets.contains(ticket)){
            this.tickets.remove(ticket);
        }
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void addPlace(Place place){
        this.places.add(place);
    }

    public boolean removePlace(Place place){
        if(places.contains(place)){
            return places.remove(place);
        }
        return false;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public Long getAmountFreeTickets() {
        return amountFreeTickets;
    }

    public void setAmountFreeTickets(Long amountFreeTickets) {
        this.amountFreeTickets = amountFreeTickets;
    }

    public Long getAmountPayedTickets() {
        return amountPayedTickets;
    }

    public void setAmountPayedTickets(Long amountPayedTickets) {
        this.amountPayedTickets = amountPayedTickets;
    }

    public Double getPriceTicket() {
        return priceTicket;
    }

    public void setPriceTicket(Double priceTicket) {
        this.priceTicket = priceTicket;
    }

    public Long freeTicketsSelled(){
        int freeTicketsSelled = 0;

        for(Ticket ticket : this.tickets){
            if(ticket.getType() == TicketType.FREE){
                freeTicketsSelled++;
            }
        }

        return Long.valueOf(freeTicketsSelled);
    }

    public Long payedTicketsSelled(){
        int payedTicketsSelled = 0;

        for(Ticket ticket : this.tickets){
            if(ticket.getType() == TicketType.PAYED){
                payedTicketsSelled++;
            }
        }

        return Long.valueOf(payedTicketsSelled);
    }

    public boolean isTicketsAvailable(TicketType type){
        
        Long amountTickets;
        Long ticketsSelled;

        if(type == TicketType.FREE){
            amountTickets = this.getAmountFreeTickets();
            ticketsSelled = this.freeTicketsSelled();
        }
        else{
            amountTickets = this.getAmountPayedTickets();
            ticketsSelled = this.payedTicketsSelled();
        }

        if(amountTickets - ticketsSelled > 0){
            return true;
        }
        
        return false;
    }

    public boolean isEventInPast(){
        
        LocalDateTime localDateTime = this.getEndDate().atTime(this.getEndTime());
        
        if(localDateTime.isBefore(LocalDateTime.now())){
            return true;
        }

        return false;
    }

    public boolean isEventBegin(){
        
        LocalDateTime localDateTime = this.getStartDate().atTime(this.getStartTime());
        
        if(localDateTime.isBefore(LocalDateTime.now())){
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Event other = (Event) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public void update(EventUpdateDTO updateDTO, Admin admin2) {
        this.name = updateDTO.getName();
        this.description = updateDTO.getDescription();
        this.startDate = updateDTO.getStartDate();
        this.endDate = updateDTO.getEndDate();
        this.startTime = updateDTO.getStartTime();
        this.endTime = updateDTO.getEndTime();
        this.emailContact = updateDTO.getEmailContact();
        this.amountFreeTickets = updateDTO.getAmountFreeTickets();
        this.amountPayedTickets = updateDTO.getAmountPayedTickets();
        this.priceTicket = updateDTO.getPriceTicket();
        this.admin = admin2;
    }

}
