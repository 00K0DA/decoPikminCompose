# decoPikminCompose

下記のプロジェクトを、Composeで作り直します。

https://github.com/00K0DA/decopikmin

## テーブル定義


### decor_type_table
DecorTypeを管理するテーブルです。
| カラム名 | 型 | PK | Non Null | その他 |
| :---: | :---: | :---: | :---: | :---: |
| decor_type_id | Int | 〇 | 〇 | Auto Increment |
| decor_type_name | String |  | 〇 | |

### costume_type_table
CostumeTypeを管理するテーブルです。
| カラム名 | 型 | PK | Non Null | その他 |
| :---: | :---: | :---: | :---: | :---: |
| costume_type_id | Int | 〇 | 〇 | Auto Increment |
| costume_type_name | String |  | 〇 | |

### pikmin_type_table
PikminTypeを管理するテーブルです。
| カラム名 | 型 | PK | Non Null | その他 |
| :---: | :---: | :---: | :---: | :---: |
| pikmin_type_id | Int | 〇 | 〇 | Auto Increment |
| pikmin_type_name | String |  | 〇 | |

### pikmin_status_table
ピクミンの状態を管理するテーブルです。
| カラム名 | 型 | PK | Non Null | その他 |
| :---: | :---: | :---: | :---: | :---: |
| decor_type_id | Int | 〇 | 〇 | |
| costume_type_id | Int | 〇 | 〇 | |
| pikmin_type_id | Int | 〇 | 〇 | |
| pikmin_number | Int | 〇 | 〇 | |
| pikmin_status | Int |  | 〇 | |
