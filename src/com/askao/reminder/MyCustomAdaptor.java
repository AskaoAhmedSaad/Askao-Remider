package com.askao.reminder;

import java.util.Vector;

import com.askao.reminder.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCustomAdaptor extends ArrayAdapter<GtuMcaBean>{
    Context context;
    int layoutResourceId;  
    static String datesort;
   
    GtuMcaBean currentMRB;
    Vector<GtuMcaBean> data;
    /** Called when the activity is first created. */
    // TODO Auto-generated constructor stub
    public MyCustomAdaptor(Context context, int layoutResourceId, Vector<GtuMcaBean> data)
    {
        super(context,layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context=context;
        this.data = data;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        MyStringReaderHolder holder;
       
        if(row==null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent,false);
           
            holder= new MyStringReaderHolder();
           
            holder.gtumcaTvFirstName =(TextView)row.findViewById(R.id.gtumcaTvFirstName);
            holder.gtumcaTvLastName =(TextView)row.findViewById(R.id.gtumcaTvLastName);
            holder.gtumcaTvBirthDate  =(TextView)row.findViewById(R.id.gtumcaTvBirthDate);
            holder.gtumNotes  =(TextView)row.findViewById(R.id.gtumNotes);
            holder.gtumcaIvIcon=(ImageView) row.findViewById(R.id.gtumcaIvIcon);
           
            row.setTag(holder);
        }
        else
        {
            holder=(MyStringReaderHolder) row.getTag();
        }
       
        GtuMcaBean mrb =data.elementAt(position);
        System.out.println("Position="+position);
     
        holder.gtumcaTvFirstName.setText(mrb.noti_name);
        holder.gtumcaTvLastName.setText(mrb.noti_time);
        holder.gtumcaTvBirthDate.setText(mrb.noti_type);
        holder.gtumNotes.setText(mrb.notes);
        if (mrb.noti_name.equals("salah"))
        	holder.gtumcaIvIcon.setImageResource(R.drawable.salah);
        else if (mrb.noti_name.equals("sleep"))
        	holder.gtumcaIvIcon.setImageResource(R.drawable.sleep);
        else if (mrb.noti_name.equals("wakeup"))
        	holder.gtumcaIvIcon.setImageResource(R.drawable.alarm);
        else if (mrb.noti_name.equals("food"))
        	holder.gtumcaIvIcon.setImageResource(R.drawable.food);
        else if (mrb.noti_name.equals("work"))
        	holder.gtumcaIvIcon.setImageResource(R.drawable.work);
        else if (mrb.noti_name.equals("meeting"))
        	holder.gtumcaIvIcon.setImageResource(R.drawable.meeting);
        else if (mrb.noti_name.equals("study"))
        	holder.gtumcaIvIcon.setImageResource(R.drawable.study);
        else
        	holder.gtumcaIvIcon.setImageResource(R.drawable.ic_launcher);
        return row;
    }
    static class MyStringReaderHolder
    {
        TextView gtumcaTvFirstName,gtumcaTvLastName,gtumcaTvBirthDate,gtumNotes;
        ImageView gtumcaIvIcon;
    }
}

