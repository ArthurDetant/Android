package com.example.tpimageadetant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    private Bitmap restoreBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loadBtn = (Button) findViewById(R.id.loadBtn);
        loadBtn.setOnClickListener(loadButtonClickListener);

        Button restoreBtn = (Button) findViewById(R.id.restoreBtn);
        restoreBtn.setOnClickListener(restoreButtonClickListener);

        ImageView img = (ImageView) findViewById(R.id.imgImageView);
        registerForContextMenu(img);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextuel, menu);
    }


    View.OnClickListener loadButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent actionGetContentIntent = new Intent();
            actionGetContentIntent.setType("image/*");
            actionGetContentIntent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(actionGetContentIntent, "Select Picture"),
                    SELECT_PICTURE);
        }
    };

    View.OnClickListener restoreButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            ImageView img = (ImageView) findViewById(R.id.imgImageView);
            img.setImageBitmap(restoreBitmap);
        }
    };


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ImageView img;
        Bitmap bitmap;
        switch(id){
            case R.id.HmirorOption:
                img = (ImageView) findViewById(R.id.imgImageView);
                bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                bitmap = flipImage(bitmap, "H");
                img.setImageBitmap(bitmap);

                return true;
            case R.id.VmirorOption:
                img = (ImageView) findViewById(R.id.imgImageView);
                bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                bitmap = flipImage(bitmap, "V");
                img.setImageBitmap(bitmap);

                return true;
            case R.id.rotationDroiteOption:
                img = (ImageView) findViewById(R.id.imgImageView);
                bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                bitmap = rotationDroite(bitmap);
                img.setImageBitmap(bitmap);

                return true;
            case R.id.rotationGaucheOption:
                img = (ImageView) findViewById(R.id.imgImageView);
                bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                bitmap = rotationGauche(bitmap);
                img.setImageBitmap(bitmap);

                return true;
           }

        return false;
    };


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ImageView imageView;
        Bitmap bitmap;
        Bitmap newBitmap;
        switch (item.getItemId()) {
            case R.id.inverserCouleursOption:
                imageView = (ImageView) findViewById(R.id.imgImageView);
                bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                newBitmap =inverserCouleur(bitmap);
                imageView.setImageBitmap(newBitmap);
                return true;
            case R.id.niveauGrisOption:
                imageView = (ImageView) findViewById(R.id.imgImageView);
                bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                newBitmap =niveauGris(bitmap);
                imageView.setImageBitmap(newBitmap);
                return true;
            case R.id.niveauGrisOption2:
                imageView = (ImageView) findViewById(R.id.imgImageView);
                bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                newBitmap =niveauGris2(bitmap);
                imageView.setImageBitmap(newBitmap);
                return true;
            case R.id.niveauGrisOption3:
                imageView = (ImageView) findViewById(R.id.imgImageView);
                bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                newBitmap =niveauGris3(bitmap);
                imageView.setImageBitmap(newBitmap);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    public Bitmap flipImage(Bitmap bitmap, String orientation) {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),Bitmap.Config.ARGB_8888);

        switch (orientation) {
            case "H": // miroir Horizontal
                for (int x = 0; x < bitmap.getWidth(); x++) {
                    int h = bitmap.getHeight() - 1;
                    for (int y = 0; y < bitmap.getHeight(); y++) {
                        int pixel = bitmap.getPixel(x, y);
                        newBitmap.setPixel(x, h, pixel);
                        h--;
                    }
                }
                return newBitmap;

            case "V": // miroir vertical
                int w = bitmap.getWidth() - 1;
                for (int x = 0; x < bitmap.getWidth(); x++) {
                    for (int y = 0; y < bitmap.getHeight(); y++) {
                        int pixel = bitmap.getPixel(x, y);
                        newBitmap.setPixel(w, y, pixel);
                    }
                    w--;
                }
                return newBitmap;
        }

        return null;
    };


     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                try {
                    Uri imageUri = data.getData();

                    BitmapFactory.Options option = new BitmapFactory.Options();
                    Bitmap bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, option);

                    ImageView img = (ImageView) findViewById(R.id.imgImageView);
                    img.setImageBitmap(bm);

                    TextView uriTextView = (TextView) findViewById(R.id.uriTextView);
                    uriTextView.setText(imageUri.getPath());
                    restoreBitmap = bm.copy(bm.getConfig(), bm.isMutable());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public Bitmap inverserCouleur(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        int taille = bitmap.getWidth() * bitmap.getHeight();
        int[] pixels = new int[taille];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int i = 0; i < taille; i++) {
            int pixel = pixels[i];
            int alphaValue = Color.alpha(pixel);
            int redValue = 255 - Color.red(pixel);
            int greenValue = 255 - Color.green(pixel);
            int blueValue = 255 - Color.blue(pixel);

            pixels[i] = Color.argb(alphaValue, redValue, greenValue, blueValue);
        }

        newBitmap.setPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return newBitmap;
    }


    public Bitmap niveauGris(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        int length = bitmap.getWidth() * bitmap.getHeight();
        int[] pixels = new int[length];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int i = 0; i < length; i++) {

            int pixel = pixels[i];
            int alphaValue = Color.alpha(pixel);
            int redValue = Color.red(pixel);
            int greenValue = Color.green(pixel);
            int blueValue = Color.blue(pixel);

            redValue = blueValue = greenValue = (redValue + blueValue + greenValue) / 3;
            pixels[i] = Color.argb(alphaValue, redValue, greenValue, blueValue);
        }
        newBitmap.setPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return newBitmap;
    }

    public Bitmap niveauGris2(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        int length = bitmap.getWidth() * bitmap.getHeight();
        int[] pixels = new int[length];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int i = 0; i < length; i++) {

            int pixel = pixels[i];
            int alphaValue = Color.alpha(pixel);
            int redValue = Color.red(pixel);
            int greenValue = Color.green(pixel);
            int blueValue = Color.blue(pixel);

            redValue = greenValue = blueValue =
                    (Math.max(redValue, Math.max(greenValue,blueValue)) +
                            Math.min(redValue, Math.min(greenValue,blueValue))) / 2;

            pixels[i] = Color.argb(alphaValue, redValue, greenValue, blueValue);
        }
        newBitmap.setPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return newBitmap;
    }

    public Bitmap niveauGris3(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        int length = bitmap.getWidth() * bitmap.getHeight();
        int[] pixels = new int[length];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int i = 0; i < length; i++) {

            int pixel = pixels[i];
            int alphaValue = Color.alpha(pixel);
            int redValue = Color.red(pixel);
            int greenValue = Color.green(pixel);
            int blueValue = Color.blue(pixel);

            redValue = greenValue = blueValue = (int)(0.21 * redValue + 0.72 * greenValue + 0.07 * blueValue);
            pixels[i] = Color.argb(alphaValue, redValue, greenValue, blueValue);
        }
        newBitmap.setPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return newBitmap;
    }

        //rotation horaire
    public Bitmap rotationDroite(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getWidth(), bitmap.getConfig());

        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                newBitmap.setPixel(j, i, bitmap.getPixel(i, bitmap.getHeight() - j - 1));
            }
        }
        return newBitmap;
    }

    // Rotation anti-horaire
    public Bitmap rotationGauche(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getWidth(), bitmap.getConfig());

        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                newBitmap.setPixel(j, i, bitmap.getPixel(bitmap.getWidth() - i - 1, j));
            }
        }
        return newBitmap;
    }


}