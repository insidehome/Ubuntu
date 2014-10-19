package smsapp.rest.bean;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import org.bson.BSONObject;
import org.bson.types.ObjectId;

import com.mongodb.DBObject;
import com.mongodb.BasicDBObject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class smsDBean implements DBObject{
	
	//private fields
	private ObjectId _id;
	private String name;
	private String v;
	

	public smsDBean() {
		// TODO Auto-generated constructor stub
	}


	
	public smsDBean(String name, String value) {
		// TODO Auto-generated constructor stub
		this.name =name;
		this.v=value;
	}	
	
	public ObjectId getID() {return this._id;}
	public void SetId(ObjectId _id) {this._id = _id;}
	public void generateId() {if (this._id == null) this._id = new ObjectId();}
	
	public String getName() {return this.name;}
	public void setName(String name) {this.name = name;}
	public String getV() {return this.v;}
	public void setV(String value) {this.v = value;}
	
	public DBObject bsonFromPojo()
	{
		BasicDBObject document = new BasicDBObject();
		
		document.put("_id",	this._id);
		document.put("name", this.name);
		document.put("v", this.v);
		
		return document;		
	}
	
	public void makePojoFromBson(DBObject bson)
	{
		BasicDBObject b = (BasicDBObject) bson;
		
		this._id =(ObjectId) b.getObjectId("_id");
		this.name = (String) b.get("name");
		this.v = (String) b.get("v");
	}
	
	
	
	@Override
	public boolean containsField(String arg0) {
		// TODO Auto-generated method stub
		//return false;
		return(arg0.equals("_id")
				|| arg0.equals("name")
				|| arg0.equals("v")
		);
	}

	@Override
	public boolean containsKey(String arg0) {
		// TODO Auto-generated method stub
		//return false;
		return containsField(arg0);
	}

	@Override
	public Object get(String arg0) {
		// TODO Auto-generated method stub
		//
		if (arg0.equals("_id")) return this._id;
		if (arg0.equals("name")) return this.name;
		if (arg0.equals("v")) return this.v;
		return null;
	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		//return null;
		Set<String> set = new HashSet<String>();
		set.add("_id");
		set.add("name");
		set.add("v");
		return set;
	}

	@Override
	public Object put(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		//
		if(arg0.equals("_id")) 
		{
			this._id = (ObjectId) arg1;
			return arg1;
		}
		if(arg0.equals("name")) 
		{
			this.name = (String) arg1;
			return arg1;
		}
		if(arg0.equals("v")) 
		{
			this.v = (String) arg1;
			return arg1;
		}
		return null;
	}

	@Override
	public void putAll(BSONObject arg0) {
		// TODO Auto-generated method stub
		for(String key: arg0.keySet())
			put(key, arg0.get(key));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void putAll(@SuppressWarnings("rawtypes") Map arg0) {
		// TODO Auto-generated method stub
		for (Map.Entry<String, Object> entry:(Set<Map.Entry<String, Object>>) arg0.entrySet())
			put (entry.getKey().toString(), entry.getValue());
	}

	
	@Override
	public Object removeField(String arg0) {
		// TODO Auto-generated method stub
		//return null;
		throw new RuntimeException("Unsupported method.");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map toMap() {
		// TODO Auto-generated method stub
		//return null;
		Map<String, Object> map = new HashMap<String,Object>();
		
		if (this._id != null) map.put("_id", this._id);
		if (this.name != null) map.put("name", this.name);
		if (this.v != null) map.put("v", this.v);
		
		return map;
	}

	@Override
	public boolean isPartialObject() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void markAsPartialObject() {
		// TODO Auto-generated method stub
		throw new RuntimeException("Method not implemented.");
	}

}
