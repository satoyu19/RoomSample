/*
 * Copyright 2019, The Android Open Source Project
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

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.launch

/**
 * AndroidViewModel →　ViewModelと変わりないが、コンストラクタで受け取ったapplicationをプロパティとして利用できるようにしている
 */
class SleepTrackerViewModel(val database: SleepDatabaseDao, application: Application) : AndroidViewModel(application) {

    private var tonight = MutableLiveData<SleepNight?>()

    init {
        initializeTonight()

    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        //最新の夜の情報を抽出
        var night = database.getToNight()
        if(night?.endTimeMilli != night?.startTimeMilli){   //計測済み出なければnullを返す
            night = null
        }
        return night
    }

    //スタートボタンのハンドラー
    suspend fun onStartTracking(){
        val newNight = SleepNight()
        insert(newNight)
        tonight.value = getTonightFromDatabase()
    }

    private suspend fun insert(newNight: SleepNight) {
        database.insert(newNight)
    }
}

