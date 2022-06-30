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
import androidx.lifecycle.*
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.launch

/**
 * AndroidViewModel →　ViewModelと変わりないが、コンストラクタで受け取ったapplicationをプロパティとして利用できるようにしている
 */
class SleepTrackerViewModel(val database: SleepDatabaseDao, application: Application) : AndroidViewModel(application) {

    //navigationのトリガー
    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()
    val navigateToSleepQuality: LiveData<SleepNight>
    get() = _navigateToSleepQuality

    private var tonight = MutableLiveData<SleepNight?>()

    //navigationのトリガーをリセット
    fun doneNavigating(){
        _navigateToSleepQuality.value = null
    }

    /** database.getAllNightはLiveDateを返すため、databaseに更新があれば、nightsを利用したUIも更新される */
    private var nights = database.getAllNights()
    val nightString = Transformations.map(nights){ nights ->
        //データベースの情報を１つの文字列に変更する
        formatNights(nights, application.resources)
    }

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
     fun onStartTracking(){
        viewModelScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insert(newNight: SleepNight) {
        database.insert(newNight)
    }

    //ストップボタンのハンドラー
    fun onStopTracking(){
        viewModelScope.launch {
            //終了時間がセットされていなければ、終了時間を更新する
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            upDate(oldNight)

            //_navigateToSleepQualityが値を持っている時に遷移する
            _navigateToSleepQuality.value = oldNight
        }
    }

    private suspend fun upDate(night: SleepNight) {
        database.upDate(night)
    }

    //clearButtonのハンドラー、database内全削除
    fun onClear(){
        viewModelScope.launch {
            clear()
            tonight.value = null
        }
    }

    private suspend fun clear(){
        database.clear()
    }
}

