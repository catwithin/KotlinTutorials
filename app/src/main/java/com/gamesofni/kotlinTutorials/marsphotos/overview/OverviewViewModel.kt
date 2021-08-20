/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gamesofni.kotlinTutorials.marsphotos.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamesofni.kotlinTutorials.marsphotos.network.MarsApi
import com.gamesofni.kotlinTutorials.marsphotos.network.MarsPhoto
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()
    private val _photos = MutableLiveData<MarsPhoto>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status
    val photos: LiveData<MarsPhoto> = _photos

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getMarsPhotos() {
        viewModelScope.launch {
            // TODO: how to make it auto-refresh when connection is re-established??
            try {
//                val listResult = MarsApi.retrofitService.getPhotos()
                _photos.value = MarsApi.retrofitService.getPhotos()[0]

                // with scalar String result
//                _status.value = listResult
                // with Mosdhi converter
//                _status.value = "Success: ${listResult.size} Mars photos retrieved"
                _status.value = "   First Mars image URL : ${_photos.value!!.imgSrcUrl}"

            } catch (e: Exception) {
                // catching no internet exception
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}
