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

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase: RoomDatabase(){       //Roomが実装を作成するため、抽象クラス。

    //Daoについて知る必要があるため、Daoを返す抽象値を宣言
    abstract val sleepDatabaseDao: SleepDatabaseDao

    //インスタンス化する必要がない
    companion object{

        /*volatile変数の値は決してキャッシュされず、全ての読み書きはメインメモリで行われます。これによりINSTANCEの値が常に最新かつ、全ての実行中のスレッドで同じことが保障されます。
        二つ以上のスレッドがそれぞれキャッシュ内の同じ実体を更新してしまうといった状況に陥ることがなくなります。*/
        @Volatile
        //databaseが作成された時点でINSTANCE変数にはdatabaseの参照をもたせ、繰り返し負荷のかかるデータベースへの接続を開く必要がなくなる。
        private var INSTANCE: SleepDatabase? = null

        fun getInstance(context: Context): SleepDatabase{
            //排他制御、実行中の一つのスレッドのみ、このコードのブロックに突入することができ、データベースが一度しか初期化されない
            synchronized(this){
                //スマートキャスト
                var instance = INSTANCE
                if(instance == null){
                    //データベースを構築、本来スキーマー関わった時用に移行オブジェクト移行戦略と共に提供する必要があるが、fallbackToDestructiveMigration()を利用
                    instance = Room.databaseBuilder(context.applicationContext, SleepDatabase::class.java,"sleep_history_database").
                    fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}