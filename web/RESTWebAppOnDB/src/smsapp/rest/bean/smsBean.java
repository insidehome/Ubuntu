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
public class smsBean implements DBObject{
//public class smsBean extends BasicDBObject{	
	//private fields
	//ObjectId
	//private static final long serialVersionUID = 1L;
	private ObjectId _id;
	//head
	private String messageno = "";
	private String status = "";
	private String occurtime = "";
	//drinfo
	private String drno = "";
	private String drmobile = "";
	private String devicenumber = "";
	//patinfo
	private String patname = "";
	private String patno = "";
	private String bedno = "";
	//labinfo
	private String orderno = "";
	private String requestno = "";
	private String specimenno = "";
	//msg
	private String sending = "";
	private String sendtime = "";
	private String response = "";
	private String responsetime = "";
	
	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getMessageno() {
		return messageno;
	}

	public void setMessageno(String messageno) {
		this.messageno = messageno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOccurtime() {
		return occurtime;
	}

	public void setOccurtime(String occurtime) {
		this.occurtime = occurtime;
	}

	public String getDrno() {
		return drno;
	}

	public void setDrno(String drno) {
		this.drno = drno;
	}

	public String getDrmobile() {
		return drmobile;
	}

	public void setDrmobile(String drmobile) {
		this.drmobile = drmobile;
	}

	public String getDevicenumber() {
		return devicenumber;
	}

	public void setDevicenumber(String devicenumber) {
		this.devicenumber = devicenumber;
	}

	public String getPatname() {
		return patname;
	}

	public void setPatname(String patname) {
		this.patname = patname;
	}

	public String getPatno() {
		return patno;
	}

	public void setPatno(String patno) {
		this.patno = patno;
	}

	public String getBedno() {
		return bedno;
	}

	public void setBedno(String bedno) {
		this.bedno = bedno;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getRequestno() {
		return requestno;
	}

	public void setRequestno(String requestno) {
		this.requestno = requestno;
	}

	public String getSpecimenno() {
		return specimenno;
	}

	public void setSpecimenno(String specimenno) {
		this.specimenno = specimenno;
	}

	public String getSending() {
		return sending;
	}

	public void setSending(String sending) {
		this.sending = sending;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getResponsetime() {
		return responsetime;
	}

	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}

	//default constructor
	public smsBean()
	{
		super();
	}
	
	public smsBean(String msgno, String status, String occurtime,
			String drno, String drmobile, String devicenumber, 
			String patname, String patno, String bedno, 
			String orderno, String requestno, String specimenno, 
			String sending, String sendtime)
	{
		put("messageno", msgno);
		put("status",status);
		put("occurtime", occurtime);
		put("drno", drno);
		put("drmobile", drmobile);
		put("devicenumber",devicenumber);
		put("patname",patname);
		put("patno", patno);
		put("bedno", bedno);
		put("orderno", orderno);
		put("requestno",requestno);
		put("sepcimenno", specimenno);
		put("sending", sending);
		put("sendtime", sendtime);
	}
	
	public smsBean( BSONObject base)
	{
		super();
		this.putAll(base);
		
	}
	
	public DBObject bsonFromPojo()
	{
		BasicDBObject document = new BasicDBObject();
		
		document.put("_id",	this._id);
		document.put("messageno", this.messageno);
		document.put("status",this.status);
		document.put("occurtime", this.occurtime);
		document.put("drno", this.drno);
		document.put("drmobile", this.drmobile);
		document.put("devicenumber",this.devicenumber);
		document.put("patname",this.patname);
		document.put("patno", this.patno);
		document.put("bedno", this.bedno);
		document.put("orderno", this.orderno);
		document.put("requestno", this.requestno);
		document.put("specimenno", this.specimenno);
		document.put("sending", this.sending);
		document.put("sendtime", this.sendtime);
		document.put("response", this.response);
		document.put("responsetime", this.responsetime);		
		return document;		
	}
	
	public void makePojoFromBson(DBObject bson)
	{
		System.out.println("smsBean makePojoFromBson begin .....");
		BasicDBObject b = (BasicDBObject) bson;
		System.out.println("change to BasicDBObject :" + b.get("_id").toString() + ", " + b.get("_id") + ".");
		this._id = (ObjectId)(b.get("_id"));
		this.messageno = (String) b.get("messageno");
		this.status = (String) b.get("status");
		this.occurtime = (String) b.get("occurtime");
		this.drno = (String) b.get("drno");
		this.drmobile = (String) b.get("drmobile");
		this.devicenumber = (String) b.get("devicenumber");
		this.patname = (String) b.get("patname");
		this.patno = (String) b.get("patno");
		this.bedno = (String) b.get("bedno");
		this.orderno = (String) b.get("orderno");
		this.requestno = (String) b.get("requestno");
		this.specimenno = (String) b.get("specimenno");
		this.sending = (String) b.get("sending");
		this.sendtime = (String) b.get("sendtime");
		this.response = (String) b.get("response");
		this.responsetime = (String) b.get("responsetime");
		System.out.println("smsBean makePojoFromBson finished.");
	}	

	public void searchPojoFromBson(DBObject bson)
	{
		System.out.println("smsBean makePojoFromBson begin .....");
		BasicDBObject b = (BasicDBObject) bson;
		System.out.println("change to BasicDBObject " + b.get("_id").toString() + ".");
		this._id = new ObjectId(b.get("_id").toString());
		this.messageno = (String) b.get("messageno");
		this.status = (String) b.get("status");
		this.occurtime = (String) b.get("occurtime");
		this.drno = (String) b.get("drno");
		this.drmobile = (String) b.get("drmobile");
		this.devicenumber = (String) b.get("devicenumber");
		this.patname = (String) b.get("patname");
		this.patno = (String) b.get("patno");
		this.bedno = (String) b.get("bedno");
		this.orderno = (String) b.get("orderno");
		this.requestno = (String) b.get("requestno");
		this.specimenno = (String) b.get("specimenno");
		this.sending = (String) b.get("sending");
		this.sendtime = (String) b.get("sendtime");
		this.response = (String) b.get("response");
		this.responsetime = (String) b.get("responsetime");
		System.out.println("smsBean makePojoFromBson finished.");
	}		
	
	//public String toString()
	public String getSmsBeanString()
	{
		System.out.println("smsBean getSmsBeanString() begin .....");
		StringBuffer rtStr = new StringBuffer();
		rtStr.append("{");
		if (this._id != null) rtStr.append("_id:{\"$oid\":" + this._id + "\"},");
		//if (this._id != null) rtStr.append("_id:" + this._id + ",");
		if (!"".equals(this.messageno)) rtStr.append("messageno:" + this.messageno + ",");
		if (!"".equals(this.status)) rtStr.append("status:" + this.status + ",");
		if (!"".equals(this.occurtime)) rtStr.append("occurtime:" + this.occurtime + ",");
		if (!"".equals(this.drno)) rtStr.append("drno:" + this.drno + ",");
		if (!"".equals(this.drmobile)) rtStr.append("drmobile:" + this.drmobile + ",");
		if (!"".equals(this.devicenumber)) rtStr.append("devicenumber:" + this.devicenumber + ",");
		if (!"".equals(this.patname)) rtStr.append("patname:" + this.patname + ",");
		if (!"".equals(this.patno)) rtStr.append("patno:" + this.patno + ",");
		if (!"".equals(this.bedno)) rtStr.append("bedno:" + this.bedno + ",");
		if (!"".equals(this.orderno)) rtStr.append("orderno:" + this.orderno + ",");
		if (!"".equals(this.requestno)) rtStr.append("requestno:" + this.requestno + ",");
		if (!"".equals(this.specimenno)) rtStr.append("specimenno:" + this.specimenno + ",");
		if (!"".equals(this.sending)) rtStr.append("sending:" + this.sending + ",");
		if (!"".equals(this.sendtime)) rtStr.append("sendtime:" + this.sendtime + ",");
		if (!"".equals(this.response)) rtStr.append("response:" + this.response + ",");
		if (!"".equals(this.responsetime)) rtStr.append("responsetime:" + this.responsetime + ",");
		if (",".equals(rtStr.substring(rtStr.length()-1))) rtStr.delete(rtStr.length()-1, rtStr.length());
		rtStr.append("}");
		System.out.println(rtStr.toString());
		System.out.println("smsBean getSmsBeanString() finished .....");
		return rtStr.toString();
	}
	
	
	@Override
	public boolean containsField(String arg0) {
		// TODO Auto-generated method stub
		//return false;
		return(arg0.equals("_id")
				|| arg0.equals("messageno")
				|| arg0.equals("status")
				|| arg0.equals("occurtime")
				|| arg0.equals("drno")
				|| arg0.equals("drmobile")
				|| arg0.equals("devicenumber")
				|| arg0.equals("patname")
				|| arg0.equals("patno")
				|| arg0.equals("bedno")
				|| arg0.equals("orderno")
				|| arg0.equals("requestno")
				|| arg0.equals("specimenno")
				|| arg0.equals("sending")				
				|| arg0.equals("sendtime")
				|| arg0.equals("response")				
				|| arg0.equals("responsetime")
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
		if (arg0.equals("messageno")) return this.messageno;
		if (arg0.equals("status")) return this.status;
		if (arg0.equals("occurtime")) return this.occurtime;
		if (arg0.equals("drno")) return this.drno;
		if (arg0.equals("drmobile")) return this.drmobile;
		if (arg0.equals("devicenumber")) return this.devicenumber;
		if (arg0.equals("patname")) return this.patname;
		if (arg0.equals("patno")) return this.patno;
		if (arg0.equals("bedno")) return this.bedno;
		if (arg0.equals("orderno")) return this.orderno;
		if (arg0.equals("requestno")) return this.requestno;
		if (arg0.equals("specimenno")) return this.specimenno;
		if (arg0.equals("sending")) return this.sending;
		if (arg0.equals("sendtime")) return this.sendtime;	
		if (arg0.equals("response")) return this.response;
		if (arg0.equals("responsetime")) return this.responsetime;
		
		return null;
	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		//return null;
		Set<String> set = new HashSet<String>();
		set.add("_id");
		set.add("messageno");
		set.add("status");
		set.add("occurtime");
		set.add("drno");
		set.add("drmobile");
		set.add("devicenumber");
		set.add("patname");
		set.add("patno");
		set.add("bedno");
		set.add("orderno");
		set.add("requestno");
		set.add("specimenno");
		set.add("sending");
		set.add("sendtime");
		set.add("response");
		set.add("responsetime");		
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
		if(arg0.equals("messageno")) 
		{
			this.messageno = (String) arg1;
			return arg1;
		}
		if(arg0.equals("status")) 
		{
			this.status = (String) arg1;
			return arg1;
		}
		if(arg0.equals("occurtime")) 
		{
			this.occurtime = (String) arg1;
			return arg1;
		}
		if(arg0.equals("drno")) 
		{
			this.drno = (String) arg1;
			return arg1;
		}
		if(arg0.equals("drmobile")) 
		{
			this.drmobile = (String) arg1;
			return arg1;
		}
		if(arg0.equals("devicenumber")) 
		{
			this.devicenumber = (String) arg1;
			return arg1;
		}
		if(arg0.equals("patname")) 
		{
			this.patname = (String) arg1;
			return arg1;
		}
		if(arg0.equals("patno")) 
		{
			this.patno = (String) arg1;
			return arg1;
		}
		if(arg0.equals("bedno")) 
		{
			this.bedno = (String) arg1;
			return arg1;
		}
		if(arg0.equals("orderno")) 
		{
			this.orderno = (String) arg1;
			return arg1;
		}
		if(arg0.equals("requestno")) 
		{
			this.requestno = (String) arg1;
			return arg1;
		}	
		if(arg0.equals("requestno")) 
		{
			this.requestno = (String) arg1;
			return arg1;
		}		
		if(arg0.equals("specimenno")) 
		{
			this.specimenno = (String) arg1;
			return arg1;
		}		
		if(arg0.equals("sending")) 
		{
			this.sending = (String) arg1;
			return arg1;
		}		
		if(arg0.equals("sendtime")) 
		{
			this.sendtime = (String) arg1;
			return arg1;
		}				
		if(arg0.equals("response")) 
		{
			this.response = (String) arg1;
			return arg1;
		}		
		if(arg0.equals("responsetime")) 
		{
			this.responsetime = (String) arg1;
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
		if (this.messageno != null) map.put("messageno", this.messageno);
		if (this.status != null) map.put("status", this.status);
		if (this.occurtime != null) map.put("occurtime", this.occurtime);
		if (this.drno != null) map.put("drno", this.drno);
		if (this.drmobile != null) map.put("drmobile", this.drmobile);
		if (this.devicenumber != null) map.put("devicenumber", this.devicenumber);
		if (this.patname != null) map.put("patname", this.patname);
		if (this.patno != null) map.put("patno", this.patno);
		if (this.bedno != null) map.put("bedno", this.bedno);
		if (this.orderno != null) map.put("orderno", this.orderno);
		if (this.requestno != null) map.put("requestno", this.requestno);
		if (this.specimenno != null) map.put("specimenno", this.specimenno);
		if (this.sending != null) map.put("sending", this.sending);
		if (this.sendtime != null) map.put("sendtime", this.sendtime);
		if (this.response != null) map.put("response", this.response);
		if (this.responsetime != null) map.put("responsetime", this.responsetime);		
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
