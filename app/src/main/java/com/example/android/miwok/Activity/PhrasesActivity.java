/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok.activity;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.miwok.R;
import com.example.android.miwok.Word;
import com.example.android.miwok.adapter.WordAdapter;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    /**Handles playback for all the sound files **/
    private MediaPlayer mMediaPlayer;

    /**Handles Audio Focus for media playback**/
    AudioManager mAudioManager;

    /**Listener for handling audio focus changes **/
    AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = getAudioFocusChangeCallback();



    /** This listener gets triggered when the {@link MediaPlayer} has completed playing
     * the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){

        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setBackgroundColor(Color.parseColor("#16AFCA"));
        //listView.setBackgroundColor(R.color.category_phrases);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        //plays the audio for number one when any item in the list is clicked click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //release current resources being used for media player if it currently exists
                //because we are about to play a different sound file.
                releaseMediaPlayer();

                //get the word located in the list that is at same position as the item clicked in the list
                Word currentWord = words.get(position);

                /// Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request temporary focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    //create the medial player with the audio file that is stored in the list for that word.
                    mMediaPlayer = MediaPlayer.create(getApplicationContext(), currentWord.getmMiwokAudio());
                    //play the file
                    mMediaPlayer.start();

                    //listener to stop and release the media player and resources being used
                    // once the sounds has finished playing
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        //stop and release media player when user navigates away from the activity and the activity
        //is stopped
        releaseMediaPlayer();
    }
    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Abandon audio focus when playback complete
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }

    private AudioManager.OnAudioFocusChangeListener getAudioFocusChangeCallback(){

        return new AudioManager.OnAudioFocusChangeListener() {
            Handler mHandler = new Handler();
            public void onAudioFocusChange(int focusChange) {

                // The AUDIOFOCUS_LOSS case means we've lost audio focus permanently
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    // Stop playback and clean up resources
                    releaseMediaPlayer();
                }
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time.
                else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                    // Pause playback immediately
                    mMediaPlayer.pause();
                    // play the word from the beginning when we resume playback.
                    mMediaPlayer.seekTo(0);
                }
                //The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume.
                else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    // Lower the volume, keep playing
                    mAudioManager.adjustVolume(AudioManager.ADJUST_LOWER, 0);
                }
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    // Raise volume to normal, restart playback if necessary
                    mMediaPlayer.start();
                    mAudioManager.adjustVolume(AudioManager.ADJUST_RAISE, 0);

                }
            }
        };
    }
}
