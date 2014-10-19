package smsapp.rest.db;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Set;
public class ConnectToMongoDB {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub


		// To directly connect to a single MongoDB server (note that this will not auto-discover the primary even
		// if it's a member of a replica set:
		//MongoClient mongoClient = new MongoClient();
		// or
		//MongoClient mongoClient = new MongoClient( "localhost" );
		// or
		MongoClient mongoClient = new MongoClient( "192.168.1.20" , 27017 );
		// or, to connect to a replica set, with auto-discovery of the primary, supply a seed list of members
		//MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27017),
		//                                      new ServerAddress("localhost", 27018),
		//                                     new ServerAddress("localhost", 27019)));

		DB db = mongoClient.getDB( "mydb" );
		
		/*
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("test");
		*/
		//boolean auth = db.authenticate(myUserName, myPassword);
		Set<String> colls = db.getCollectionNames();

		for (String s : colls) {
		    System.out.println(s);
		}
		
		//DBCollection coll = db.getCollection("testCollection");
		DBCollection coll = db.getCollection("testData");
		
		mongoClient.setWriteConcern(WriteConcern.JOURNALED);
		/*
		BasicDBObject doc = new BasicDBObject("name", "MongoDB").
                append("type", "database").
                append("count", 1).
                append("info", new BasicDBObject("x", 203).append("y", 102));

		coll.insert(doc);
		*/
		DBObject myDoc = coll.findOne();
		System.out.println(myDoc);
		/*
		for (int i=0; i < 100; i++) {
		    coll.insert(new BasicDBObject("i", i));
		}
		*/
		System.out.println(coll.getCount());

		DBCursor cursor = coll.find();
		try {
		   while(cursor.hasNext()) {
		       System.out.println(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
		/*
		BasicDBObject query = new BasicDBObject("i", 71);

		cursor = coll.find(query);
		
		try {
		   while(cursor.hasNext()) {
		       System.out.println(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
		*/
		
		BasicDBObject query = new BasicDBObject("j", new BasicDBObject("$ne", 3)).
                append("k", new BasicDBObject("$gt", 10));

		cursor = coll.find(query);
		
		try {
		while(cursor.hasNext()) {
		System.out.println(cursor.next());
		}
		} finally {
		cursor.close();
		}
		
	}

}
