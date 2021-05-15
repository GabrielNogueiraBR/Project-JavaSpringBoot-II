package br.facens.projectjavaspringboot.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="TB_ATTEND")
@PrimaryKeyJoinColumn(name="USER_ID")
public class Attend extends BaseUser{

    private Double balance;

    @OneToMany(mappedBy = "attend")
    private List<Ticket> tickets = new ArrayList<>();

    public Attend(){
        
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public Attend(Double balance) {
        this.balance = balance;
    }

    public Attend(Long id, String name, String email, Double balance) {
        super(id, name, email);
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
    
}
