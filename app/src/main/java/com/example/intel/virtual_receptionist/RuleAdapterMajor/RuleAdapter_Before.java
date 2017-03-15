package com.example.intel.virtual_receptionist.RuleAdapterMajor;

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

import com.example.intel.virtual_receptionist.DBManager.DBHandler;
import com.example.intel.virtual_receptionist.Model.BeforeRule;
import com.example.intel.virtual_receptionist.R;
import com.example.intel.virtual_receptionist.RuleActivity.Rule_Before_Activity;
import com.example.intel.virtual_receptionist.URL.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by intel on 2/22/2017.
 */
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import cz.msebera.android.httpclient.Header;

public class RuleAdapter_Before extends RecyclerView.Adapter<RuleAdapter_Before.MyViewHolder> {
    LayoutInflater inflater;
    private Context context;
    ArrayList<BeforeRule> before_rule_list = new ArrayList<BeforeRule>();

    public RuleAdapter_Before(Context context, ArrayList<BeforeRule> before_rule_list) {
        this.context = context;
        this.before_rule_list = before_rule_list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myview = inflater.inflate(R.layout.custom_list_item, parent, false);
        MyViewHolder Holder = new MyViewHolder(myview, context, before_rule_list);
        return Holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.CALLER.setText(before_rule_list.get(position).getCaller());
        final BeforeRule beforeRule = before_rule_list.get(position);
        String ACTION_NAME = beforeRule.getAction();
        if (ACTION_NAME.equals("1")) {
            holder.ACTION.setText("BLACK LIST");
            holder.agent_img.setImageResource(R.drawable.blacklist);

        }
        if (ACTION_NAME.equals("2")) {
            holder.ACTION.setText("DTMF");
            holder.agent_img.setImageResource(R.drawable.dtmf);
        }
        if (ACTION_NAME.equals("3")) {
            holder.ACTION.setText("MISS CALL");
            holder.agent_img.setImageResource(R.drawable.misscall);
        }
        if (ACTION_NAME.equals("4")) {
            holder.ACTION.setText("PHONE");
            holder.agent_img.setImageResource(R.drawable.phone);
        }
        if (ACTION_NAME.equals("5")) {
            holder.ACTION.setText("SOUND");
            holder.agent_img.setImageResource(R.drawable.sound);
        }
        if (ACTION_NAME.equals("6")) {
            holder.ACTION.setText("VOICE MAIL");
            holder.agent_img.setImageResource(R.drawable.voicemail);
        }
        holder.layout1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final BeforeRule beforeRule = before_rule_list.get(position);
                final String ITEMID = beforeRule.getId();
                android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(context);
                dialog.setTitle("Delete :  " + beforeRule.getAction());
                dialog.setMessage("Are You Sure you want to delete the item ");
                dialog.setIcon(R.drawable.delete);
                dialog.setPositiveButton("DELETE ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHandler handler = new DBHandler(context);
                        String USERID = handler.getUserId();
                        final ProgressDialog pDialog;
                        AsyncHttpClient client = new AsyncHttpClient();
                        RequestParams params = new RequestParams();
                        pDialog = new ProgressDialog(context);
                        pDialog.setMessage("Deleting  Please wait..");
                        pDialog.setCanceledOnTouchOutside(false);
                        pDialog.show();
                        client.setTimeout(10 * 1000);
                        client.post(Urls.delete_rule_before + USERID + "&id=" + ITEMID, params, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onStart() {
                                        super.onStart();
                                        pDialog.show();
                                    }

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        try {

                                            if (response.getString("msg").equals("200")) {
                                                before_rule_list.remove(position);
                                                notifyItemRemoved(position);
                                                notifyItemRangeRemoved(position, before_rule_list.size());
                                                pDialog.dismiss();
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

    }

    @Override
    public int getItemCount() {
        return before_rule_list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView ACTION;
        public ImageView agent_img;
        public LinearLayout layout1;
        public TextView CALLER;
        ArrayList<BeforeRule> before_rule_list = new ArrayList<BeforeRule>();
        Context context;

        public MyViewHolder(View itemView, Context context, ArrayList<BeforeRule> before_rule_list) {
            super(itemView);
            this.context = context;
            this.before_rule_list = before_rule_list;
            agent_img = (ImageView) itemView.findViewById(R.id.img_dummy);
            ACTION = (TextView) itemView.findViewById(R.id.agent_name);
            CALLER = (TextView) itemView.findViewById(R.id.agent_active_or_not);
            layout1 = (LinearLayout) itemView.findViewById(R.id.parent_layout);
            layout1.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            BeforeRule beforeRule = before_rule_list.get(getAdapterPosition());
            Intent start = new Intent(context, Rule_Before_Activity.class);
            start.putExtra("ID", beforeRule.getId());//DONE
            start.putExtra("UID", beforeRule.getUid());//DONE
            start.putExtra("CALLER", beforeRule.getCaller());//DONE
            start.putExtra("ACTION", beforeRule.getAction());//DONE
            start.putExtra("DTMFRULE", beforeRule.getDtmf_rules());
            start.putExtra("VOICEEMAILRULE", beforeRule.getVoicemail_rules());
            start.putExtra("PHONERULE", beforeRule.getPhone_rules());
            start.putExtra("IVRD", beforeRule.getIvrid());
            start.putExtra("DESCRIPTION", beforeRule.getDescription());
            start.putExtra("NOTIFICATION", beforeRule.getNotifications());
            start.putExtra("DATEADDED", beforeRule.getDateadded());
            start.putExtra("IP", beforeRule.getIp());
            start.putExtra("NID", beforeRule.getNid());//DONE

            this.context.startActivity(start);


        }
    }
}
