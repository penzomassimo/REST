package max;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

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
}
