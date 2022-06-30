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

package com.example.android.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SleepDatabaseDao{
    //データの挿入
    @Insert
    suspend fun insert(night: SleepNight)

    //データのアップデート
    @Update
    suspend fun upDate(night: SleepNight)

    //データの削除
    @Delete
    suspend fun delete(night: SleepNight)

    //カスタムクエリ(データの取得)
    @Query("SELECT * FROM daily_sleep_quality_table WHERE nightId = :key")  //関数の引数を利用
    suspend fun get(key: Long): SleepNight?

    //カスタムクエリ(データの全削除)
    @Query("DELETE FROM daily_sleep_quality_table")
    suspend fun clear()

    //カスタムクエリ(idをもとに降順で並び替え、リストの一番最初(最新のデーター)を一つだけ取得。
    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    suspend fun getToNight(): SleepNight?

    //カスタムクエリ(全データを取得)
    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<SleepNight>>
}
