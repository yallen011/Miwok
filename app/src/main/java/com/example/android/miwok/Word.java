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
package com.example.android.miwok;

/**
 * {@link Word} represents a vocabulary word that the user wants to learn.
 * It contains a default translation and a Miwok translation for that word.
 */
public class Word {

    /** Default translation for the word */
    private String mDefaultTranslation;

    /** Miwok translation for the word */
    private String mMiwokTranslation;

    /** Miwok image that is associated with the word. By default this image is not provided unless it is set in the constructor  */
    private int mMiwokImageId = NO_IMAGE_PROVIDED;

    /**Miwok audio that is associated with the word */
    private int mMiwokAudio;

    //This is used to indicate that no image was provided.
    private static final int NO_IMAGE_PROVIDED = -1;

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation is the word in the Miwok language
     */

    public Word(String defaultTranslation, String miwokTranslation, int miwokAudio) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mMiwokAudio = miwokAudio;
    }

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation is the word in the Miwok language
     *
     * @param miwokImageId is the image resource id for that word
     */
    public Word(String defaultTranslation, String miwokTranslation, int miwokImageId, int miwokAudio) {
        this(defaultTranslation, miwokTranslation, miwokAudio);
        mMiwokImageId = miwokImageId;
    }
    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    /**
     * Get the Miwok image associated to the word
     * @return String imageUrl
     */
    public int getmMiwokImageId() {
        return mMiwokImageId;
    }


    /**
     * Get the Miwok audio associated to the word
     * @return int audioId
     */
    public int getmMiwokAudio() {
        return mMiwokAudio;
    }

    public void setmMiwokAudio(int mMiwokAudio) {
        this.mMiwokAudio = mMiwokAudio;
    }

    /**
     * method to specify if a word has an image
     * @return true or false
     */
    public boolean hasImage(){
        
        //if word has an image, it will return true, else it will return false
        return getmMiwokImageId() != NO_IMAGE_PROVIDED;
    }

}