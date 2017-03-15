package com.example.intel.virtual_receptionist.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.intel.virtual_receptionist.AgentProfile;
import com.example.intel.virtual_receptionist.DBManager.DBHandler;
import com.example.intel.virtual_receptionist.Model.Agent;
import com.example.intel.virtual_receptionist.R;
import com.example.intel.virtual_receptionist.URL.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.intel.virtual_receptionist.R.id;

/**
 * Created by intel on 2/9/2017.
 */
public class CustomAgentAdapter extends RecyclerView.Adapter<CustomAgentAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Agent> myagent = new ArrayList<Agent>();
    private LayoutInflater inflater;
    public CustomAgentAdapter(Context context, ArrayList<Agent> myagent) {
        this.context = context;
        this.myagent = myagent;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CustomAgentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View myview = inflater.inflate(R.layout.custom_list_item, parent, false);
        ViewHolder Holder = new ViewHolder(myview, context, myagent);
        return Holder;
    }
    @Override
    public void onBindViewHolder(CustomAgentAdapter.ViewHolder holder, final int position) {
        holder.agent_name.setText(myagent.get(position).getAgent_Name());
        if (myagent.get(position).getActive_or_inactive().equals("0")) {
            holder.agent_active_or_not.setText("Inactive");
        } else {
            holder.agent_active_or_not.setText("Active");
        }
        //holder.agent_sipname.setText(myagent.get(position).getAgent_Sip_Name());
        // holder.agent_password.setText(myagent.get(position).getAgent_Password());
        holder.layout1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return false;
            }
        });
        holder.layout1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final Agent itemLabel = myagent.get(position);
                String id = itemLabel.getAgent_id();
                android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(context);
                dialog.setTitle("Delete :     " + itemLabel.getAgent_Name());
                dialog.setMessage("Are You Sure you want to delete the item ");
                dialog.setIcon(R.drawable.delete);
                dialog.setPositiveButton("Delete ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHandler storage = new DBHandler(context);
                        String uid = storage.getUserId();
                        final ProgressDialog pDialog;
                        AsyncHttpClient client = new AsyncHttpClient();
                        RequestParams params = new RequestParams();
                        pDialog = new ProgressDialog(context);
                        pDialog.setMessage("Deleting  Please wait..");
                        pDialog.setCanceledOnTouchOutside(false);
                        pDialog.show();
                        client.setTimeout(10 * 1000);
                        String totalarray = "&uid=" + uid + "&id=" + itemLabel.agent_id;
                        client.post(Urls.agent_delete + totalarray, params, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onStart() {
                                        super.onStart();
                                        pDialog.show();
                                    }
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        try {

                                            if (response.getString("msg").equals("200")) {
                                                myagent.remove(position);
                                                notifyItemRemoved(position);
                                                notifyItemRangeRemoved(position, myagent.size());
                                                pDialog.dismiss();
                                          /*  Urls.createDialogSuccess(getActivity(), Urls.AlertMsgs.success);*/

                                           /* Toast.makeText(getActivity(), "Added Successfully ", Toast.LENGTH_SHORT).show();
                                          */
                                                Urls.createDialogSuccess(context, Urls.AlertMsgs.success);

                                            }
                                            if (response.getString("msg").equals("202")) {
                                                pDialog.dismiss();
                                                Urls.createDialog(context, Urls.AlertMsgs.alreadyaddded);
                                            }
                                            if (response.getString("msg").equals("404")) {
                                                pDialog.dismiss();
                                                Urls.createDialog(context, Urls.AlertMsgs.server);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }


                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                        super.onFailure(statusCode, headers, throwable, errorResponse);
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                        super.onFailure(statusCode, headers, responseString, throwable);
                                    }
                                }
                        );

                    }

                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.create().show();


                return true;
            }
        });

/*
        holder.layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Agent itemLabel = myagent.get(position);
                String id = itemLabel.getAgent_id();
                android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(context);
                dialog.setTitle("Delete :     " + itemLabel.getAgent_Name());
                dialog.setMessage("Are You Sure you want to delete the item ");
                dialog.setIcon(R.drawable.delete);

                dialog.setPositiveButton("Delete ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   */
