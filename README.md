Room(データベース)
==================================

参考URL：https://developer.android.com/training/data-storage/room/defining-data?hl=ja

概要
------------

・データベースの世界では、実体とクエリというものが必要になります。
    ・実体：データベースに保存するためにオブジェクトや概念、およびそれらのプロパティを表すものです。
        実体クラスはテーブルとそのクラスがテーブル内で示す行ごとのインスタンスを定義します。
        それぞれのプロパティはカラムを定義します。今回のアプリでは、実体は睡眠した夜についての情報を保持します。

    ・クエリ：データベースのテーブル、およびテーブルの組み合わせからデータや情報を要求する際の要求、またデータ上でなんらかのアクションを起こさせるための要求のことを言います。
        一般的なクエリは実体の取得、挿入、更新などです。例えば、全ての夜の記録を開始時間で並べ替えて要求することなどができます。

・それぞれの実体をアノテーションされたデータクラスとして、またデータとのやり取りはアノテーションされたインターフェースである、データアクセスオブジェクト（DAO)として定義する必要があります。
    Roomはこれらのアノテーションされたクラスをデータベースにテーブルやデータベース上で動作するクエリを作成するために使用します。

Pre-requisites
--------------

You need to know:

* Building a basic user interface (UI) for an Android app, 
  using an activity, fragments, and views.
* Navigating between fragments and using Safe Args (a Gradle plugin) 
  to pass data between fragments.
* View models, view-model factories, and LiveData and its observers. 
  These Architecture Components topics are covered in an earlier codelab in this course.
* A basic understanding of SQL databases and the SQLite language.


Getting Started
---------------

1. Download and run the app.

License
-------

Copyright 2019 Google, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.