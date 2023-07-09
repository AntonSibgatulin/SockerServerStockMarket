package jp.antonsibgatulin.messager.user;

import org.hibernate.annotations.Immutable;
import org.java_websocket.WebSocket;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Immutable
public class User implements Serializable {


    @Id
    private Long id;

    private String email;



    private Boolean ban = false;

    @Column(name = "delete_account")
    private Boolean delete = false;



    @Transient
    private WebSocket webSocket;


    public User() {
    }

    public void send(String message) {
        if (webSocket != null && !webSocket.isClosing() && webSocket.isConnecting())
            webSocket.send(message);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getBan() {
        return ban;
    }

    public void setBan(Boolean ban) {
        this.ban = ban;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }
}
