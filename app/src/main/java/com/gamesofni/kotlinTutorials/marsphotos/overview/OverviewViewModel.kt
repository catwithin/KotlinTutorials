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
import com.gamesofni.kotlinTutorials.marsphotos.network.MarsProperty
import kotlinx.coroutines.launch


enum class MarsApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<MarsApiStatus>()
    private val _photos = MutableLiveData<List<MarsProperty>>()

    // The external immutable LiveData for the request status
    val status: LiveData<MarsApiStatus> = _status
    val photos: LiveData<List<MarsProperty>> = _photos

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsProperty] [List] [LiveData].
     */
    private fun getMarsPhotos() {
        viewModelScope.launch {
            // TODO: how to make it auto-refresh when connection is re-established??
            _status.value = MarsApiStatus.LOADING
            try {
//                val listResult = MarsApi.retrofitService.getPhotos()
                _photos.value = MarsApi.retrofitService.getPhotos()

                // with scalar String result
//                _status.value = listResult
                // with Mosdhi converter
//                _status.value = "Success: ${listResult.size} Mars photos retrieved"
//                _status.value = "   First Mars image URL : ${_photos.value!!.imgSrcUrl}"
                _status.value = MarsApiStatus.DONE

            } catch (e: Exception) {
                // catching no internet exception
                _status.value = MarsApiStatus.ERROR
                _photos.value = listOf()
            }
        }
    }
}
