package jp.antonsibgatulin.messager.message;

import jp.antonsibgatulin.messager.user.User;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private Dilog dilog;
    private Long fromId;

    private String message;

    public Message(){

    }


    public Message(Dilog dilog, User fromUser,String message){
        this.message = message;
        this.dilog = dilog;
        this.fromId = fromUser.getId();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dilog getDilog() {
        return dilog;
    }

    public void setDilog(Dilog dilog) {
        this.dilog = dilog;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
