/*
 * This file is part of Siebe Projects samples.
 *
 * Siebe Projects samples is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Siebe Projects samples is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the Lesser GNU General Public License
 * along with Siebe Projects samples.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.siebeprojects.samples.keyboardheight;

import android.widget.TextView;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * MainActivity that initialises the keyboardheight 
 * provider and observer. 
 */
public final class MainActivity extends AppCompatActivity implements KeyboardHeightObserver {

    /** Tag for logging */
    private final static String TAG = "sample_MainActivity";

    /** The keyboard height provider */
    private KeyboardHeightProvider keyboardHeightProvider;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        View parentView = findViewById(R.id.parentview);
        keyboardHeightProvider = new KeyboardHeightProvider(this, parentView, 0, 0);
        
        // start the keyboard height provider after 
        // the view is initialized and after onResume of this Activity
        parentView.post(new Runnable() {
                public void run() {
                    keyboardHeightProvider.start();
                }
            });
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        keyboardHeightProvider.setKeyboardHeightObserver(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        keyboardHeightProvider.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onKeyboardHeightChanged(int height) {
        Log.i(TAG, "onKeyboardHeightChanged in pixels: " + height);
        TextView tv = (TextView)findViewById(R.id.height_text);
        tv.setText(Integer.toString(height));
    }
}
