package com.askao.reminder;

public class GtuMcaBean{
	int id;
    String noti_name,noti_time,noti_type,notes;  
    GtuMcaBean(int id,String noti_name,String noti_time,String noti_type, String notes){
    	this.id = id;
        this.noti_name = noti_name;
        this.noti_time = noti_time;
        this.noti_type = noti_type;
        this.notes = notes;
    }
}