# simple_park_management
駐車場管理システム（Simple Park Management）
このプロジェクトは、Spring Boot を使用して構築されたシンプルな駐車場管理システムです。ユーザー、車両、利用履歴などを効率的に管理するための RESTful API を提供します。

プロジェクト構成
src/ 
├── main/ 
│ ├── java/com/example/park/ 
│ │ ├── controller/ - APIエンドポイント 
│ │ ├── domain/ - 業務ロジック層 
│ │ │ ├── entity/ - エンティティ（DBモデル） 
│ │ │ ├── dto/ - データ転送オブジェクト 
│ │ │ ├── mapper/ - MyBatis-Plus マッパー 
│ │ │ ├── service/ - サービスインターフェースと実装 
│ │ ├── common/ - 共通処理（例外、レスポンスなど） 
│ │ ├── advice/ - 例外ハンドリング 
│ │ └── ParkApplication.java - メインクラス 
│ └── resources/ 
│ ├── application.properties - 設定ファイル 
│ ├── mapper/ - XMLマッピング（任意） 
│ ├── static/ - 静的ファイル（任意） 
│ └── templates/ - テンプレート（任意） 
└── test/ - テストコード

主な機能
ユーザー登録・認証

車両の登録・編集・削除

駐車場の利用履歴管理（乗車・降車）

ステータスによる車両の使用状況管理

統一されたレスポンス形式とエラーハンドリング

DTO/Entity 分離による保守性の高い設計

使用技術
Java 17

Spring Boot 3.x

MyBatis-Plus

MapStruct

Maven

Git / GitHub

VS Code

起動方法
必要な依存関係をインストール：

mvn clean install

アプリケーションを起動：

mvn spring-boot:run


データベース初期化
docs/schema.sql に建表SQLが含まれています。MySQLなどのRDBMSにて実行してください。

API例（抜粋）
POST /park/user/register：ユーザー登録

GET /park/car/list：車両一覧取得

POST /park/car/ride：乗車開始

POST /park/car/drop：降車処理

ライセンス
このプロジェクトは MIT ライセンスのもとで公開されています。

作者
GAOMING バックエンドアーキテクト / Java & Spring Boot エキスパート GitHub: https://github.com/Chengmc6
