package com.example.wang.myandroidproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.Context.MODE_PRIVATE;

/*
 * Created by wang on 2018/5/7.
 */

public class User extends Fragment implements OnClickListener{
    private Button btn_change_info;
    private Button btn_personal;
    private Button btn_task;
    private Button btn_buy;
    private Button btn_publish;
    private Button btn_sold;
    private Button btn_message;
    private Button btn_exit;
    private Button btn_index;
    private ImageButton btn_add;
    private String set_user_name;
    private String set_user_id;
    private TextView tv_publish;
    private TextView tv_order;
    private TextView tv_buy;
    private TextView tv_sold;
    private TextView tv_username;
    private TextView tv_userid;
    private ImageView blurImageView;
    private ImageView avatarImageView;
    private String array[] = new String[]{"????????????", "????????????"};
    private static String strCon = "jdbc:mysql://101.132.152.183:3306/android_server"; // ???????????????
    private static String strUser = "root"; // ??????????????????
    private static String strPwd = "root"; // ??????
    private int publish_rownum;
    private int order_rownum;
    private int buy_rownum;
    private int sold_rownum;
    @SuppressLint("HandlerLeak")
    private Handler mHanler=new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            tv_userid.setText(set_user_id);
            tv_username.setText(set_user_name);
            tv_buy.setText(buy_rownum+"");
            tv_order.setText(order_rownum+"");
            tv_publish.setText(publish_rownum+"");
            tv_sold.setText(sold_rownum+"");//??????????????????

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.users,container,false);
        tv_username=view.findViewById(R.id.tv_username);
        tv_buy=view.findViewById(R.id.num_of_buy);
        tv_order=view.findViewById(R.id.num_of_task);
        tv_publish=view.findViewById(R.id.num_of_publish);
        tv_sold=view.findViewById(R.id.num_of_sold);
        tv_userid=view.findViewById(R.id.tv_userid);
        btn_change_info=view.findViewById(R.id.button_change_info);
        btn_personal=view.findViewById(R.id.personal);
        btn_task=view.findViewById(R.id.button_task);
        btn_publish=view.findViewById(R.id.button_publish);
        btn_buy=view.findViewById(R.id.button_buy);
        btn_sold=view.findViewById(R.id.button_sold);
        btn_exit=view.findViewById(R.id.button_exit);
//        btn_index=view.findViewById(R.id.btn_index);
        blurImageView=view.findViewById(R.id.h_back);
        avatarImageView=view.findViewById(R.id.h_head);
//        btn_add=view.findViewById(R.id.btn_add);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*
            ?????????????????????
         */
