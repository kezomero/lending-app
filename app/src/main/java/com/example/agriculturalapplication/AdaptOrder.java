package com.example.agriculturalapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class AdaptOrder extends RecyclerView.Adapter<AdaptOrder.tool> {

    private static final String TAG = "Adapt";
    private Context context;
    private static List<ModelOrder> modelList;
    private String mail;

    public AdaptOrder(Context context, List<ModelOrder> modelList,String mail) {
        this.context = context;
        this.modelList = modelList;
        this.mail=mail;
    }

    @NonNull
    @Override
    public tool onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.details, parent, false);
        return new tool(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tool holder, int position) {
        ModelOrder model = modelList.get(position);
        holder.name.setText(model.getName());
        holder.status.setText("Status: " + model.getStatus());
        holder.days.setText("Hiring Days: " + model.getDays());
        holder.contact.setText("Hierer Contact: " + model.getContact());

        final String id=model.getId();

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the 'id' and 'mail' value to the OrderDetails activity
                Intent intent = new Intent(context, OrderDetail.class);
                intent.putExtra("id", id);
                intent.putExtra("mail", mail);
                context.startActivity(intent);
            }
        });

        String imageUrl = model.getImage();

        GetImageFromUrl getImageTask = new GetImageFromUrl(holder.image,context);
        getImageTask.execute(imageUrl);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class tool extends RecyclerView.ViewHolder {
        TextView name, status, days, contact;
        ImageView image;
        Button view;

        public tool(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status);
            days = itemView.findViewById(R.id.days);
            contact = itemView.findViewById(R.id.contact);
            image = itemView.findViewById(R.id.image);
            view=itemView.findViewById(R.id.view);

        }
    }

    private static class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

        private ImageView imageView;
        private Context context;
        private ProgressDialog dialog;

        public GetImageFromUrl(ImageView imageView, Context context) {
            this.imageView = imageView;
            this.context = context;
            this.dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading images...");
            dialog.setCanceledOnTouchOutside(false);
            //dialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urlDisplay = url[0];
            Bitmap bitmap = null;

            try {
                URL imageUrl = new URL("http://192.168.100.23/LoginRegister/" + urlDisplay);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream inputStream = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                //dialog.dismiss();
            } catch (IOException e) {
                Log.e(TAG, "Error loading image: " + e.getMessage());
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Log.e(TAG, "Bitmap is null");
            }
        }
    }
}
