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
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Path("storeRESTendpoint")
public class MyResource {

    @GET
    @Path("man_catalog")
    @Produces("application/json")
    public String getManCatalog() {
        List<Product> myList = new ArrayList<>();
        Product p1 = new Product("Camera0","Best Camera0",555555);
        Product p2 = new Product("Camera1","Best Camera1",220);
        Product p3 = new Product("Camera2","Best Camera2", 2);
        Product p4 = new Product("Camera3","Best Camera3", 3);
        Product p5 = new Product("Camera4","Best Camera4", 4);
        Product p6 = new Product("Camera5","Best Camera4", 5);
        Product p7 = new Product("Camera6","Best Camera4", 6);
        myList.add(p1);
        myList.add(p2);
        myList.add(p3);
        myList.add(p4);
        myList.add(p5);
        myList.add(p6);
        myList.add(p7);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(myList);
    }

    @GET
    @Path("woman_catalog")
    @Produces(MediaType.TEXT_PLAIN)
    public String getWomanCatalog() {
        return "this is the WOMAN CATALOG";
    }

    @GET
    @Path("kid_catalog")
    @Produces(MediaType.TEXT_PLAIN)
    public String getKidCatalog() {
        return "this is the KID CATALOG";
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

    @GET
    @Produces("text/plain")
    @Path("/add_doctor")
    public String addDoctors() {
		/* CREATING DOCTOR OBJECTS */
        Doctor doc1 = new Doctor();
        Doctor doc2 = new Doctor();
        Doctor doc3 = new Doctor();
        Doctor doc4 = new Doctor();
		/* SETTING PROPERTIES */
        doc1.setDoctor_name("Mary");
        doc1.setDoctor_lastname("Munter");

        doc2.setDoctor_name("Virginia");
        doc2.setDoctor_lastname("Johnson");

        doc3.setDoctor_name("Pedro");
        doc3.setDoctor_lastname("Martinez");

        doc4.setDoctor_name("Alma");
        doc4.setDoctor_lastname("Carreras");

		/* HIBERNATE PROGRAMMING MODEL */
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        org.hibernate.Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(doc1);
        session.save(doc2);
        session.save(doc3);
        session.save(doc4);
        session.getTransaction().commit();

        return "YOUR DOCTORS HAVE BEEN SAVED";
    }

    @GET
    @Path("/generic")
    @Produces("application/json")
    public String getGenericProducts(){

        /* HIBERNATE PROGRAMMING MODEL */
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        org.hibernate.Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "SELECT doctor_id, doctor_name FROM Doctor";
        Query query = session.createQuery(hql);
        List results = query.list();
        session.getTransaction().commit();
        /*CREATING THE JSON STRING*/
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(results);

        return json;
    }

}
