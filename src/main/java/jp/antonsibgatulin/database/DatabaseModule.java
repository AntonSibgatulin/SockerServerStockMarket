package jp.antonsibgatulin.database;

import jp.antonsibgatulin.main.Main;
import jp.antonsibgatulin.messager.message.Dilog;
import jp.antonsibgatulin.messager.message.Message;
import jp.antonsibgatulin.messager.user.Token;
import org.hibernate.Query;
import org.hibernate.Session;

public class DatabaseModule {



    public DatabaseModule(){

    }



    public Token authByToken(String token){
        Session session = Main.getSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Token t WHERE t.token = :token");
        query.setString("token",token);

        var tokenModel = (Token) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return tokenModel;
    }


    public Dilog getDilogById(long dilogId) {

        var session = Main.getSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Dilog d WHERE d.id = :id");
        query.setLong("id",dilogId);

        var dilog = (Dilog)query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        return dilog;

    }

    public <T> void save(T t) {
        var session = Main.getSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
    }
}