//        btn_index.setOnClickListener(this);
//        btn_add.setOnClickListener(this);
        /*
            ?????????
         */
        btn_change_info.setOnClickListener(this);
        btn_task.setOnClickListener(this);
        btn_publish.setOnClickListener(this);
        btn_buy.setOnClickListener(this);
        btn_sold.setOnClickListener(this);

        /*
            ??????????????????
         */
        btn_exit.setOnClickListener(this);
        /*
            ?????????????????????
         */
        Glide.with(this).load(R.mipmap.logo_no_word)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(blurImageView);

        Glide.with(this).load(R.mipmap.logo_no_word)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(avatarImageView);
        /*

         */
        new Thread(runnable).start();

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.users);
//        btn_change_info=findViewById(R.id.button_change_info);
//        btn_personal=findViewById(R.id.personal);
//        btn_task=findViewById(R.id.button_task);
//        btn_publish=findViewById(R.id.button_publish);
//        btn_buy=findViewById(R.id.button_buy);
//        btn_sold=findViewById(R.id.button_sold);
//        btn_exit=findViewById(R.id.button_exit);
//        btn_index=findViewById(R.id.btn_index);
//        blurImageView=findViewById(R.id.h_back);
//        avatarImageView=findViewById(R.id.h_head);
//        btn_add=findViewById(R.id.btn_add);
//        /*
//            ?????????????????????
//         */
//        btn_index.setOnClickListener(this);
//        btn_add.setOnClickListener(this);
//        /*
//            ?????????
//         */
//        btn_change_info.setOnClickListener(this);
//        btn_task.setOnClickListener(this);
//        btn_publish.setOnClickListener(this);
//        btn_buy.setOnClickListener(this);
//        btn_sold.setOnClickListener(this);
//
//        /*
//            ??????????????????
//         */
//        btn_exit.setOnClickListener(this);
//        /*
//            ?????????????????????
//         */
//        Glide.with(this).load(R.mipmap.logo_no_word)
//                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
//                .into(blurImageView);
//
//        Glide.with(this).load(R.mipmap.logo_no_word)
//                .bitmapTransform(new CropCircleTransformation(this))
//                .into(avatarImageView);
//        /*
//
//         */
//        new Thread(runnable).start();
//    }

    @Override
    public void onClick(View view) {
        SharedPreferences pref=getActivity().getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        switch (view.getId()) {
            case R.id.button_change_info:
                Intent intent=new Intent(getActivity(),User_detail.class);
                startActivity(intent);
                this.getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.button_task:
                editor.putString("send_info","1");//??????send_info???My_task?????????????????????????????????
                editor.apply();
                intent=new Intent(getActivity(),My_task.class);
                startActivity(intent);
                this.getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.button_publish:
                editor.putString("send_info","2");//??????send_info???My_task?????????????????????????????????
                editor.apply();
                intent=new Intent(getActivity(),My_task.class);
                startActivity(intent);
                this.getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.button_buy:
                editor.putString("send_info","3");//??????send_info???My_product?????????????????????????????????
                editor.apply();
                intent=new Intent(getActivity(),My_product.class);
                startActivity(intent);
                this.getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.button_sold:
                editor.putString("send_info","4");//??????send_info???My_product?????????????????????????????????
                editor.apply();
                intent=new Intent(getActivity(),My_product.class);
                startActivity(intent);
                this.getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.button_exit:
                Map<String,?> key_Value= pref.getAll(); //??????????????????????????????????????????????????????Map????????????
                for (String key : key_Value.keySet()) {
                    Log.i("map_key",key);
                    editor.remove(key+"");//??????????????????????????????????????????
                    editor.apply();
                }
                intent=new Intent(getActivity(),Login.class);
                startActivity(intent);
                getActivity().onBackPressed();
                break;
            case R.id.btn_index:
                intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().onBackPressed();
                break;
            case R.id.btn_add:
                AlertDialog.Builder ad =new AlertDialog.Builder(getActivity()).setTitle("?????????")
                        .setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ListView lw = ((AlertDialog) dialogInterface).getListView();
                                Object checkedItem = lw.getAdapter().getItem(i);
                                dialogInterface.dismiss();
                                String xz=(String) checkedItem;
                                /*toast.makeText(MainActivity.this,xz,Toast.LENGTH_SHORT).show();*/  //??????xz?????????
                                if (xz.equals("????????????")){
                                    Intent intent = new Intent(getActivity(), Publish_task.class);
                                    startActivity(intent);}
                                else{
                                    Intent intent = new Intent(getActivity(), Publish_product.class);
                                    startActivity(intent);
                                }
                            }

                        });
                AlertDialog dialog = ad.create();
                dialog.show();
                break;
        }
    }
    public void onBackPressed() {
        Intent MyIntent = new Intent(Intent.ACTION_MAIN);
        MyIntent.addCategory(Intent.CATEGORY_HOME);
        startActivity(MyIntent);
    }
    Runnable runnable = new Runnable() {
        private Connection con = null;// ??????????????????
        @Override


        public void run() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(strCon, strUser, strPwd);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            try {
                sqlCon(con);    //?????????????????????
            } catch (java.sql.SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.i("LoginActivity", "????????????"+e.getMessage()+"");
            }
        }
        private void sqlCon(Connection con1) throws java.sql.SQLException {
            SharedPreferences pref=getActivity().getSharedPreferences("data", MODE_PRIVATE);
            String user_id= pref.getString("user_id","");
            Log.i("??????",user_id);
            try { // ????????????
                Class.forName("com.mysql.jdbc.Driver"); // ??????????????????
                Connection con;// ??????????????????
                con = DriverManager.getConnection(strCon, strUser, strPwd);
                Statement sta = con.createStatement(); // ??????????????????
                ResultSet rs = sta.executeQuery("SELECT * FROM base_user WHERE user_id=('"+user_id+"')");// ??????SQL??????
                while (rs.next()) { // ?????????????????????????????????????????????????????????false
                    Log.d("UserActivity", ""+rs.getString("user_id")+"");
                    Log.d("UserActivity", ""+rs.getString("password")+"");
                    set_user_name=rs.getString("user_name");
                    set_user_id=rs.getString("user_id");
                }
                rs = sta.executeQuery("SELECT * FROM task WHERE user_id=('"+user_id+"') and destroy_time is NULL ORDER BY create_time DESC");
                rs.last();
                publish_rownum=rs.getRow();//???????????????????????????

                rs = sta.executeQuery("SELECT task.* FROM user_task,task WHERE '"+user_id+"'=user_task.user_id and user_task.task_id=task.id and task.flag=1 and task.destroy_time is NULL ORDER BY create_time DESC");
                rs.last();
                order_rownum=rs.getRow();//???????????????????????????

                rs = sta.executeQuery("SELECT * FROM product WHERE user_id=('"+user_id+"') and destroy_time is NULL ORDER BY create_time DESC");
                rs.last();
                sold_rownum=rs.getRow();//???????????????????????????

                rs = sta.executeQuery("SELECT product.* FROM user_product,product WHERE '"+user_id+"'=user_product.user_id and user_product.product_id=product.id and product.flag=1 and product.destroy_time is NULL ORDER BY create_time DESC");
                rs.last();
                buy_rownum=rs.getRow();//???????????????????????????

//                Log.i("RowNum",publish_rownum+"");
//                rs.close();
//                sta.close();
                mHanler.sendEmptyMessage(0);
                con.close(); // ?????????????????????????????????
            } catch (ClassNotFoundException e ) {
                e.printStackTrace();
                Log.i("LoginActivity", "????????????:"+e.getMessage()+"");
            }
//            finally {
//                if (con1 != null)
//                    try {
//                        con1.close();
//                        Log.i("LoginActivity", "????????????:");
//                    } catch (SQLException sqle) {
//                        sqle.printStackTrace();
//                        Log.i("LoginActivity", "????????????:"+sqle.getMessage()+"");
//                    }
//            }
        }
    };
}
