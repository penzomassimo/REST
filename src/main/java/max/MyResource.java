package max;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Path("storeRESTendpoint")
public class MyResource {

    @GET
    @Path("man_catalog")
    @Produces("application/json")
    public String getManProducts() {

        /* HIBERNATE PROGRAMMING MODEL */
/*
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
*/

        org.hibernate.Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        /*org.hibernate.Session session = sessionFactory.openSession();*/
        session.beginTransaction();
        String hql = "FROM ManProduct";
        Query query = session.createQuery(hql);
        List results = query.list();
        session.getTransaction().commit();
        /*CREATING THE JSON STRING*/
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(results);

        return json;
    }

    @GET
    @Path("add_man_product")
    @Consumes("application/json")
    @Produces("application/json")
    public String addNewManProduct(final String jsonData){

        ManProduct p = new ManProduct("iMac3333","Best computer", 255.99);

		/* HIBERNATE PROGRAMMING MODEL */
/*
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
*/
        org.hibernate.Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        /*org.hibernate.Session session = sessionFactory.openSession();*/
        session.beginTransaction();
        session.save(p);
        session.getTransaction().commit();

        return "Your MAN product has been saved";
    }

    @GET
    @Path("woman_catalog")
    @Produces("application/json")
    public String getWomanProducts() {

        /* HIBERNATE PROGRAMMING MODEL */
/*
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
*/

        org.hibernate.Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        /*org.hibernate.Session session = sessionFactory.openSession();*/
        session.beginTransaction();
        String hql = "FROM WomanProduct";
        Query query = session.createQuery(hql);
        List results = query.list();
        session.getTransaction().commit();
        /*CREATING THE JSON STRING*/
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(results);

        return json;
    }

    @GET
    @Path("add_woman_product")
    @Consumes("application/json")
    @Produces("application/json")
    public String addNewWomanProduct(final String jsonData){


        WomanProduct p = new WomanProduct("iMac","Best computer", 255.99);

		/* HIBERNATE PROGRAMMING MODEL */
/*
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
*/

        org.hibernate.Session session = HibernateUtil.getSessionFactory().getCurrentSession();
/*
        org.hibernate.Session session = sessionFactory.openSession();
*/
        session.beginTransaction();
        session.save(p);
        session.getTransaction().commit();

        return "Your WOMAN product has been saved";
    }

    @GET
    @Path("kid_catalog")
    @Produces("application/json")
    public String getKidProducts() {

        /* HIBERNATE PROGRAMMING MODEL */
/*
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
*/

        org.hibernate.Session session = HibernateUtil.getSessionFactory().getCurrentSession();
/*
        org.hibernate.Session session = sessionFactory.openSession();
*/
        session.beginTransaction();
        String hql = "FROM KidProduct";
        Query query = session.createQuery(hql);
        List results = query.list();
        session.getTransaction().commit();
        /*CREATING THE JSON STRING*/
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(results);

        return json;
    }

    @GET
    @Path("add_kid_product")
    @Consumes("application/json")
    @Produces("application/json")
    public String addNewKidProduct(final String jsonData){


        KidProduct p = new KidProduct("iMac","Best computer", 255.99);

		/* HIBERNATE PROGRAMMING MODEL */
/*
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
*/

        org.hibernate.Session session = HibernateUtil.getSessionFactory().getCurrentSession();
/*
        org.hibernate.Session session = sessionFactory.openSession();
*/
        session.beginTransaction();
        session.save(p);
        session.getTransaction().commit();

        return "Your KID product has been saved";
    }

    @GET
    @Path("send_order")
    @Produces("application/json")
    public String sendMailOrder(){
        try {

            String host = "pop.gmail.com";
            String username = "penzo.massimo@gmail.com";
            String password = "electrica913";

            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");

            Session emailSession = Session.getDefaultInstance(properties);

            // create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");
            store.connect(host, username, password);

            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            /*CREATE A LIST TO STORE ALL THE MESSAGES*/
            List<CustomMessage> list = new ArrayList<>();

            for (int i = 0, n = 10; i < n; i++) {
                CustomMessage ms = new CustomMessage();
                ms.setSubject(messages[i].getSubject());
                ms.setDate("May 24th, 2014");
                list.add(ms);
            }

            /*CREATING THE JSON STRING THAT WILL BE SENT*/
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String json = gson.toJson(list);

            // close the store and folder objects
            emailFolder.close(false);
            store.close();

            return json;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "problem";
    }

    @POST
    @Path("send_email")
    @Consumes("application/json")
    @Produces("application/json")
    public String sendMail(final String jsonData) {

        Properties props = new Properties();
        Session session = Session.getInstance(props);
        MimeMessage msg = new MimeMessage(session);
        Transport t = null;
        try {
            Address massimo = new InternetAddress("penzo.massimo@gmail.com", "ONLINE SALES");
            Address alma = new InternetAddress("penzo.massimo@gmail.com");
            msg.setText(jsonData);
            msg.setFrom(massimo);
            msg.setRecipient(Message.RecipientType.TO, alma);
            msg.setSubject("NEW Purchase Order");
            t = session.getTransport("smtps");
            t.connect("smtp.gmail.com", "penzo.massimo@gmail.com", "electrica913");
            t.sendMessage(msg, msg.getAllRecipients());
            return "email sent";
        } catch (MessagingException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        finally {
            if (t != null) {
                try {
                    t.close();
                } catch (MessagingException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return "closed";
    }

}
