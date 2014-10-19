package smsapp.rest.ws;

import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  

import javax.ws.rs.GET;  
import javax.ws.rs.PUT;
import javax.ws.rs.Path;  
import javax.ws.rs.PathParam;  
import javax.ws.rs.Produces;  
import javax.ws.rs.Consumes;

import org.bson.BSONObject;
import org.bson.types.ObjectId;

import smsapp.rest.bean.smsBean;
import smsapp.rest.bean.smsDBean;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Set;


@Path("/webservice")  
public class WebController {
    
	@GET  
    @Path("/hello")  
    @Produces("text/plain")  
    public String hello(){  
        return "Hello World!!! smsapp";      
    }  	
	
    @GET  
    @Path("/message/{message}")  
    @Produces("text/plain")  
    public String showMsg(@PathParam("message") String message){  
        return message;      
    }  
    
    //20140303，這先用STRING傳送，之後會自動轉換成JSON
    @GET  
    @Path("/smsapp/{messageno}")  
    @Produces("application/json")
    //@Produces("text/plain")  
    //public DBObject getMsgText(@PathParam("messageno") String messageno) throws UnknownHostException{
   	//public smsBean getMsgText(@PathParam("messageno") String messageno) throws UnknownHostException{
    public String getMsgText(@PathParam("messageno") String messageno) throws UnknownHostException{
    	System.out.println("getMsgText begin ..... " + messageno + ".");
    	DBObject rt = new BasicDBObject();
    	//JSON js = new JSON();
    	//smsBean rSb = new smsBean();
    	String rtStr = "";
    	MongoClient mongoClient = new MongoClient( "192.168.1.20" , 27017 );
    	DB db = mongoClient.getDB( "mydb" );
		DBCollection coll = db.getCollection("smsData");
		
		mongoClient.setWriteConcern(WriteConcern.JOURNALED); 
		BasicDBObject query = new BasicDBObject("UUID", messageno);
		
		DBCursor cursor = coll.find(query);
		System.out.println("query ....");
		try {
			System.out.println("count: " + cursor.count() + ".");
			System.out.println("cursor.hasNext(): " + cursor.hasNext() + ".");
			if (cursor.count() == 1)
			{
			   rt = (BasicDBObject)cursor.next();
			   //rSb.makePojoFromBson(rt);
			   rtStr += JSON.serialize((Object)rt);
			   System.out.println("There is a only one record here. rtStr: " + rtStr + ".");
				
			} else if (cursor.count() > 1)
			{
				rtStr += "[";
				while(cursor.hasNext()) {
					   rt = (BasicDBObject)cursor.next();
					   //rSb.makePojoFromBson(rt);
					   rtStr += JSON.serialize((Object)rt) + ",";
					   
					   //rtStr = rSb.getSmsBeanString(); 
				       //System.out.println(rSb.get_id().toString() + ", " + rSb.getMessageno() + "," + rSb.getStatus() + "." );				
				}
				rtStr = rtStr.substring(0, rtStr.length()-1) + "]";
				System.out.println("rtStr: " + rtStr + ".");
		     }
		   //js = (JSON) JSON.parse(rtStr);
		} catch (Exception e){
			System.out.print("Exception: " + e.toString());
    	}finally {
		   cursor.close();
		}
		//System.out.println("rSb._id: " + rSb.get_id().toString() + ".");
		//System.out.println("rSb: " + rSb.toString() + ".");
		//return rSb;
		return rtStr;
		//return rt;
		//return js;
        //return new ArrayList<Employee>(employees.values());  
    }  	

    //提供給IOS以外的系統使用，因為nsurlconnectio只支援get與post的方式
    @PUT 
    //@Consumes("text/plain")
    @Path("/replyapp/{smsbean}") 
    @Produces("text/plain")  
    public String replyMsg(@PathParam("smsbean") String smsbean) throws UnknownHostException{  
        System.out.println("replyMsg begin .....");
        String rtStr = "";
        try{
            DBObject dbo = (DBObject) JSON.parse(smsbean);
            System.out.println(dbo.toString());
            smsBean sms = new smsBean();
            sms.searchPojoFromBson(dbo);
            System.out.println("sms._id: " + sms.get_id() + ", response: " + sms.getResponse() + ", " + sms.getResponsetime() + ".");
            //update db
        	MongoClient mongoClient = new MongoClient( "192.168.1.20" , 27017 );
        	DB db = mongoClient.getDB( "mydb" );
    		DBCollection coll = db.getCollection("wsData");
    		
    		mongoClient.setWriteConcern(WriteConcern.JOURNALED);
            String idString = sms.get_id().toString();
            DBObject searchById = new BasicDBObject("_id", new ObjectId(idString));
            DBObject update  = new BasicDBObject().append("$set", 
            				   new BasicDBObject().append("response",sms.getResponse()).append("responsetime", sms.getResponsetime()));
            coll.update(searchById, update);
            rtStr = "replied successfully!";
        } catch (Exception e){
        	//System.out.println(e.printStackTrace());
        	System.out.println("Some Exception here");
        	rtStr = e.toString();
        	System.out.print(rtStr);
        }

    	return rtStr; 
        
   }  
    
    //提供給IOS以外的系統使用，因為nsurlconnectio只支援get與post的方式
    @GET//之後調整成為put
    //@Consumes("text/plain")
    @Path("/replyIOSapp/{smsbean}") 
    @Produces("text/plain")  
    public String replyIOSMsg(@PathParam("smsbean") String smsbean) throws UnknownHostException{  
        System.out.println("replyIOSMsg begin .....");
        String rtStr = "";
        try{
            DBObject dbo = (DBObject) JSON.parse(smsbean);
            System.out.println(dbo.toString());
            smsBean sms = new smsBean();
            sms.searchPojoFromBson(dbo);
            System.out.println("sms._id: " + sms.get_id() + ", response: " + sms.getResponse() + ", " + sms.getResponsetime() + ".");
            //update db
        	MongoClient mongoClient = new MongoClient( "192.168.1.20" , 27017 );
        	DB db = mongoClient.getDB( "mydb" );
    		DBCollection coll = db.getCollection("wsData");
    		
    		mongoClient.setWriteConcern(WriteConcern.JOURNALED);
            String idString = sms.get_id().toString();
            DBObject searchById = new BasicDBObject("_id", new ObjectId(idString));
            DBObject update  = new BasicDBObject().append("$set", 
            				   new BasicDBObject().append("response",sms.getResponse()).append("responsetime", sms.getResponsetime()));
            coll.update(searchById, update);
            rtStr = "replied successfully!";
        } catch (Exception e){
        	//System.out.println(e.printStackTrace());
        	System.out.println("Some Exception here");
        	rtStr = e.toString();
        	System.out.print(rtStr);
        }
        System.out.println("replyIOSMsg end .....");
    	return rtStr;         
   }      
}
