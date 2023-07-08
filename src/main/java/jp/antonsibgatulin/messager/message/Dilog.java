package jp.antonsibgatulin.messager.message;

import jp.antonsibgatulin.messager.user.User;

import javax.persistence.*;

@Entity
public class Dilog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn
    private User user_1;

    @ManyToOne
    @JoinColumn
    private User user_2;


    public Dilog(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser_1() {
        return user_1;
    }

    public void setUser_1(User user_1) {
        this.user_1 = user_1;
    }

    public User getUser_2() {
        return user_2;
    }

    public void setUser_2(User user_2) {
        this.user_2 = user_2;
    }
}
