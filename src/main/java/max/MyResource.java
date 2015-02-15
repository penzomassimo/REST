package max;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

        List<Product> myList = new ArrayList<Product>();

        Product p1 = new Product("Camera0","Best Camera0",101);
        Product p2 = new Product("Camera1","Best Camera1",220);
        Product p3 = new Product("Camera2","Best Camera2",002);
        Product p4 = new Product("Camera3","Best Camera3",003);
        Product p5 = new Product("Camera4","Best Camera4",004);
        Product p6 = new Product("Camera5","Best Camera4",005);
        Product p7 = new Product("Camera6","Best Camera4",006);

        myList.add(p1);
        myList.add(p2);
        myList.add(p3);
        myList.add(p4);
        myList.add(p5);
        myList.add(p6);
        myList.add(p7);


        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        String json = gson.toJson(myList);



        return json;


        /*Product p1 = new Product("Camera","The best camera",200);
        System.out.println("the product has been created");
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(p1);
        return json;*/
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

            // create a list to store all messages
            List<CustomMessage> list = new ArrayList<CustomMessage>();

            for (int i = 0, n = 10; i < n; i++) {

                CustomMessage ms = new CustomMessage();
                ms.setSubject(messages[i].getSubject());
                ms.setDate("May 24th, 2014");
                list.add(ms);

            }

            // creating JSON file
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String json = gson.toJson(list);

            // close the store and folder objects
            emailFolder.close(false);
            store.close();

            return json;

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "problem";
    }

    @GET
    @Path("send_email")
    @Produces("application/json")
    public String sendMail() {
        Properties props = new Properties();
        Session session = Session.getInstance(props);
        MimeMessage msg = new MimeMessage(session);
        Transport t = null;
        try {
            Address massimo = new InternetAddress("penzo.massimo@gmail.com", "CHUCKY");
            Address alma = new InternetAddress("almavcarreras@gmail.com");
            msg.setText("Resistance is futile. You will be assimilated!");
            msg.setFrom(massimo);
            msg.setRecipient(Message.RecipientType.TO, alma);
            msg.setSubject("You must comply.");
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
