-- ユーザー表
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ユーザーID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT 'ユーザー名',
    password VARCHAR(100) NOT NULL COMMENT 'パスワード',
    role TINYINT(1) DEFAULT 0 COMMENT 'ロール（0=普通ユーザー，1=管理員）',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '削除標識（0=未削除，1=削除した）',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '生成日時',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時'
);

-- 車輌情報表
CREATE TABLE car (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '車輌ID',
    car_number VARCHAR(20) NOT NULL UNIQUE COMMENT '車輌番号',
    status TINYINT(1) DEFAULT 0 COMMENT '輌ステータス（0=未使用，1=乗車中）',
    current_user_id BIGINT COMMENT '今使用者ID（空値できる）',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '削除標識（0=未削除，1=削除した）',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '車輌更新時間',
    FOREIGN KEY (current_user_id) REFERENCES user(id)
);
-- 履歴表
CREATE TABLE car_usage (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '使用記録ID',
    car_id BIGINT NOT NULL COMMENT '車輌ID',
    user_id BIGINT NOT NULL COMMENT '使用者ID',
    ride_time DATETIME NOT NULL COMMENT '乗車時間',
    ride_alcohol_level DECIMAL(4,2) COMMENT '乗車時のアルコール度数',
    drop_time DATETIME DEFAULT NULL COMMENT '降車時間（未降車の時はNULL）',
    drop_alcohol_level DECIMAL(4,2) DEFAULT NULL COMMENT '降車時のアルコール度数（未降車の時はNULL）',
    FOREIGN KEY (car_id) REFERENCES car(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

