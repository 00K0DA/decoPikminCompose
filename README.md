# decoPikminCompose

下記のプロジェクトを、Composeで作り直します。

https://github.com/00K0DA/decopikmin

## テーブル定義
decor_type, costume_type, pikmin_typeなどはIntで管理するか悩んだが、
StringとIntでのSelectによる時間の差はほとんどないようなので、
Enumで定義しやすく、可読性もよいStringを採択した。

### pikmin_status_table
ピクミンの状態を管理するテーブルです。
| カラム名 | 型 | PK | Non Null | その他 |
| :---: | :---: | :---: | :---: | :---: |
| decor_type | String | 〇 | 〇 | |
| costume_type | String | 〇 | 〇 | |
| pikmin_type | String | 〇 | 〇 | |
| pikmin_number | Int | 〇 | 〇 | |
| pikmin_status | Int |  | 〇 | |
