■jsonのスケルトンを生成し、それを手直ししてテーブルスキーマを作成
　テーブル生成時にjsonファイルを渡してテーブルを作る。
$ aws dynamodb create-table --generate-cli-skeleton > tmp/skeleton.json
$ aws dynamodb create-table --cli-input-json file://productCatalog.json 