/* agentList.remove(getAdapterPosition());*//*

                        DBHandler storage = new DBHandler(context);
                        String uid = storage.getUserId();
                        final ProgressDialog pDialog;
                        AsyncHttpClient client = new AsyncHttpClient();
                        RequestParams params = new RequestParams();
                        pDialog = new ProgressDialog(context);
                        pDialog.setMessage("Deleting  Please wait..");
                        pDialog.setCanceledOnTouchOutside(false);
                        pDialog.show();
                        client.setTimeout(10 * 1000);
                        String totalarray = "&uid=" + uid + "&id=" + itemLabel.agent_id;


                        client.post(Urls.agent_delete + totalarray, params, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onStart() {
                                        super.onStart();
                                        pDialog.show();
                                    }

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                                        try {

                                            if (response.getString("msg").equals("200")) {
                                                myagent.remove(position);
                                                notifyItemRemoved(position);
                                                notifyItemRangeRemoved(position, myagent.size());
                                                pDialog.dismiss();
                                          */
/*  Urls.createDialogSuccess(getActivity(), Urls.AlertMsgs.success);*//*


                                           */
/* Toast.makeText(getActivity(), "Added Successfully ", Toast.LENGTH_SHORT).show();
                                          *//*

                                                Urls.createDialogSuccess(context, Urls.AlertMsgs.success);

                                            }
                                            if (response.getString("msg").equals("202")) {
                                                pDialog.dismiss();
                                                Urls.createDialog(context, Urls.AlertMsgs.alreadyaddded);
                                            }
                                            if (response.getString("msg").equals("404")) {
                                                pDialog.dismiss();
                                                Urls.createDialog(context, Urls.AlertMsgs.server);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }


                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                        super.onFailure(statusCode, headers, throwable, errorResponse);
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                        super.onFailure(statusCode, headers, responseString, throwable);
                                    }
                                }
                        );

                    }

                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.create().show();


            }
        });
*/

    }


    @Override
    public int getItemCount() {
        return myagent.size();

    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView agent_name;
        // public TextView agent_sipname;
        // public TextView agent_password;
        public ImageView agent_img;
        public LinearLayout layout1;
        Context context;
        public TextView agent_active_or_not;
        ArrayList<Agent> agentList = new ArrayList<Agent>();

        public ViewHolder(View itemView, Context context, ArrayList<Agent> agentList) {
            super(itemView);
            this.context = context;
            this.agentList = agentList;

            agent_name = (TextView) itemView.findViewById(id.agent_name);
            agent_active_or_not = (TextView) itemView.findViewById(id.agent_active_or_not);

           /* agent_sipname = (TextView) itemView.findViewById(id.agent_sipname);
            agent_password = (TextView) itemView.findViewById(id.agent_passwordt);
            */
            agent_img = (ImageView) itemView.findViewById(id.img_dummy);
            layout1 = (LinearLayout) itemView.findViewById(id.parent_layout);
            layout1.setClickable(true);
            layout1.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Agent agent = this.agentList.get(getAdapterPosition());
            Intent start = new Intent(context, AgentProfile.class);
            start.putExtra("Agent_Id", agent.getAgent_id());
            start.putExtra("Agent_Name", agent.getAgent_Name());
            start.putExtra("Agent_Password", agent.getAgent_Password());
            start.putExtra("AgentSip_Name", agent.getAgent_Sip_Name());
            start.putExtra("AgentMobile", agent.getAgent_mobile());
            start.putExtra("active_or_Inactive", agent.getActive_or_inactive());
            start.putExtra("date_added", agent.getDate_added());
            this.context.startActivity(start);


            //This is the way to get the value from the Recycler View adapter android
          /*  int position = getAdapterPosition();
            Agent agent = this.agentList.get(position);
            Toast.makeText(context, "" + agent.toString(), Toast.LENGTH_LONG).show();
            System.out.println(agent.getAgent_Name());
            System.out.println(agent.getAgent_Password());
            System.out.println(agent.getAgent_Sip_Name());*/
        }


    }
    public void Filter_Item(ArrayList<Agent> new_item)
    {
        myagent =new ArrayList<>();
        myagent.addAll(new_item);
        notifyDataSetChanged();
    }


}